package client.control;

import client.Repository;
import client.control.event.LoadBankStatementsBtnEventHandler;
import client.control.event.LoadRepositoryBtnEventHandler;
import client.control.event.SaveRepositoryBtnEventHandler;
import client.view.RepositoryCreatorDialogView;
import client.view.model.OperationTreeItem;
import com.catchex.bankstmt.categories.OperationCategoryResolverImpl;
import com.catchex.configuration.Configuration;
import com.catchex.models.Category;
import com.catchex.models.Operation;
import javafx.stage.Stage;

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
            Configuration.getCategoriesConfiguration().getCategories() );
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


    private void initMenuBtnsEventHandler()
    {
        loadBankStatementsMenuItemActionEventHandling();
        loadRepositoryMenuItemActionEventHandling();
        saveRepositoryMenuItemActionEventHandling();
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


    private void loadRepositoryMenuItemActionEventHandling()
    {
        view.getLoadRepositoryMenuItem()
            .setOnAction( new LoadRepositoryBtnEventHandler( this )::handle );
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
}
