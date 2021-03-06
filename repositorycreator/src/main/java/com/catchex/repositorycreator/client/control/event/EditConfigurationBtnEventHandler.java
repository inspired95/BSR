package com.catchex.repositorycreator.client.control.event;

import com.catchex.configurationcreator.client.control.ConfigurationEditorDialogController;
import com.catchex.repositorycreator.client.control.RepositoryCreatorDialogController;
import dialogs.EventHandler;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class EditConfigurationBtnEventHandler
    extends EventHandler<RepositoryCreatorDialogController>
{
    private static final Logger logger =
        LoggerFactory.getLogger( EditConfigurationBtnEventHandler.class );


    public EditConfigurationBtnEventHandler( RepositoryCreatorDialogController controller )
    {
        super( "EditConfiguration", controller );
    }


    @Override
    public void handle()
    {
        super.handle();
        Platform.runLater( () -> {
            try
            {
                new ConfigurationEditorDialogController().start( new Stage() );
            }
            catch( Exception e )
            {
                logger.error( "Error during application staring {}",
                    ExceptionUtils.getStackTrace( e ) );
            }
        } );

    }
}
