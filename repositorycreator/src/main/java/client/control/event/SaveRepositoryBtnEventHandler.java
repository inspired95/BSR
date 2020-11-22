package client.control.event;

import client.control.RepositoryCreatorDialogController;
import javafx.event.ActionEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class SaveRepositoryBtnEventHandler implements EventHandler<ActionEvent> {

    private RepositoryCreatorDialogController controller;

    public SaveRepositoryBtnEventHandler( RepositoryCreatorDialogController controller ) {
        this.controller = controller;
    }

    @Override
    public void handle( ActionEvent event ) {
        Stage window = (Stage) controller.getView().getScene().getWindow();
        FileChooser repositorySaveFileChooser = new FileChooser();
        repositorySaveFileChooser.setTitle("Save repository");
        repositorySaveFileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("BSR repository selectedFile", "*.bsrrepository"));
        File selectedFile = repositorySaveFileChooser.showSaveDialog(window);
        if (selectedFile != null){
            try{
                FileOutputStream f = new FileOutputStream(selectedFile);
                ObjectOutputStream o = new ObjectOutputStream(f);
                o.writeObject(controller.getRepository());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
