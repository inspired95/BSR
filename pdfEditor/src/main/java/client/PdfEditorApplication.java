package client;


import com.catchex.bankstmt.pdfconverters.PKOBankStmtConverter;
import com.catchex.io.reader.PDFReader;
import com.catchex.models.RawOperation;
import com.catchex.models.RawOperationRepository;
import javafx.application.Application;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTreeTableCell;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class PdfEditorApplication extends Application {

    private RawOperationRepository repository;
    private Stage stage;
    private Scene scene;
    private VBox container;
    private Menu bankStatementsMenu;
    private Menu repositoryMenu;
    private MenuItem loadBankStatementsMenuItem;
    private MenuItem loadRepositoryMenuItem;
    private MenuItem saveRepositoryMenuItem;
    private MenuBar menuBar;
    private TreeTableView<RawOperation> treeTableView;
    private final TreeItem<RawOperation> treeRootItem = new TreeItem<>(
            RawOperation.createRoot("Bank statements"));

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy", Locale.US);

    public PdfEditorApplication(){
        repository = new RawOperationRepository();
        initScene();
    }

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start( Stage stage) {
        this.stage = stage;
        loadBankStatementsMenuItemActionEventHandling();
        loadRepositoryMenuItemActionEventHandling();
        saveRepositoryMenuItemActionEventHandling();

        stage.setScene(scene);
        stage.show();
    }


    private void initScene(){
        //Menu

        loadRepositoryMenuItem = new MenuItem("Load repository");
        saveRepositoryMenuItem = new MenuItem("Save repository");
        repositoryMenu = new Menu("Repository");
        repositoryMenu.getItems().add(loadRepositoryMenuItem);
        repositoryMenu.getItems().add(saveRepositoryMenuItem);

        loadBankStatementsMenuItem = new MenuItem("Append bank statements");
        bankStatementsMenu = new Menu("Bank statements");
        bankStatementsMenu.getItems().add(loadBankStatementsMenuItem);

        menuBar = new MenuBar();
        menuBar.getMenus().add(repositoryMenu);
        menuBar.getMenus().add(bankStatementsMenu);

        //TreeTableView
        treeRootItem.setExpanded(true);
        treeTableView = new TreeTableView<>();
        treeTableView.setRoot(treeRootItem);
        buildTreeTableViewColumns();

        //VBox
        container = new VBox(menuBar);
        container.getChildren().add(treeTableView);
        container.setPadding(new Insets(10));

        scene = new Scene(container, 1280, 800);
    }

    private void loadBankStatementsMenuItemActionEventHandling(){
        loadBankStatementsMenuItem.setOnAction(actionEvent -> {
            Stage window = (Stage) stage.getScene().getWindow();
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Resource File");

            List<File> selectedBankStatements = fileChooser.showOpenMultipleDialog(window);

            if (selectedBankStatements != null){
                for (File bankStatement : selectedBankStatements) {
                    Optional<String> read = PDFReader.read(bankStatement.getAbsolutePath());
                    List<RawOperation> convert = Collections.emptyList();
                    if (read.isPresent()){
                        convert = new PKOBankStmtConverter().convert(bankStatement.getName(), read.get());
                    }

                    updateRepository(convert);
                }

                redrawTreeViewTable();
            }
        });
    }

    private void loadRepositoryMenuItemActionEventHandling(){
        loadRepositoryMenuItem.setOnAction(actionEvent -> {
            Stage window = (Stage) stage.getScene().getWindow();
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Resource File");

            File selectedRepository = fileChooser.showOpenDialog(window);

            if (selectedRepository != null){
                RawOperationRepository pr1 = null;
                try(ObjectInputStream oi = new ObjectInputStream(new FileInputStream(new File("myObjects.data")))){

                    pr1 = (RawOperationRepository) oi.readObject();
                }catch (IOException | ClassNotFoundException exp ){
                    exp.printStackTrace();
                }
                repository = pr1;
                }
                redrawTreeViewTable();
        });
    }

    private void saveRepositoryMenuItemActionEventHandling(){
        saveRepositoryMenuItem.setOnAction(actionEvent -> {
            try{
                FileOutputStream f = new FileOutputStream(new File("myObjects.data"));
                ObjectOutputStream o = new ObjectOutputStream(f);
                o.writeObject(repository);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void redrawTreeViewTable() {
        Set<String> keySet = repository.getRawOperations().keySet();
        keySet.stream().map(cat -> this.repository.getRawOperations().get(cat)).filter(rawOps -> rawOps.iterator().hasNext()).forEach(rawOps -> {
            TreeItem<RawOperation> category = new TreeItem<>(
                    RawOperation.createIntervalCategory(rawOps.iterator().next().getDate()));
            rawOps.stream().map(TreeItem::new).forEach(bz -> category.getChildren().add(bz));
            treeRootItem.getChildren().add(category);
        });
        treeRootItem.setExpanded(true);
        treeTableView.setRoot(treeRootItem);
    }

    private void updateRepository( List<RawOperation> convert ) {
        convert.forEach(rawOperation -> {
            String formatedData = rawOperation.getDate().format(formatter);
            Set<RawOperation> rawOperation1 = repository.getRawOperations().get(formatedData);
            if (rawOperation1 == null) {
                Set<RawOperation> list = new HashSet<>();
                list.add(rawOperation);
                repository.getRawOperations().put(formatedData, list);
            } else {
                rawOperation1.add(rawOperation);
                repository.getRawOperations().put(formatedData, rawOperation1);
            }
        });
    }


    private void buildTreeTableViewColumns() {
        // --- date column
        TreeTableColumn<RawOperation, String> dateColumn = buildDateColumn();

        // --- id column
        TreeTableColumn<RawOperation, String> nameColumn = buildIdColumn();

        // --- type column
        TreeTableColumn<RawOperation, String> typeColumn = buildTypeColumn();

        // --- amount column
        TreeTableColumn<RawOperation, String> amountColumn = buildAmountColumn();

        // --- description column
        TreeTableColumn<RawOperation, String> descriptionColumn = buildDescriptionColumn();

        treeTableView.setEditable(true);
        treeTableView.getColumns().setAll(dateColumn, nameColumn, typeColumn, amountColumn, descriptionColumn);
    }

    private TreeTableColumn<RawOperation, String> buildDescriptionColumn() {
        TreeTableColumn<RawOperation, String> descriptionColumn = new TreeTableColumn<>("Description");
        descriptionColumn.setPrefWidth(810);
        descriptionColumn.setCellValueFactory(
                p -> new ReadOnlyObjectWrapper<>(p.getValue().getValue().getDesc().getValue()));

        descriptionColumn.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());
        descriptionColumn.setOnEditCommit( event -> {
            TreeItem<RawOperation> treeItem = event.getRowValue();
            RawOperation operation = treeItem.getValue();
            LocalDate date = treeItem.getParent().getValue().getDate();
            Set<RawOperation> operations = this.repository.getRawOperations().get(date.format(formatter));
            operations.remove(operation);

            String value = event.getNewValue();
            operation.setDesc(value);
            operations.add(operation);
        });
        return descriptionColumn;
    }

    private TreeTableColumn<RawOperation, String> buildAmountColumn() {
        TreeTableColumn<RawOperation, String> amountColumn = new TreeTableColumn<>("Amount");
        amountColumn.setPrefWidth(100);
        amountColumn.setCellValueFactory(
                p -> {
                    Double amount = p.getValue().getValue().getAmount().getValue();
                    if (amount.equals(Double.NaN)) return new ReadOnlyObjectWrapper<>("");
                    return new ReadOnlyObjectWrapper<>(amount.toString());
                });
        return amountColumn;
    }

    private TreeTableColumn<RawOperation, String> buildTypeColumn() {
        TreeTableColumn<RawOperation, String> typeColumn = new TreeTableColumn<>("Type");
        typeColumn.setPrefWidth(100);
        typeColumn.setCellValueFactory(
                p -> new ReadOnlyObjectWrapper<>(p.getValue().getValue().getType().getValue()));
        return typeColumn;
    }

    private TreeTableColumn<RawOperation, String> buildIdColumn() {
        TreeTableColumn<RawOperation, String> nameColumn = new TreeTableColumn<>("ID");
        nameColumn.setPrefWidth(100);
        nameColumn.setCellValueFactory(
                p -> new ReadOnlyObjectWrapper<>(p.getValue().getValue().getID().getValue()));
        return nameColumn;
    }

    private TreeTableColumn<RawOperation, String> buildDateColumn() {
        TreeTableColumn<RawOperation, String> dateColumn = new TreeTableColumn<>("Date");
        dateColumn.setPrefWidth(150);
        dateColumn.setCellValueFactory(
                p -> {
                    LocalDate date = p.getValue().getValue().getDate();
                    if (date.equals(LocalDate.MIN)){
                        return new ReadOnlyObjectWrapper<>("");
                    }
                    if (p.getValue().getValue().getID().getValue().isEmpty()){
                        return new ReadOnlyObjectWrapper<>(date.format(formatter));
                    }
                    return new ReadOnlyObjectWrapper<>(date.toString());
                });
        return dateColumn;
    }


}
