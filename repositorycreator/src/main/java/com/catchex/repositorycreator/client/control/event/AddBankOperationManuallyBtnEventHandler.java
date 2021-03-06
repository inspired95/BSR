package com.catchex.repositorycreator.client.control.event;

import com.catchex.repositorycreator.client.control.RepositoryCreatorDialogController;
import com.catchex.repositorycreator.client.control.dialog.AddBankOperationDialogController;
import dialogs.EventHandler;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class AddBankOperationManuallyBtnEventHandler
    extends EventHandler<RepositoryCreatorDialogController>
{
    private static final Logger logger =
        LoggerFactory.getLogger( AddBankOperationManuallyBtnEventHandler.class );


    public AddBankOperationManuallyBtnEventHandler( RepositoryCreatorDialogController controller )
    {
        super( "AddBankOperationManually", controller );
    }


    @Override
    public void handle()
    {
        super.handle();
        Platform.runLater( () -> {
            try
            {
                new AddBankOperationDialogController().start( new Stage() );
            }
            catch( Exception e )
            {
                logger.error( "Error during application staring {}",
                    ExceptionUtils.getStackTrace( e ) );
            }
        } );

    }
}
