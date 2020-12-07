package client.control.event;

import client.Repository;
import client.control.RepositoryCreatorDialogController;
import javafx.event.ActionEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;


public class LoadRepositoryBtnEventHandler
    implements EventHandler<ActionEvent>
{

    private RepositoryCreatorDialogController controller;


    public LoadRepositoryBtnEventHandler( RepositoryCreatorDialogController controller )
    {
        this.controller = controller;
    }


    @Override
    public void handle( ActionEvent event )
    {
        File selectedRepository = getFileToLoad();

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
                controller.getView().updateView( loadedRepository );
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
