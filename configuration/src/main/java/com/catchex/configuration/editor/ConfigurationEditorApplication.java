package com.catchex.configuration.editor;

import com.catchex.configuration.editor.control.ConfigurationEditorDialogController;
import javafx.application.Application;
import javafx.stage.Stage;

import static com.catchex.util.Log.initLogging;


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
