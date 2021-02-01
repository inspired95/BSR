package com.catchex.repositorycreator.client.control.event;

import com.catchex.models.Repository;
import com.catchex.repositorycreator.client.control.RepositoryCreatorDialogController;
import com.catchex.repositorycreator.client.model.CurrentRepositoryUtil;
import dialogs.EventHandler;
import javafx.event.ActionEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Optional;


public class LoadRepositoryBtnEventHandler
    extends EventHandler<ActionEvent>
{

    private RepositoryCreatorDialogController controller;


    public LoadRepositoryBtnEventHandler( RepositoryCreatorDialogController controller )
    {
        super( "LoadRepository" );
        this.controller = controller;
    }


    @Override
    public void handle( ActionEvent event )
    {
        super.handle( event );
        File selectedRepository = getFileToLoad();
        if( selectedRepository == null )
        {
            actionCancelled();
            return;
        }
        loadRepositoryFromFile( selectedRepository );
    }


    private void loadRepositoryFromFile( File selectedRepository )
    {
        if( selectedRepository != null )
        {
            Repository loadedRepository = null;
            try (ObjectInputStream oi = new ObjectInputStream(
                new FileInputStream( selectedRepository ) ))
            {
                loadedRepository = (Repository)oi.readObject();
            }
            catch( IOException | ClassNotFoundException exp )
            {
                exp.printStackTrace();
            }
            if( loadedRepository != null )
            {
                new CurrentRepositoryUtil().applyRepository( Optional.of( loadedRepository ) );
            }
        }
    }


    private File getFileToLoad()
    {
        Stage window = (Stage)controller.getView().getScene().getWindow();
        FileChooser repositoryToLoadFileChooser = new FileChooser();
        repositoryToLoadFileChooser.setTitle( "Select repository" );
        repositoryToLoadFileChooser.getExtensionFilters()
            .add( new FileChooser.ExtensionFilter( "BSR repository file", "*.bsrrepository" ) );

        return repositoryToLoadFileChooser.showOpenDialog( window );
    }
}
