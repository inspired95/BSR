package client.view;

import client.Repository;
import client.RepositoryCreatorDialogController;
import com.catchex.models.Operation;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTreeTableCell;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class RepositoryCreatorDialogView {
    RepositoryCreatorDialogController controller;

    private Scene scene;
    private MenuItem loadBankStatementsMenuItem;
    private MenuItem loadRepositoryMenuItem;
    private MenuItem saveRepositoryMenuItem;
    private TreeTableView<RepositoryCreatorTreeItem> treeTableView;

    //private RepositoryCreatorViewModel repository;

    public static final DateTimeFormatter intervalTreeItemFormatter = DateTimeFormatter.ofPattern("MMMM yyyy", Locale.US);

    public RepositoryCreatorDialogView(RepositoryCreatorDialogController controller){
        this.controller = controller;
    }

    public void initView(){
        //Menu
        loadRepositoryMenuItem = new MenuItem("Load repository");
        saveRepositoryMenuItem = new MenuItem("Save repository");
        Menu repositoryMenu = new Menu("Repository");
        repositoryMenu.getItems().add(loadRepositoryMenuItem);
        repositoryMenu.getItems().add(saveRepositoryMenuItem);

        loadBankStatementsMenuItem = new MenuItem("Append bank statements");
        Menu bankStatementsMenu = new Menu("Bank statements");
        bankStatementsMenu.getItems().add(loadBankStatementsMenuItem);

        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().add(repositoryMenu);
        menuBar.getMenus().add(bankStatementsMenu);

        //TreeTableView
        TreeItem<RepositoryCreatorTreeItem> treeRootItem = new TreeItem<>(
                new RepositoryCreatorRootTreeItem());
        treeRootItem.setExpanded(true);
        treeTableView = new TreeTableView<>();
        treeTableView.setRoot(treeRootItem);
        buildTreeTableViewColumns();

        //VBox
        VBox container = new VBox(menuBar);
        container.getChildren().add(treeTableView);
        container.setPadding(new Insets(10));

        scene = new Scene(container, 1280, 800);
    }

    private void buildTreeTableViewColumns() {
        treeTableView.setEditable(true);
        treeTableView.setTableMenuButtonVisible(true);
        treeTableView.getColumns().setAll(buildIntervalColumn(), buildDateColumn(), buildIdColumn(),
                buildTypeColumn(), buildCategoryColumn(), buildAmountColumn(), buildDescriptionColumn(),
                buildBankNameColumn(), buildFileNameColumn());
    }

    private TreeTableColumn<RepositoryCreatorTreeItem, String> buildDescriptionColumn() {
        TreeTableColumn<RepositoryCreatorTreeItem, String> descriptionColumn = new TreeTableColumn<>("Description");
        descriptionColumn.setPrefWidth(810);
        descriptionColumn.setCellValueFactory(
                p -> new ReadOnlyObjectWrapper<>(p.getValue().getValue().getDesc()));

        descriptionColumn.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());
        descriptionColumn.setOnEditCommit( event -> {
            controller.handleDescriptionEditEvent(event);
        });
        return descriptionColumn;
    }

    private TreeTableColumn<RepositoryCreatorTreeItem, String> buildCategoryColumn() {
        TreeTableColumn<RepositoryCreatorTreeItem, String> categoryColumn = new TreeTableColumn<>("Category");
        categoryColumn.setPrefWidth(75);
        categoryColumn.setCellValueFactory(
                p -> {
                    if (p.getValue().getValue() instanceof RepositoryCreatorOperationTreeItem){
                        return new ReadOnlyObjectWrapper<>(p.getValue().getValue().getCategory());
                    }
                    return new ReadOnlyObjectWrapper<>("");
                });

        return categoryColumn;
    }

    private TreeTableColumn<RepositoryCreatorTreeItem, String> buildAmountColumn() {
        TreeTableColumn<RepositoryCreatorTreeItem, String> amountColumn = new TreeTableColumn<>("Amount");
        amountColumn.setPrefWidth(100);
        amountColumn.setCellValueFactory(
                p -> {
                    Double amount = p.getValue().getValue().getAmount();
                    if (amount.equals(Double.NaN))  return new ReadOnlyObjectWrapper<>("");
                    return new ReadOnlyObjectWrapper<>(String. format("%.2f", amount));
                });
        amountColumn.setComparator(( s, t1 ) -> {
            if (s.isEmpty() || t1.isEmpty()) {
                return 0;
            }
            Double val1 = Double.parseDouble(s);
            Double val2 = Double.parseDouble(t1);
            return val1.compareTo(val2);
        });
        return amountColumn;
    }

    private TreeTableColumn<RepositoryCreatorTreeItem, String> buildTypeColumn() {
        TreeTableColumn<RepositoryCreatorTreeItem, String> typeColumn = new TreeTableColumn<>("Type");
        typeColumn.setPrefWidth(100);
        typeColumn.setCellValueFactory(
                p -> {
                    if (p.getValue().getValue() instanceof RepositoryCreatorOperationTreeItem){
                        return new ReadOnlyObjectWrapper<>(p.getValue().getValue().getType());
                    }
                    return new ReadOnlyObjectWrapper<>("");
                });
        return typeColumn;
    }

    private TreeTableColumn<RepositoryCreatorTreeItem, String> buildIdColumn() {
        TreeTableColumn<RepositoryCreatorTreeItem, String> nameColumn = new TreeTableColumn<>("ID");
        nameColumn.setPrefWidth(100);
        nameColumn.setCellValueFactory(
                p -> new ReadOnlyObjectWrapper<>(p.getValue().getValue().getID()));
        nameColumn.setVisible(false);
        return nameColumn;
    }

    private TreeTableColumn<RepositoryCreatorTreeItem, String> buildIntervalColumn() {
        TreeTableColumn<RepositoryCreatorTreeItem, String> intervalColumn = new TreeTableColumn<>("Interval");
        intervalColumn.setPrefWidth(150);
        intervalColumn.setCellValueFactory(
                p -> {
                    if (p.getValue().getValue() instanceof RepositoryCreatorIntervalTreeItem){
                        LocalDate date = p.getValue().getValue().getDate();
                        return new ReadOnlyObjectWrapper<>(date.format(intervalTreeItemFormatter));
                    }
                    return new ReadOnlyObjectWrapper<>("");
                });
        intervalColumn.setComparator(( s, t1 ) -> {
            if (s.isEmpty() || t1.isEmpty()) {
                return 0;
            }
            LocalDate localDate1 = LocalDate.parse("01 " + s, DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale.US));
            LocalDate localDate2 = LocalDate.parse("01 " + t1, DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale.US));
            return localDate1.compareTo(localDate2);
        });
        intervalColumn.setSortType(TreeTableColumn.SortType.ASCENDING);
        return intervalColumn;
    }

    private TreeTableColumn<RepositoryCreatorTreeItem, String> buildDateColumn() {
        TreeTableColumn<RepositoryCreatorTreeItem, String> dateColumn = new TreeTableColumn<>("Date");
        dateColumn.setPrefWidth(80);
        dateColumn.setCellValueFactory(
                p -> {
                    if (p.getValue().getValue() instanceof RepositoryCreatorOperationTreeItem ){
                        LocalDate date = p.getValue().getValue().getDate();
                        return new ReadOnlyObjectWrapper<>(date.toString());
                    }
                    return new ReadOnlyObjectWrapper<>("");
                });
        return dateColumn;
    }

    private TreeTableColumn<RepositoryCreatorTreeItem, String> buildBankNameColumn() {
        TreeTableColumn<RepositoryCreatorTreeItem, String> nameColumn = new TreeTableColumn<>("Bank");
        nameColumn.setPrefWidth(100);
        nameColumn.setCellValueFactory(
                p -> new ReadOnlyObjectWrapper<>(p.getValue().getValue().getBankName()));
        nameColumn.setVisible(false);
        return nameColumn;
    }
    private TreeTableColumn<RepositoryCreatorTreeItem, String> buildFileNameColumn() {
        TreeTableColumn<RepositoryCreatorTreeItem, String> nameColumn = new TreeTableColumn<>("File");
        nameColumn.setPrefWidth(100);
        nameColumn.setCellValueFactory(
                p -> new ReadOnlyObjectWrapper<>(p.getValue().getValue().getFileName()));
        nameColumn.setVisible(false);
        return nameColumn;
    }

    public void updateView( Repository repository){
            updateView(repository.getOperations());
    }

    public void updateView( Set<Operation> operations ){
        operations.forEach(operation -> {
            updateView(operation, -1);
        });
    }

    public void updateView( Operation operation, int index ) {
        String currentInterval = operation.getRawOperation().getDate().format(intervalTreeItemFormatter);
        TreeItem<RepositoryCreatorTreeItem> currentRootTreeItem = treeTableView.getRoot();
        ObservableList<TreeItem<RepositoryCreatorTreeItem>> intervals = currentRootTreeItem.getChildren();
        Optional<TreeItem<RepositoryCreatorTreeItem>> intervalTreeItemToAddOperation = findIntervalTreeItemOfOperation(currentInterval, intervals);
        TreeItem<RepositoryCreatorTreeItem> newOperationTreeItem = new TreeItem<>(new RepositoryCreatorOperationTreeItem(operation));
        if (intervalTreeItemToAddOperation.isPresent()){
            if (index != -1 ){
                intervalTreeItemToAddOperation.get().getChildren().add(index, newOperationTreeItem);
                treeTableView.getSelectionModel().select(currentRootTreeItem);
            }
            else{
                intervalTreeItemToAddOperation.get().getChildren().add(newOperationTreeItem);
            }
            intervalTreeItemToAddOperation.get().getValue().increaseAmount(newOperationTreeItem.getValue().getAmount());
        }else{
            TreeItem<RepositoryCreatorTreeItem> newIntervalTreeItem = new TreeItem<>(new RepositoryCreatorIntervalTreeItem(operation.getRawOperation().getDate()));
            newIntervalTreeItem.getValue().increaseAmount(operation.getRawOperation().getAmount());
            currentRootTreeItem.getChildren().add( newIntervalTreeItem );
            newIntervalTreeItem.getChildren().add(newOperationTreeItem);
        }
    }

    private Optional<TreeItem<RepositoryCreatorTreeItem>> findIntervalTreeItemOfOperation( String currentInterval, ObservableList<TreeItem<RepositoryCreatorTreeItem>> intervals ) {
        return intervals.stream().filter(
                        interval -> interval.getValue().getDate().format(intervalTreeItemFormatter).equals(currentInterval)).findFirst();
    }

    public Scene getScene() {
        return scene;
    }

    public MenuItem getLoadBankStatementsMenuItem() {
        return loadBankStatementsMenuItem;
    }

    public MenuItem getLoadRepositoryMenuItem() {
        return loadRepositoryMenuItem;
    }

    public MenuItem getSaveRepositoryMenuItem() {
        return saveRepositoryMenuItem;
    }

    public TreeTableView<RepositoryCreatorTreeItem> getTreeTableView() {
        return treeTableView;
    }
}
