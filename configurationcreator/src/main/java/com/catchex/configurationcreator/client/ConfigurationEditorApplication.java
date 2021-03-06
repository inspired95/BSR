package com.catchex.configurationcreator.client;

import com.catchex.configurationcreator.client.control.ConfigurationEditorDialogController;
import javafx.application.Application;
import javafx.stage.Stage;


public class ConfigurationEditorApplication
    extends Application
{
    private final ConfigurationEditorDialogController dialogController;


    public ConfigurationEditorApplication()
    {
        dialogController = new ConfigurationEditorDialogController();
    }


    public static void main( String[] args )
    {
        launch();
    }


    @Override
    public void start( Stage stage ) throws Exception
    {
        dialogController.start( stage );
    }
}
