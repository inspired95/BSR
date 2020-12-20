package client.control;

import client.Repository;
import client.control.event.*;
import client.view.RepositoryCreatorDialogView;
import client.view.model.OperationTreeItem;
import com.catchex.bankstmt.categories.OperationCategoryResolverImpl;
import com.catchex.models.Category;
import com.catchex.models.Configuration;
import com.catchex.models.Operation;
import javafx.scene.control.Alert;
import javafx.scene.control.TreeTableColumn;
import javafx.stage.Stage;

import java.util.Optional;
import java.util.Set;


public class RepositoryCreatorDialogController
{

    private Repository repository;
    private RepositoryCreatorDialogView view;
    private OperationCategoryResolverImpl categoryResolver;


    public RepositoryCreatorDialogController()
    {
        this.repository = new Repository();
        this.view = new RepositoryCreatorDialogView( this );
        this.categoryResolver = new OperationCategoryResolverImpl(
            Configuration.getInstance().getCategoriesConfiguration().getCategories() );
    }


    public void init( Stage stage )
    {
        view.initView( stage );
        initMenuBtnsEventHandler();

        stage.show();
    }


    public void addOperations( Set<Operation> operations )
    {
        repository.addOperations( operations );
        view.updateView( operations );
    }


    public void handleDescriptionChange(
        OperationTreeItem operationTreeItem, String newDescription )
    {
        Category newCategory = resolveCategoryForDescription( newDescription );

        updateModel( operationTreeItem.getOperation(), newDescription, newCategory );

        updateView( operationTreeItem, newDescription, newCategory );
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


    private Category resolveCategoryForDescription( String newOperationDescription )
    {
        return categoryResolver.resolve( newOperationDescription );
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
