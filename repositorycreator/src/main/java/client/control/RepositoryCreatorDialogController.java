package client.control;

import client.Repository;
import client.control.event.LoadBankStatementsBtnEventHandler;
import client.control.event.LoadRepositoryBtnEventHandler;
import client.control.event.SaveRepositoryBtnEventHandler;
import client.view.RepositoryCreatorDialogView;
import com.catchex.bankstmt.categories.OperationCategoryResolverImpl;
import com.catchex.configuration.Configuration;
import com.catchex.models.Operation;
import javafx.stage.Stage;

import java.util.Set;

import static com.catchex.util.Constants.PKO;


public class RepositoryCreatorDialogController
{

    private Repository repository;
    private RepositoryCreatorDialogView view;
    private OperationCategoryResolverImpl categoryResolver;

    private Stage stage;

    private String[] supportedBanks = { PKO };


    public RepositoryCreatorDialogController()
    {
        this.repository = new Repository();
        this.view = new RepositoryCreatorDialogView( this );
        this.categoryResolver = new OperationCategoryResolverImpl(
            Configuration.getCategoriesConfiguration().getCategories() );
    }


    public void init( Stage stage )
    {
        this.stage = stage;
        view.initView();
        initMenuBtnsEventHandler();

        stage.setScene( view.getScene() );
        stage.show();
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


    public void addOperations( Set<Operation> operations )
    {
        repository.addOperations( operations );
        view.updateView( operations );
    }


    public void addOperation( Operation operation, int index )
    {
        repository.addOperation( operation );
        view.updateView( operation, index );
    }


    public Repository getRepository()
    {
        return repository;
    }


    public OperationCategoryResolverImpl getCategoryResolver()
    {
        return categoryResolver;
    }


    public RepositoryCreatorDialogView getView()
    {
        return view;
    }


    public String[] getSupportedBanks()
    {
        return supportedBanks;
    }
}
