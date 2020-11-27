package client.view;

import client.Constants;
import client.Repository;
import client.control.RepositoryCreatorDialogController;
import client.view.model.AbstractTreeItem;
import client.view.model.IntervalTreeItem;
import client.view.model.OperationTreeItem;
import com.catchex.models.Operation;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;


public class RepositoryCreatorDialogView
{
    RepositoryCreatorDialogController controller;
    private Scene scene;
    private MenuItem loadBankStatementsMenuItem;
    private MenuItem loadRepositoryMenuItem;
    private MenuItem saveRepositoryMenuItem;
    private TreeTableView<AbstractTreeItem> treeTableView;


    public RepositoryCreatorDialogView( RepositoryCreatorDialogController controller )
    {
        this.controller = controller;
    }


    public void initView( Stage stage )
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
        treeTableView = new TreeTableViewBuilder( controller ).build();

        //VBox
        VBox container = new VBox( menuBar );
        container.getChildren().add( treeTableView );
        container.setPadding( new Insets( 10 ) );

        scene = new Scene( container, 1280, 800 );
        stage.setScene( scene );
    }


    public void updateView( Repository repository )
    {
        updateView( repository.getOperations() );
    }


    public void updateView( Set<Operation> operations )
    {
        operations.forEach( this::updateView );
    }


    public void updateView( Operation operation )
    {
        Optional<TreeItem<AbstractTreeItem>> intervalTreeItemToPutOperation =
            findIntervalTreeItemOfOperation( operation.getRawOperation().getDate() );

        TreeItem<AbstractTreeItem> treeItemToPut =
            new TreeItem<>( new OperationTreeItem( operation ) );

        if( intervalTreeItemToPutOperation.isPresent() ) //interval for particular operation exists
        {
            putOperationTreeItem( intervalTreeItemToPutOperation.get(), treeItemToPut );
            increaseIntervalAmount(
                intervalTreeItemToPutOperation.get(), treeItemToPut.getValue().getAmount() );
        }
        else //there is no interval for particular operation yet
        {
            TreeItem<AbstractTreeItem> newIntervalTreeItem =
                generateNewInterval( treeItemToPut.getValue().getDate(),
                    treeItemToPut.getValue().getAmount() );

            putOperationTreeItem( newIntervalTreeItem, treeItemToPut );

        }
    }


    public void refresh()
    {
        treeTableView.refresh();
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


    private Optional<TreeItem<AbstractTreeItem>> findIntervalTreeItemOfOperation(
        LocalDate date )
    {
        String intervalToFind = getIntervalName( date );

        return treeTableView.getRoot().getChildren().stream().filter(
            interval -> getIntervalName( interval.getValue().getDate() ).equals( intervalToFind ) )
            .findFirst();
    }


    private void putOperationTreeItem(
        TreeItem<AbstractTreeItem> intervalTreeItemToPutOperation,
        TreeItem<AbstractTreeItem> treeItemToPut )
    {
        putTreeItem( intervalTreeItemToPutOperation, treeItemToPut );
        treeTableView.getSelectionModel().select( treeTableView.getRoot() );
    }


    private void increaseIntervalAmount(
        TreeItem<AbstractTreeItem> intervalTreeItem, double value )
    {
        intervalTreeItem.getValue().increaseAmount( value );
    }


    private TreeItem<AbstractTreeItem> generateNewInterval(
        LocalDate date, double initialAmount )
    {
        TreeItem<AbstractTreeItem> newIntervalTreeItem =
            prepareNewIntervalTreeItem( date, initialAmount );
        putTreeItem( treeTableView.getRoot(), newIntervalTreeItem );
        return newIntervalTreeItem;
    }


    private void putTreeItem(
        TreeItem<AbstractTreeItem> parent, TreeItem<AbstractTreeItem> child )
    {
        parent.getChildren().add( child );
    }


    private TreeItem<AbstractTreeItem> prepareNewIntervalTreeItem(
        LocalDate date, double initialAmount )
    {
        TreeItem<AbstractTreeItem> newIntervalTreeItem =
            new TreeItem<>( new IntervalTreeItem( date ) );
        increaseIntervalAmount( newIntervalTreeItem, initialAmount );

        return newIntervalTreeItem;
    }


    private String getIntervalName( LocalDate date )
    {
        return date.format( Constants.intervalTreeItemFormatter );
    }
}
