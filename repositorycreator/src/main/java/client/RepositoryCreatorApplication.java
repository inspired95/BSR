package client;


import client.control.RepositoryCreatorDialogController;
import javafx.application.Application;
import javafx.stage.Stage;

import static com.catchex.configuration.ConfigurationLoader.loadConfiguration;
import static com.catchex.util.Log.initLogging;

public class RepositoryCreatorApplication extends Application {

    private RepositoryCreatorDialogController dialogController;

    public RepositoryCreatorApplication(){
        dialogController = new RepositoryCreatorDialogController();
    }

    public static void main(String[] args) {
        initLogging();
        loadConfiguration( true );
        launch();
    }

    @Override
    public void start( Stage stage) {
        dialogController.init(stage);
    }
}
