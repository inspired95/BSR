package client.control.event;

import client.control.RepositoryCreatorDialogController;
import com.catchex.bankstmt.categories.OperationCategoryResolverImpl;
import com.catchex.bankstmt.operationtype.OperationTypeResolver;
import com.catchex.bankstmt.operationtype.OperationTypeResolverFactory;
import com.catchex.bankstmt.pdfconverters.BankStmtConverter;
import com.catchex.bankstmt.pdfconverters.BankStmtConverterFactory;
import com.catchex.bankstmt.transformators.RawOperationExtender;
import com.catchex.configuration.Configuration;
import com.catchex.io.reader.PDFReader;
import com.catchex.models.Operation;
import com.catchex.models.RawOperation;
import com.catchex.util.Log;
import javafx.event.ActionEvent;
import javafx.scene.control.ChoiceDialog;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static client.Constants.supportedBanks;


public class LoadBankStatementsBtnEventHandler
    implements EventHandler<ActionEvent>
{

    private RepositoryCreatorDialogController controller;


    public LoadBankStatementsBtnEventHandler( RepositoryCreatorDialogController controller )
    {
        this.controller = controller;
    }


    @Override
    public void handle( ActionEvent event )
    {
        String selectedBankName = getSelectedBank();

        List<File> selectedBankStatementsFiles = getBankStatementsFileToLoad();

        if( selectedBankStatementsFiles != null )
        {
            for( File bankStatementFile : selectedBankStatementsFiles )
            {
                Optional<String> readBankStatementContent =
                    PDFReader.read( bankStatementFile.getAbsolutePath() );
                readBankStatementContent.ifPresentOrElse(
                    s -> manageReadBankStatementFile( selectedBankName, bankStatementFile.getName(),
                        s ), () -> Log.LOGGER.warning(
                        "Cannot read bank statement file: " + bankStatementFile.getName() ) );
            }
        }
    }


    private void manageReadBankStatementFile(
        String bankChoiceDialog, String bankStatementFileName, String readBankStatementFile )
    {
        List<RawOperation> rawOperations =
            convertRawReadBankStatementFileToRawBankOperations( bankChoiceDialog,
                bankStatementFileName, readBankStatementFile );
        if( !rawOperations.isEmpty() )
        {
            controller.addOperations( decorate( bankChoiceDialog, rawOperations ) );
        }
    }


    private Set<Operation> decorate(
        String bankChoiceDialog, List<RawOperation> convert )
    {
        Optional<OperationTypeResolver> operationTypeResolver =
            new OperationTypeResolverFactory().match( bankChoiceDialog );
        if( operationTypeResolver.isPresent() )
        {
            RawOperationExtender extender = getRawOperationExtender( operationTypeResolver.get() );
            return extender.extend( convert );
        }
        return Collections.emptySet();
    }


    private RawOperationExtender getRawOperationExtender(
        OperationTypeResolver operationTypeResolver )
    {
        return new RawOperationExtender(
            operationTypeResolver, new OperationCategoryResolverImpl(
            Configuration.getCategoriesConfiguration().getCategories() ) );
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


    private String getSelectedBank()
    {
        ChoiceDialog<String> bankChoiceDialog =
            new ChoiceDialog<>( supportedBanks[0], supportedBanks );
        bankChoiceDialog.showAndWait();
        return bankChoiceDialog.getSelectedItem();
    }


    private List<File> getBankStatementsFileToLoad()
    {
        Stage window = (Stage)controller.getView().getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle( "Select bank statements" );

        return fileChooser.showOpenMultipleDialog( window );
    }
}
