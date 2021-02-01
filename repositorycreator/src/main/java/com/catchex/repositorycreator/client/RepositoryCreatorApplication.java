package com.catchex.repositorycreator.client;

import com.catchex.configuration.ConfigurationLoader;
import com.catchex.repositorycreator.client.control.RepositoryCreatorDialogController;
import javafx.application.Application;
import javafx.stage.Stage;

import static com.catchex.logging.Log.LOGGER;
import static com.catchex.logging.Log.initLogging;


public class RepositoryCreatorApplication
    extends Application
{

    private RepositoryCreatorDialogController dialogController;


    public RepositoryCreatorApplication()
    {
        dialogController = new RepositoryCreatorDialogController();
    }


    public static void main( String[] args )
    {
        initLogging();
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
            LOGGER.severe( "Error during application staring" );
            e.printStackTrace();
        }
    }
}