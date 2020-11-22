package client.view;

import client.Repository;
import client.control.RepositoryCreatorDialogController;
import client.view.columns.*;
import client.view.model.AbstractTreeItem;
import client.view.model.IntervalTreeItem;
import client.view.model.OperationTreeItem;
import client.view.model.RootTreeItem;
import com.catchex.models.Operation;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;


public class RepositoryCreatorDialogView
{
    RepositoryCreatorDialogController controller;

    private Scene scene;

    private VBox container;
    private MenuItem loadBankStatementsMenuItem;
    private MenuItem loadRepositoryMenuItem;
    private MenuItem saveRepositoryMenuItem;

    private TreeTableView<AbstractTreeItem> treeTableView;

    public static final DateTimeFormatter intervalTreeItemFormatter =
        DateTimeFormatter.ofPattern( "MMMM yyyy", Locale.US );


    public RepositoryCreatorDialogView( RepositoryCreatorDialogController controller )
    {
        this.controller = controller;
    }


    public void initView()
    {
        //Menu
        loadRepositoryMenuItem = new MenuItem( "Load repository" );
        saveRepositoryMenuItem = new MenuItem( "Save repository" );
        Menu repositoryMenu = new Menu( "Repository" );
        repositoryMenu.getItems().add( loadRepositoryMenuItem );
        repositoryMenu.getItems().add( saveRepositoryMenuItem );

        loadBankStatementsMenuItem = new MenuItem( "Append bank statements" );
        Menu bankStatementsMenu = new Menu( "Actions" );
        bankStatementsMenu.getItems().add( loadBankStatementsMenuItem );

        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().add( repositoryMenu );
        menuBar.getMenus().add( bankStatementsMenu );

        //TreeTableView
        TreeItem<AbstractTreeItem> treeRootItem = new TreeItem<>( new RootTreeItem() );
        treeRootItem.setExpanded( true );
        treeTableView = new TreeTableView<>();
        treeTableView.setRoot( treeRootItem );
        buildTreeTableViewColumns();
        treeTableView.setPrefHeight( 600 );

        //VBox
        container = new VBox( menuBar );
        container.getChildren().add( treeTableView );
        container.setPadding( new Insets( 10 ) );

        scene = new Scene( container, 1280, 800 );
    }


    private void buildTreeTableViewColumns()
    {
        treeTableView.setRowFactory( ttv -> new OperationRowFactory() );
        treeTableView.setEditable( true );
        treeTableView.setTableMenuButtonVisible( true );

        treeTableView.getColumns().setAll(
            new IntervalColumn( intervalTreeItemFormatter, controller ),
            new DateColumn( controller ), new IdColumn( controller ), new TypeColumn( controller ),
            new CategoryColumn( controller ), new AmountColumn( controller ),
            new DescriptionColumn( controller ), new BankNameColumn( controller ),
            new FileNameColumn( controller ) );

    }


    public void updateView( Repository repository )
    {
        updateView( repository.getOperations() );
    }


    public void updateView( Set<Operation> operations )
    {
        operations.forEach( operation -> updateView( operation, -1 ) );
    }


    public void updateView( Operation operation, int index )
    {
        String currentInterval =
            operation.getRawOperation().getDate().format( intervalTreeItemFormatter );
        TreeItem<AbstractTreeItem> currentRootTreeItem = treeTableView.getRoot();
        ObservableList<TreeItem<AbstractTreeItem>> intervals = currentRootTreeItem.getChildren();
        Optional<TreeItem<AbstractTreeItem>> intervalTreeItemToAddOperation =
            findIntervalTreeItemOfOperation( currentInterval, intervals );
        TreeItem<AbstractTreeItem> newOperationTreeItem =
            new TreeItem<>( new OperationTreeItem( operation ) );
        if( intervalTreeItemToAddOperation.isPresent() )
        {
            if( index != -1 )
            {
                intervalTreeItemToAddOperation.get().getChildren()
                    .add( index, newOperationTreeItem );
                treeTableView.getSelectionModel().select( currentRootTreeItem );
            }
            else
            {
                intervalTreeItemToAddOperation.get().getChildren().add( newOperationTreeItem );
            }
            intervalTreeItemToAddOperation.get().getValue()
                .increaseAmount( newOperationTreeItem.getValue().getAmount() );
        }
        else
        {
            TreeItem<AbstractTreeItem> newIntervalTreeItem =
                new TreeItem<>( new IntervalTreeItem( operation.getRawOperation().getDate() ) );
            newIntervalTreeItem.getValue()
                .increaseAmount( operation.getRawOperation().getAmount() );
            currentRootTreeItem.getChildren().add( newIntervalTreeItem );
            newIntervalTreeItem.getChildren().add( newOperationTreeItem );
        }
    }


    private Optional<TreeItem<AbstractTreeItem>> findIntervalTreeItemOfOperation(
        String currentInterval, ObservableList<TreeItem<AbstractTreeItem>> intervals )
    {
        return intervals.stream().filter(
            interval -> interval.getValue().getDate().format( intervalTreeItemFormatter )
                .equals( currentInterval ) ).findFirst();
    }


    public Scene getScene()
    {
        return scene;
    }


    public MenuItem getLoadBankStatementsMenuItem()
    {
        return loadBankStatementsMenuItem;
    }


    public MenuItem getLoadRepositoryMenuItem()
    {
        return loadRepositoryMenuItem;
    }


    public MenuItem getSaveRepositoryMenuItem()
    {
        return saveRepositoryMenuItem;
    }
}
