package client;


import javafx.application.Application;
import javafx.stage.Stage;

public class RepositoryCreatorApplication extends Application {

    private RepositoryCreatorDialogController dialogController;

    public RepositoryCreatorApplication(){
        dialogController = new RepositoryCreatorDialogController();
    }

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start( Stage stage) {
        dialogController.init(stage);
    }
}
