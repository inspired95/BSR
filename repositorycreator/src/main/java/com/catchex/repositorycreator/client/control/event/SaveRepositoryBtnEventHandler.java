package com.catchex.repositorycreator.client.control.event;

import GuiHelpers.Alerts;
import com.catchex.io.writer.ObjectToFileWriter;
import com.catchex.repositorycreator.client.control.RepositoryCreatorDialogController;
import com.catchex.repositorycreator.client.model.CurrentRepositoryUtil;
import dialogs.EventHandler;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.Optional;


public class SaveRepositoryBtnEventHandler
    extends EventHandler<RepositoryCreatorDialogController>
{

    public SaveRepositoryBtnEventHandler( RepositoryCreatorDialogController controller )
    {
        super( "SaveRepository", controller );
    }


    @Override
    public void handle()
    {
        super.handle();
        Stage window = (Stage)getDialogController().getView().getScene().getWindow();
        FileChooser fileChooser = Alerts.getRepositoryFileChooser( "Save repository" );
        Optional<File> fileToFile = Alerts.showSaveFileChooser( window, fileChooser );
        fileToFile.ifPresentOrElse(
            file -> ObjectToFileWriter.getInstance()
                .writeToFile( new CurrentRepositoryUtil().getRepositoryFromCurrentRepository(),
                    file.toPath() ), this::actionCancelled );
    }

}
