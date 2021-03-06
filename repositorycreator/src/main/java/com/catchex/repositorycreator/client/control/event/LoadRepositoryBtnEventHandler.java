package com.catchex.repositorycreator.client.control.event;

import com.catchex.io.reader.RepositoryReader;
import com.catchex.models.Repository;
import com.catchex.repositorycreator.client.control.RepositoryCreatorDialogController;
import com.catchex.repositorycreator.client.model.CurrentRepositoryUtil;
import dialogs.EventHandler;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Optional;


public class LoadRepositoryBtnEventHandler
    extends EventHandler<RepositoryCreatorDialogController>
{
    private static final Logger logger =
        LoggerFactory.getLogger( LoadRepositoryBtnEventHandler.class );


    public LoadRepositoryBtnEventHandler( RepositoryCreatorDialogController controller )
    {
        super( "LoadRepository", controller );
    }


    @Override
    public void handle()
    {
        super.handle();
        File selectedRepository = getFileToLoad();
        if( selectedRepository == null )
        {
            actionCancelled();
            return;
        }
        Optional<Repository> loadedRepository =
            RepositoryReader.loadRepository( selectedRepository );
        loadedRepository.ifPresentOrElse(
            repository -> new CurrentRepositoryUtil().applyRepository( repository ), () -> logger
                .error( "Error while loading repository from file {}",
                    selectedRepository.getName() ) );

    }


    private File getFileToLoad()
    {
        Stage window = getDialogController().getDialogView().getStage();
        FileChooser repositoryToLoadFileChooser = new FileChooser();
        repositoryToLoadFileChooser.setTitle( "Select repository" );
        repositoryToLoadFileChooser.getExtensionFilters()
            .add( new FileChooser.ExtensionFilter( "BSR repository file", "*.bsrrepository" ) );

        return repositoryToLoadFileChooser.showOpenDialog( window );
    }
}
