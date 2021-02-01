package com.catchex.repositorycreator.client.control.event;

import com.catchex.configuration.editor.ConfigurationEditorApplication;
import dialogs.EventHandler;
import javafx.application.Platform;
import javafx.stage.Stage;


public class EditConfigurationBtnEventHandler
    extends EventHandler
{
    public EditConfigurationBtnEventHandler()
    {
        super( "EditConfiguration" );
    }


    @Override
    public void handle( Object event )
    {
        super.handle( event );
        Platform.runLater( () -> {
            try
            {
                new ConfigurationEditorApplication().start( new Stage() );
            }
            catch( Exception e )
            {
                e.printStackTrace();
            }
        } );

    }
}
