package com.catchex.repositorycreator.client.control;

import com.catchex.models.Category;
import com.catchex.models.Operation;
import com.catchex.models.Repository;
import com.catchex.repositorycreator.categoryresolving.OperationCategoryResolverImpl;
import com.catchex.repositorycreator.client.control.event.*;
import com.catchex.repositorycreator.client.view.RepositoryCreatorDialogView;
import com.catchex.repositorycreator.client.view.model.AbstractTreeItem;
import com.catchex.repositorycreator.client.view.model.OperationTreeItem;
import javafx.scene.control.Alert;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.stage.Stage;

import java.util.Optional;
import java.util.Set;


public class RepositoryCreatorDialogController
{

    private Repository repository;
    private RepositoryCreatorDialogView view;
    private OperationCategoryResolverImpl categoryResolver;

    private CategoriesConfigurationChangeListener categoriesConfigurationChangeListener;


    public RepositoryCreatorDialogController()
    {
        this.repository = new Repository();
        this.view = new RepositoryCreatorDialogView( this );
        this.categoryResolver = new OperationCategoryResolverImpl();
    }


    public void init( Stage stage )
    {
        this.view.initView( stage );
        initMenuBtnsEventHandler();
        this.categoriesConfigurationChangeListener =
            new CategoriesConfigurationChangeListener( this );
        stage.show();
    }


    public void addOperations( Set<Operation> operations )
    {
        repository.addOperations( operations );
        view.updateView( operations );
    }


    public void updateCategory(
        OperationTreeItem operationTreeItem, String newDescription )
    {
        Category newCategory = resolveCategoryForDescription( newDescription );

        updateModel( operationTreeItem.getOperation(), newDescription, newCategory );

        updateView( operationTreeItem, newDescription, newCategory );
    }


    public void handleConfigurationChange()
    {
        for( TreeItem<AbstractTreeItem> interval : view.getTreeTableView().getRoot().getChildren() )
        {
            for( TreeItem<AbstractTreeItem> operation : interval.getChildren() )
            {
                OperationTreeItem operationTreeItem = (OperationTreeItem)operation.getValue();
                updateCategory(
                    operationTreeItem,
                    operationTreeItem.getOperation().getRawOperation().getDesc() );
            }
        }
    }


    public Repository getRepository()
    {
        return repository;
    }


    public RepositoryCreatorDialogView getView()
    {
        return view;
    }


    public void showAlert( Alert.AlertType type, String title, String header, String content )
    {
        Alert alert = new Alert( type );
        alert.setTitle( title );
        alert.setHeaderText( header );
        alert.setContentText( content );

        alert.showAndWait();
    }


    public void onApplicationClose()
    {
        categoriesConfigurationChangeListener.stopListen();
        categoriesConfigurationChangeListener = null;
        this.repository = null;
        this.view = null;
        this.categoryResolver = null;
    }


    private void loadBankStatementsMenuItemActionEventHandling()
    {
        /*Platform.runLater(() -> {
                new PdfEditorApplication().start(new Stage());
            });
            stage.close();*/
        view.getLoadBankStatementsMenuItem()
            .setOnAction( new LoadBankStatementsBtnEventHandler( this )::handle );
    }


    private void initMenuBtnsEventHandler()
    {
        loadBankStatementsMenuItemActionEventHandling();
        loadRepositoryMenuItemActionEventHandling();
        addBankOperationMenuItemActionEventHandling();
        saveRepositoryMenuItemActionEventHandling();
        generateReportMenuItemActionEventHandling();
    }


    private void loadRepositoryMenuItemActionEventHandling()
    {
        view.getLoadRepositoryMenuItem()
            .setOnAction( new LoadRepositoryBtnEventHandler( this )::handle );
    }


    private void generateReportMenuItemActionEventHandling()
    {
        view.getGenerateReportMenuItem()
            .setOnAction( new GenerateReportBtnEventHandler( repository, view )::handle );
    }


    private void saveRepositoryMenuItemActionEventHandling()
    {
        view.getSaveRepositoryMenuItem()
            .setOnAction( new SaveRepositoryBtnEventHandler( this )::handle );
    }


    private void updateView(
        OperationTreeItem operationTreeItem, String newDescription, Category newCategory )
    {
        operationTreeItem.setDesc( newDescription );
        operationTreeItem.setCategory( newCategory );
        view.refresh();
    }


    private void updateModel(
        Operation operation, String newDescription, Category newCategory )
    {
        operation.getRawOperation().setDesc( newDescription );
        operation.setCategory( newCategory );
    }


    private Category resolveCategoryForDescription( String description )
    {
        return categoryResolver.resolve( description );
    }


    private String getSortedByColumnName()
    {
        Optional<String> sortedByColumn =
            getView().getTreeTableView().getSortOrder().stream().map( TreeTableColumn::getText )
                .findFirst();
        if( sortedByColumn.isPresent() )
        {
            return sortedByColumn.get();
        }
        else
        {
            return "";
        }
    }


    private void addBankOperationMenuItemActionEventHandling()
    {
        view.getAddBankOperationMenuItem()
            .setOnAction( new AddBankOperationBtnEventHandler( this )::handle );
    }
}
