package client.control.event;

import client.control.RepositoryCreatorDialogController;
import com.catchex.bankstmt.categories.OperationCategoryResolverImpl;
import com.catchex.bankstmt.operationtype.OperationTypeResolver;
import com.catchex.bankstmt.operationtype.OperationTypeResolverFactory;
import com.catchex.bankstmt.pdfconverters.BankStmtConverter;
import com.catchex.bankstmt.pdfconverters.BankStmtConverterFactory;
import com.catchex.bankstmt.transformators.RawOperationExtender;
import com.catchex.io.reader.PDFReader;
import com.catchex.models.ConfigurationV2;
import com.catchex.models.Operation;
import com.catchex.models.RawOperation;
import javafx.event.ActionEvent;
import javafx.scene.control.ChoiceDialog;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.catchex.util.Constants.supportedBanks;


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
                Optional<String> readBankStatementFileContent =
                    PDFReader.read( bankStatementFile.getAbsolutePath() );
                readBankStatementFileContent.ifPresent(
                    fileContent -> manageReadBankStatementFile( selectedBankName,
                        bankStatementFile.getName(), fileContent ) );
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
        return new RawOperationExtender( operationTypeResolver, new OperationCategoryResolverImpl(
            ConfigurationV2.getInstance().getCategoriesConfiguration().getCategories() ) );
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
