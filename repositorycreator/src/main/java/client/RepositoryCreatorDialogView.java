package client;

import com.catchex.models.RawOperation;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTreeTableCell;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Set;

public class RepositoryCreatorDialogView {
    RepositoryCreatorDialogController controller;

    private Scene scene;
    private MenuItem loadBankStatementsMenuItem;
    private MenuItem loadRepositoryMenuItem;
    private MenuItem saveRepositoryMenuItem;
    private TreeTableView<RawOperation> treeTableView;
    private final TreeItem<RawOperation> treeRootItem = new TreeItem<>(
            RawOperation.createRoot("Bank statements"));

    private final DateTimeFormatter bankStatementsCategoriesFormatter = DateTimeFormatter.ofPattern("MMMM yyyy", Locale.US);

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
        // --- interval column
        TreeTableColumn<RawOperation, String> intervalColumn = buildIntervalColumn();

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
        treeTableView.getColumns().setAll(intervalColumn, dateColumn, nameColumn, typeColumn, amountColumn, descriptionColumn);
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
            Set<RawOperation> operations = controller.getRepository().getRawOperations().get(date.format(bankStatementsCategoriesFormatter));
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

    private TreeTableColumn<RawOperation, String> buildIntervalColumn() {
        TreeTableColumn<RawOperation, String> intervalColumn = new TreeTableColumn<>("Interval");
        intervalColumn.setPrefWidth(150);
        intervalColumn.setCellValueFactory(
                p -> {
                    LocalDate date = p.getValue().getValue().getDate();
                    if (date.equals(LocalDate.MIN)){
                        return new ReadOnlyObjectWrapper<>("");
                    }
                    if (p.getValue().getValue().getID().getValue().isEmpty()){
                        return new ReadOnlyObjectWrapper<>(date.format(bankStatementsCategoriesFormatter));
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
        return intervalColumn;
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
                    if (!p.getValue().getValue().isValid()){
                        return new ReadOnlyObjectWrapper<>("");
                    }
                    return new ReadOnlyObjectWrapper<>(date.toString());
                });
        return dateColumn;
    }

    public void redrawTreeViewTable() {
        Set<String> keySet = controller.getRepository().getRawOperations().keySet();
        TreeItem<RawOperation> newRootTreeItem = new TreeItem<>(RawOperation.createRoot("Bank statements"));
        keySet.stream().map(interval -> controller.getRepository().getRawOperations().get(interval)).filter(rawOps -> rawOps.iterator().hasNext()).forEach(rawOps -> {
            TreeItem<RawOperation> interval = new TreeItem<>(
                    RawOperation.createIntervalTreeItem(rawOps.iterator().next().getDate()));
            double intervalSum = 0.0;
            for (RawOperation rawOp : rawOps) {
                interval.getChildren().add(new TreeItem<>(rawOp));
                intervalSum += rawOp.getAmount().getValue();
            }
            interval.getValue().setAmount(intervalSum);
            newRootTreeItem.getChildren().add(interval);
        });
        newRootTreeItem.setExpanded(true);
        treeTableView.setRoot(newRootTreeItem);
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

    public DateTimeFormatter getBankStatementsCategoriesFormatter() {
        return bankStatementsCategoriesFormatter;
    }
}
