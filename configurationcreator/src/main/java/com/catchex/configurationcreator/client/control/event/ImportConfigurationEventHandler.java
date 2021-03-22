package com.catchex.configurationcreator.client.control.event;

import com.catchex.configurationcreator.client.control.ConfigurationEditorDialogController;
import com.catchex.core.configuration.ConfigurationUtil;
import dialogs.EventHandler;
import guihelpers.Alerts;
import guihelpers.FileChoosers;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.Optional;


public class ImportConfigurationEventHandler
    extends EventHandler<ConfigurationEditorDialogController>
{
    public ImportConfigurationEventHandler( ConfigurationEditorDialogController controller )
    {
        super( "ImportConfiguration", controller );
    }


    @Override
    public void handle()
    {
        super.handle();
        FileChooser fileChooser =
            FileChoosers.getConfigurationFileChooser( "Select configuration to import" );
        Optional<File> configurationFileToImport =
            Alerts.showOpenFileChooser( getDialogController().getView().getStage(), fileChooser );
        configurationFileToImport.flatMap(
            configurationFile -> ConfigurationUtil.getConfiguration( configurationFile.toPath() ) )
            .ifPresent( categoriesConfiguration -> {
                getDialogController().setCurrentCategoriesConfiguration( categoriesConfiguration );
                getDialogController().getView().refreshView();
            } );
    }
}
