package client;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PdfEditorApplication extends Application {
    @Override
    public void start( Stage stage) {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/PdfEditorFrame.fxml"));
        }catch (Exception ex){
            ex.printStackTrace();
            root = null;
        }
        stage.setScene(new Scene(root));
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
