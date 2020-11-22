package client.control.event;

import client.control.RepositoryCreatorDialogController;
import com.catchex.bankstmt.categories.OperationCategoryResolverImpl;
import com.catchex.bankstmt.operationtype.OperationTypeResolver;
import com.catchex.bankstmt.operationtype.OperationTypeResolverFactory;
import com.catchex.bankstmt.pdfconverters.BankStmtConverter;
import com.catchex.bankstmt.pdfconverters.BankStmtConverterFactory;
import com.catchex.bankstmt.transformators.OperationTransformer;
import com.catchex.configuration.Configuration;
import com.catchex.io.reader.PDFReader;
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

public class LoadBankStatementsBtnEventHandler implements EventHandler<ActionEvent> {

    private RepositoryCreatorDialogController controller;

    public LoadBankStatementsBtnEventHandler( RepositoryCreatorDialogController controller ) {
        this.controller = controller;
    }


    @Override
    public void handle( ActionEvent event ) {
        ChoiceDialog<String> bankChoiceDialog = new ChoiceDialog<>(controller.getSupportedBanks()[0], controller.getSupportedBanks());
        bankChoiceDialog.showAndWait();

        Stage window = (Stage) controller.getView().getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select bank statements");

        List<File> selectedBankStatements = fileChooser.showOpenMultipleDialog(window);

        if (selectedBankStatements != null){
            for (File bankStatement : selectedBankStatements) {
                Optional<String> read = PDFReader.read(bankStatement.getAbsolutePath());
                List<RawOperation> convert = Collections.emptyList();
                if (read.isPresent()){
                    Optional<BankStmtConverter> bankStmtConverter = BankStmtConverterFactory.match(bankChoiceDialog.getSelectedItem());
                    if (bankStmtConverter.isPresent()){
                        convert = bankStmtConverter.get().convert(bankStatement.getName(), read.get());
                    }
                    Optional<OperationTypeResolver> operationTypeResolver =
                            new OperationTypeResolverFactory().match( bankChoiceDialog.getSelectedItem() );
                    if (operationTypeResolver.isPresent() && !convert.isEmpty()){
                        OperationTransformer transformer = new OperationTransformer(
                                operationTypeResolver.get(), new OperationCategoryResolverImpl(
                                Configuration.getCategoriesConfiguration().getCategories() ) );
                        Set<Operation> operations = transformer.transform( convert );
                        controller.addOperations(operations);
                    }
                }
            }
        }
    }
}
