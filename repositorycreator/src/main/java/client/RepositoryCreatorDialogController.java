package client;

import client.view.RepositoryCreatorDialogView;
import client.view.RepositoryCreatorOperationTreeItem;
import client.view.event.CellEditEventHandler;
import client.view.event.RepositoryCreatorDescriptionEditCellEventHandler;
import com.catchex.bankstmt.categories.OperationCategoryResolverImpl;
import com.catchex.bankstmt.operationtype.OperationTypeResolver;
import com.catchex.bankstmt.operationtype.OperationTypeResolverFactory;
import com.catchex.bankstmt.pdfconverters.BankStmtConverter;
import com.catchex.bankstmt.pdfconverters.BankStmtConverterFactory;
import com.catchex.bankstmt.transformators.OperationTransformer;
import com.catchex.configuration.Configuration;
import com.catchex.io.reader.PDFReader;
import com.catchex.models.Category;
import com.catchex.models.Operation;
import com.catchex.models.RawOperation;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.TreeItem;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.catchex.util.Constants.PKO;
import static javafx.scene.control.TreeTableColumn.CellEditEvent;

public class RepositoryCreatorDialogController {

    private Repository repository;
    private RepositoryCreatorDialogView view;
    private OperationCategoryResolverImpl categoryResolver;
    private CellEditEventHandler descriptionEditCellEventHandler;

    private Stage stage;

    private String[] supportedBanks = { PKO };


    public RepositoryCreatorDialogController() {
        this.repository = new Repository();
        this.view = new RepositoryCreatorDialogView(this);
        this.categoryResolver = new OperationCategoryResolverImpl(Configuration.getCategoriesConfiguration().getCategories());
        this.descriptionEditCellEventHandler = new RepositoryCreatorDescriptionEditCellEventHandler(this);
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
                    List<RepositoryCreatorOperationTreeItem> viewModels = Collections.emptyList();
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
                            addOperations(operations);
                        }
                    }
                }
            }

            /*Platform.runLater(() -> {
                new PdfEditorApplication().start(new Stage());
            });
            stage.close();*/
        });
    }

    private void addOperations( Set<Operation> operations ) {
        repository.addOperations(operations);
        view.updateView(operations );
    }

    public void addOperation( Operation operation, int index ) {
        repository.addOperation(operation);
        view.updateView(operation, index);
    }

    private void loadRepositoryMenuItemActionEventHandling(){
        view.getLoadRepositoryMenuItem().setOnAction(actionEvent -> {
            Stage window = (Stage) stage.getScene().getWindow();
            FileChooser repositoryToLoadFileChooser = new FileChooser();
            repositoryToLoadFileChooser.setTitle("Select repository");
            repositoryToLoadFileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("BSR repository file", "*.bsrrepository"));

            File selectedRepository = repositoryToLoadFileChooser.showOpenDialog(window);

            if (selectedRepository != null){
                Repository loadedRepository = null;
                try(ObjectInputStream oi = new ObjectInputStream(new FileInputStream(selectedRepository))){
                    loadedRepository = (Repository) oi.readObject();
                }catch (IOException | ClassNotFoundException exp ){
                    exp.printStackTrace();
                }
                if (loadedRepository != null) {
                    view.updateView(loadedRepository);
                }
            }
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
                System.out.println(repository.getOperations().size());
                FileOutputStream f = new FileOutputStream(selectedFile);
                ObjectOutputStream o = new ObjectOutputStream(f);
                o.writeObject(repository);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }


    public void handleDescriptionEditEvent( CellEditEvent event ){
        System.out.println("event");
        descriptionEditCellEventHandler.handle(event);
        /*Object treeItemToEdit = event.getRowValue().getValue();
        if (treeItemToEdit instanceof RepositoryCreatorOperationTreeItem){
            RepositoryCreatorOperationTreeItem operationTreeItemToEdit = (RepositoryCreatorOperationTreeItem) treeItemToEdit;
            Operation operationToUpdate = operationTreeItemToEdit.getOperation();

            TreeItem intervalTreeItem = event.getRowValue().getParent();

            int currentIndex = intervalTreeItem.getChildren().indexOf(event.getRowValue());

            getRepository().getOperations().remove(operationToUpdate);
            intervalTreeItem.getChildren().remove(event.getRowValue());

            String newOperationDescription = (String)event.getNewValue();
            operationToUpdate.getRawOperation().setDesc(newOperationDescription);

            Category newOperationCategory = categoryResolver.resolve(newOperationDescription);
            operationToUpdate.setCategory(newOperationCategory);

            addOperation(operationToUpdate, currentIndex);
        }*/
    }

    public Repository getRepository() {
        return repository;
    }

    public OperationCategoryResolverImpl getCategoryResolver() {
        return categoryResolver;
    }

    public RepositoryCreatorDialogView getView() {
        return view;
    }
}
