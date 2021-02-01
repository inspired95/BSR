package com.catchex.repositorycreator.client.control.event;

import com.catchex.repositorycreator.client.control.RepositoryCreatorDialogController;
import com.catchex.repositorycreator.client.model.CurrentRepositoryUtil;
import dialogs.EventHandler;
import javafx.event.ActionEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;


public class SaveRepositoryBtnEventHandler
    extends EventHandler<ActionEvent>
{

    private RepositoryCreatorDialogController controller;


    public SaveRepositoryBtnEventHandler( RepositoryCreatorDialogController controller )
    {
        super( "SaveRepository" );
        this.controller = controller;
    }


    @Override
    public void handle( ActionEvent event )
    {
        super.handle( event );
        File fileToFile = getFileToSave();
        if( fileToFile == null )
        {
            actionCancelled();
            return;
        }
        saveRepositoryToFile( fileToFile );
    }


    private void saveRepositoryToFile( File selectedFile )
    {
        if( selectedFile != null )
        {
            try (FileOutputStream fileOutputStream = new FileOutputStream( selectedFile );
                ObjectOutputStream objectOutputStream = new ObjectOutputStream( fileOutputStream ))
            {
                objectOutputStream.writeObject(
                    new CurrentRepositoryUtil().getRepositoryFromCurrentRepository() );
            }
            catch( IOException e )
            {
                e.printStackTrace();
                //TODO GUI alert
            }
        }
    }


    private File getFileToSave()
    {
        Stage window = (Stage)controller.getView().getScene().getWindow();
        FileChooser repositorySaveFileChooser = new FileChooser();
        repositorySaveFileChooser.setTitle( "Save repository" );
        repositorySaveFileChooser.getExtensionFilters().add(
            new FileChooser.ExtensionFilter( "BSR repository selectedFile", "*.bsrrepository" ) );
        return repositorySaveFileChooser.showSaveDialog( window );
    }
}
