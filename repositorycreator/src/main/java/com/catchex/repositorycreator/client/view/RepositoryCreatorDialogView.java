package com.catchex.repositorycreator.client.view;

import com.catchex.models.CurrentOperation;
import com.catchex.repositorycreator.client.control.RepositoryCreatorDialogController;
import com.catchex.repositorycreator.client.model.repository.CurrentRepositoryHolder;
import com.catchex.repositorycreator.client.view.model.AbstractTreeItem;
import com.catchex.repositorycreator.client.view.model.IntervalTreeItem;
import com.catchex.repositorycreator.client.view.model.OperationTreeItem;
import com.catchex.util.Constants;
import com.catchex.util.Util;
import dialogs.DialogView;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.Optional;


public class RepositoryCreatorDialogView
    extends DialogView
{
    private static final Logger logger =
        LoggerFactory.getLogger( RepositoryCreatorDialogView.class );

    RepositoryCreatorDialogController controller;
    private Scene scene;
    private MenuItem loadBankStatementsMenuItem;
    private MenuItem addBankOperationManuallyMenuItem;
    private MenuItem loadRepositoryMenuItem;
    private MenuItem saveRepositoryMenuItem;
    private MenuItem generateReportMenuItem;
    private MenuItem editConfigurationMenuItem;
    private TreeTableView<AbstractTreeItem> treeTableView;


    public RepositoryCreatorDialogView( RepositoryCreatorDialogController controller )
    {
        this.controller = controller;
    }


    public void initView( Stage stage )
    {
        super.initView( stage );
        //Menu
        loadRepositoryMenuItem = new MenuItem( "Load repository" );
        saveRepositoryMenuItem = new MenuItem( "Save repository" );
        Menu repositoryMenu = new Menu( "Repository" );
        repositoryMenu.getItems().add( loadRepositoryMenuItem );
        repositoryMenu.getItems().add( saveRepositoryMenuItem );

        loadBankStatementsMenuItem = new MenuItem( "Append bank statements" );
        addBankOperationManuallyMenuItem = new MenuItem( "Add bank operation manually" );
        generateReportMenuItem = new MenuItem( "Generate report" );
        editConfigurationMenuItem = new MenuItem( "Edit configuration" );
        Menu bankStatementsMenu = new Menu( "Actions" );
        bankStatementsMenu.getItems().add( loadBankStatementsMenuItem );
        bankStatementsMenu.getItems().add( addBankOperationManuallyMenuItem );
        bankStatementsMenu.getItems().add( generateReportMenuItem );
        bankStatementsMenu.getItems().add( editConfigurationMenuItem );

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


    @Override
    public void refreshView()
    {
        //implement if needed
    }


    public void applyCurrentRepository()
    {
        logger.debug( "Current repository applying" );
        getTreeTableView().getRoot().getChildren().clear();
        CurrentRepositoryHolder.getInstance().get().getOperations()
            .forEach( this::applyCurrentOperation );
    }


    public void applyCurrentOperation( CurrentOperation operation )
    {
        logger.trace( "CurrentOperation {} applying", operation );
        Optional<TreeItem<AbstractTreeItem>> intervalTreeItemToPutOperation =
            findIntervalTreeItemOfOperation( operation.getOperation().getRawOperation().getDate() );

        TreeItem<AbstractTreeItem> treeItemToPut =
            new TreeItem<>( new OperationTreeItem( operation ) );
        operation.addCategoryChangeListener( treeItemToPut.getValue() );

        if( intervalTreeItemToPutOperation.isPresent() ) //interval for particular operation exists
        {
            logger.trace( "Interval tree item found" );
            putOperationTreeItem( intervalTreeItemToPutOperation.get(), treeItemToPut );
            increaseIntervalAmount(
                intervalTreeItemToPutOperation.get(), treeItemToPut.getValue().getAmount() );
        }
        else //there is no interval for particular operation yet
        {
            logger.trace( "Interval tree item not found" );
            TreeItem<AbstractTreeItem> newIntervalTreeItem =
                generateNewInterval( treeItemToPut.getValue().getDate(),
                    treeItemToPut.getValue().getAmount() );

            putOperationTreeItem( newIntervalTreeItem, treeItemToPut );

        }
    }


    public void refresh()
    {
        logger.debug( "TreeTableView refresh" );
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


    public MenuItem getAddBankOperationManuallyMenuItem()
    {
        return addBankOperationManuallyMenuItem;
    }


    public MenuItem getGenerateReportMenuItem()
    {
        return generateReportMenuItem;
    }


    public MenuItem getLoadRepositoryMenuItem()
    {
        return loadRepositoryMenuItem;
    }


    public MenuItem getSaveRepositoryMenuItem()
    {
        return saveRepositoryMenuItem;
    }


    public MenuItem getEditConfigurationMenuItem()
    {
        return editConfigurationMenuItem;
    }


    public TreeTableView<AbstractTreeItem> getTreeTableView()
    {
        return treeTableView;
    }


    private Optional<TreeItem<AbstractTreeItem>> findIntervalTreeItemOfOperation(
        LocalDate date )
    {
        String intervalToFind = Util.getIntervalName( date );

        logger.trace( "Interval tree item {} to find", intervalToFind );

        return treeTableView.getRoot().getChildren().stream().filter(
            interval -> Util.getIntervalName( interval.getValue().getDate() )
                .equals( intervalToFind ) ).findFirst();
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
        logger.trace( "Putting tree item {} under parent {}", child, parent );
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
