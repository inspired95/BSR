package client;

import com.catchex.bankstmt.pdfconverters.BankStmtConverter;
import com.catchex.bankstmt.pdfconverters.BankStmtConverterFactory;
import com.catchex.io.reader.PDFReader;
import com.catchex.models.RawOperation;
import com.catchex.models.RawOperationRepository;
import javafx.scene.control.ChoiceDialog;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.util.*;

import static com.catchex.util.Constants.PKO;

public class RepositoryCreatorDialogController {

    private RawOperationRepository repository;
    private RepositoryCreatorDialogView view;

    private Stage stage;

    private String[] supportedBanks = { PKO };


    public RepositoryCreatorDialogController() {
        this.repository = new RawOperationRepository();
        this.view = new RepositoryCreatorDialogView(this);

    }

    public void init(Stage stage){
        this.stage = stage;
        view.initView();
        loadBankStatementsMenuItemActionEventHandling();
        loadRepositoryMenuItemActionEventHandling();
        saveRepositoryMenuItemActionEventHandling();

        stage.setScene(view.getScene());
        stage.show();
    }


    private void loadBankStatementsMenuItemActionEventHandling(){
        view.getLoadBankStatementsMenuItem().setOnAction(actionEvent -> {
            ChoiceDialog<String> bankChoiceDialog = new ChoiceDialog<>(supportedBanks[0], supportedBanks);
            bankChoiceDialog.showAndWait();

            Stage window = (Stage) stage.getScene().getWindow();
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
                    }
                    updateRepository(convert);
                }
                view.redrawTreeViewTable();
            }

            /*Platform.runLater(() -> {
                new PdfEditorApplication().start(new Stage());
            });
            stage.close();*/
        });
    }

    private void loadRepositoryMenuItemActionEventHandling(){
        view.getLoadRepositoryMenuItem().setOnAction(actionEvent -> {
            Stage window = (Stage) stage.getScene().getWindow();
            FileChooser repositoryToLoadFileChooser = new FileChooser();
            repositoryToLoadFileChooser.setTitle("Select repository");
            repositoryToLoadFileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("BSR repository file", "*.bsrrepository"));

            File selectedRepository = repositoryToLoadFileChooser.showOpenDialog(window);

            if (selectedRepository != null){
                RawOperationRepository loadedRepository = null;
                try(ObjectInputStream oi = new ObjectInputStream(new FileInputStream(selectedRepository))){
                    loadedRepository = (RawOperationRepository) oi.readObject();
                }catch (IOException | ClassNotFoundException exp ){
                    exp.printStackTrace();
                }
                if (loadedRepository != null)
                repository = loadedRepository;
            }
            view.redrawTreeViewTable();
        });
    }

    private void saveRepositoryMenuItemActionEventHandling(){
        view.getSaveRepositoryMenuItem().setOnAction(actionEvent -> {
            Stage window = (Stage) stage.getScene().getWindow();
            FileChooser repositorySaveFileChooser = new FileChooser();
            repositorySaveFileChooser.setTitle("Save repository");
            repositorySaveFileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("BSR repository selectedFile", "*.bsrrepository"));
            File selectedFile = repositorySaveFileChooser.showSaveDialog(window);
            try{
                FileOutputStream f = new FileOutputStream(selectedFile);
                ObjectOutputStream o = new ObjectOutputStream(f);
                o.writeObject(repository);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }



    private void updateRepository( List<RawOperation> convert ) {
        convert.forEach(rawOperationToAdd -> {
            String formatedData = rawOperationToAdd.getDate().format(view.getBankStatementsCategoriesFormatter());
            Set<RawOperation> existsRawOperationsForInterval = repository.getRawOperations().get(formatedData);
            if (existsRawOperationsForInterval == null) {
                Set<RawOperation> newSet = new HashSet<>();
                newSet.add(rawOperationToAdd);
                repository.getRawOperations().put(formatedData, newSet);
            } else {
                existsRawOperationsForInterval.add(rawOperationToAdd);
                repository.getRawOperations().put(formatedData, existsRawOperationsForInterval);
            }
        });
    }

    public RawOperationRepository getRepository() {
        return repository;
    }
}
