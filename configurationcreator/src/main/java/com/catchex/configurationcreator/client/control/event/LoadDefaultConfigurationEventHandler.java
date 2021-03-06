package com.catchex.configurationcreator.client.control.event;

import GuiHelpers.Alerts;
import com.catchex.configurationcreator.client.control.ConfigurationEditorDialogController;
import com.catchex.core.configuration.ConfigurationUtil;
import dialogs.EventHandler;


public class LoadDefaultConfigurationEventHandler
    extends EventHandler<ConfigurationEditorDialogController>
{

    public LoadDefaultConfigurationEventHandler( ConfigurationEditorDialogController controller )
    {
        super( "LoadDefaultConfiguration", controller );
    }


    @Override
    public void handle()
    {
        super.handle();
        ConfigurationUtil.getDefaultConfiguration().ifPresentOrElse( categoriesConfiguration -> {
            getDialogController().setCurrentCategoriesConfiguration( categoriesConfiguration );
            getDialogController().getDialogView().refreshView();
        }, () -> Alerts.showWaringDialog(
            "Load default configuration", "Error during default configuration loading\n" +
                "Check logs to get to know issue root cause" ) );
    }
}
