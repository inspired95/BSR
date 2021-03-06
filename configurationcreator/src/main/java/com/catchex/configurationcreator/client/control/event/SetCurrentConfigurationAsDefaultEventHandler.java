package com.catchex.configurationcreator.client.control.event;

import com.catchex.configurationcreator.client.control.ConfigurationEditorDialogController;
import com.catchex.core.configuration.ConfigurationUtil;
import dialogs.EventHandler;


public class SetCurrentConfigurationAsDefaultEventHandler
    extends EventHandler<ConfigurationEditorDialogController>
{

    public SetCurrentConfigurationAsDefaultEventHandler(
        ConfigurationEditorDialogController controller )
    {
        super( "CurrentConfigurationAsDefault", controller );
    }


    @Override
    public void handle()
    {
        ConfigurationUtil
            .setConfigurationAsDefault( getDialogController().getCurrentCategoriesConfiguration() );
    }
}
