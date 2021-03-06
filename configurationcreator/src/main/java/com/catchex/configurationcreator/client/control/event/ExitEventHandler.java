package com.catchex.configurationcreator.client.control.event;

import com.catchex.configurationcreator.client.control.ConfigurationEditorDialogController;
import dialogs.EventHandler;


public class ExitEventHandler
    extends EventHandler<ConfigurationEditorDialogController>
{

    public ExitEventHandler( ConfigurationEditorDialogController controller )
    {
        super( "Exit", controller );
    }


    @Override
    public void handle()
    {
        super.handle();
        getDialogController().getView().getStage().close();
    }
}
