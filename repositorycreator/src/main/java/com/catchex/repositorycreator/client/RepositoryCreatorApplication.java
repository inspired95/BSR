package com.catchex.repositorycreator.client;

import com.catchex.core.configuration.ConfigurationLoader;
import com.catchex.repositorycreator.client.control.RepositoryCreatorDialogController;
import javafx.application.Application;
import javafx.stage.Stage;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class RepositoryCreatorApplication
    extends Application
{
    private static final Logger logger =
        LoggerFactory.getLogger( RepositoryCreatorApplication.class );
    private RepositoryCreatorDialogController dialogController;


    public RepositoryCreatorApplication()
    {
        dialogController = new RepositoryCreatorDialogController();
    }


    public static void main( String[] args )
    {
        logger.info( "Repository Creator application starting" );
        ConfigurationLoader.loadCategoriesConfiguration();
        launch();
    }


    @Override
    public void stop() throws Exception
    {
        dialogController = null;
        super.stop();
    }


    @Override
    public void start( Stage stage )
    {
        try
        {
            dialogController.start( stage );
        }
        catch( Exception e )
        {
            logger
                .error( "Error during application staring {}", ExceptionUtils.getStackTrace( e ) );
        }
    }
}
