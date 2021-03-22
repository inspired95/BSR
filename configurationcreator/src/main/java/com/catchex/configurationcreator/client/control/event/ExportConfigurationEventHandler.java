package com.catchex.configurationcreator.client.control.event;

import com.catchex.configurationcreator.client.control.ConfigurationEditorDialogController;
import com.catchex.core.configuration.ConfigurationUtil;
import dialogs.EventHandler;
import guihelpers.Alerts;
import guihelpers.FileChoosers;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.Optional;


public class ExportConfigurationEventHandler
    extends EventHandler<ConfigurationEditorDialogController>
{

    public ExportConfigurationEventHandler( ConfigurationEditorDialogController controller )
    {
        super( "ExportConfiguration", controller );
    }


    @Override
    public void handle()
    {
        super.handle();
        FileChooser fileChooser = FileChoosers
            .getConfigurationFileChooser( "Select file to export current " + "configuration" );
        Optional<File> configurationFileToImport =
            Alerts.showSaveFileChooser( getDialogController().getView().getStage(), fileChooser );
        configurationFileToImport
            .ifPresentOrElse( configurationFile -> ConfigurationUtil.saveConfiguration(
                getDialogController().getCurrentCategoriesConfiguration(),
                configurationFile.toPath() ), this::actionCancelled );
    }
}
