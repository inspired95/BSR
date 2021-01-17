package com.catchex.repositorycreator.client.control.event;

import GuiHelpers.Alerts;
import com.catchex.io.reader.PDFReader;
import com.catchex.models.Operation;
import com.catchex.models.RawOperation;
import com.catchex.repositorycreator.bankstatementsconverter.BankStmtConverter;
import com.catchex.repositorycreator.bankstatementsconverter.BankStmtConverterFactory;
import com.catchex.repositorycreator.categoryresolving.OperationCategoryResolverImpl;
import com.catchex.repositorycreator.client.control.RepositoryCreatorDialogController;
import com.catchex.repositorycreator.operationextention.RawOperationExtender;
import com.catchex.repositorycreator.typeresolving.OperationTypeResolver;
import com.catchex.repositorycreator.typeresolving.OperationTypeResolverFactory;
import com.catchex.util.Constants;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

import java.io.File;
import java.util.*;


public class LoadBankStatementsBtnEventHandler
    implements EventHandler<ActionEvent>
{
    private SortedSet<String> banks = new TreeSet<>( Arrays.asList( Constants.supportedBanks ) );

    private RepositoryCreatorDialogController controller;


    public LoadBankStatementsBtnEventHandler( RepositoryCreatorDialogController controller )
    {
        this.controller = controller;
    }


    @Override
    public void handle( ActionEvent event )
    {
        Optional<String> selectedBankName = getSelectedBank();

        selectedBankName.ifPresent( bankName -> {
            List<File> selectedBankStatementsFiles = getBankStatementsFileToLoad();

            if( selectedBankStatementsFiles != null )
            {
                for( File bankStatementFile : selectedBankStatementsFiles )
                {
                    Optional<String> readBankStatementFileContent =
                        PDFReader.read( bankStatementFile.getAbsolutePath() );
                    readBankStatementFileContent.ifPresent(
                        fileContent -> manageReadBankStatementFile( bankName,
                            bankStatementFile.getName(), fileContent ) );
                }
            }
        } );

    }


    private void manageReadBankStatementFile(
        String bankChoiceDialog, String bankStatementFileName, String readBankStatementFile )
    {
        List<RawOperation> rawOperations =
            convertRawReadBankStatementFileToRawBankOperations( bankChoiceDialog,
                bankStatementFileName, readBankStatementFile );
        if( !rawOperations.isEmpty() )
        {
            decorate( bankChoiceDialog, rawOperations );
        }
    }


    private void decorate(
        String bankChoiceDialog, List<RawOperation> rawOperations )
    {
        Optional<OperationTypeResolver> operationTypeResolver =
            new OperationTypeResolverFactory().match( bankChoiceDialog );
        if( operationTypeResolver.isPresent() )
        {
            RawOperationExtender extender = getRawOperationExtender( operationTypeResolver.get() );
            Set<Operation> operations = extender.extend( rawOperations );
            controller.addOperations( operations );
        }
    }


    private RawOperationExtender getRawOperationExtender(
        OperationTypeResolver operationTypeResolver )
    {
        return new RawOperationExtender(
            operationTypeResolver, new OperationCategoryResolverImpl() );
    }


    private List<RawOperation> convertRawReadBankStatementFileToRawBankOperations(
        String bankChoiceDialog, String bankStatementFileName, String readBankStatementFile )
    {
        Optional<BankStmtConverter> bankStmtConverter =
            getBankStatementConverter( bankChoiceDialog );
        List<RawOperation> rawOperations = Collections.emptyList();
        if( bankStmtConverter.isPresent() )
        {
            rawOperations =
                bankStmtConverter.get().convert( bankStatementFileName, readBankStatementFile );
        }
        return rawOperations;
    }


    private Optional<BankStmtConverter> getBankStatementConverter( String bankChoiceDialog )
    {
        return BankStmtConverterFactory.match( bankChoiceDialog );
    }


    private Optional<String> getSelectedBank()
    {
        return Alerts.showChoiceFromListDialog( "Repository creator", Constants.SELECT_BANK_TXT,
            new ArrayList<>( banks ) );
    }


    private List<File> getBankStatementsFileToLoad()
    {
        return Alerts.showOpenMultipleDialog( (Stage)controller.getView().getScene().getWindow(),
            "Select bank statements" );
    }

}
