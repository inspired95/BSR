package com.catchex.configurationcreator.client;

import com.catchex.configurationcreator.client.control.ConfigurationEditorDialogController;
import javafx.application.Application;
import javafx.stage.Stage;

import static com.catchex.logging.Log.initLogging;


public class ConfigurationEditorApplication
    extends Application
{
    private ConfigurationEditorDialogController dialogController;


    public ConfigurationEditorApplication()
    {
        dialogController = new ConfigurationEditorDialogController();
    }


    public static void main( String[] args )
    {
        initLogging();
        launch();
    }


    @Override
    public void start( Stage stage ) throws Exception
    {
        dialogController.init( stage );
    }
}
