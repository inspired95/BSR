package com.catchex.configurationcreator.client.control.event;

import com.catchex.configurationcreator.client.control.ConfigurationEditorDialogController;
import com.catchex.models.Keyword;
import dialogs.EventHandler;
import guihelpers.Alerts;

import java.util.Optional;


public class RenameKeywordEventHandler
    extends EventHandler<ConfigurationEditorDialogController>
{
    private final Keyword keywordToRename;


    public RenameKeywordEventHandler(
        ConfigurationEditorDialogController controller, Keyword keyword )
    {
        super( "RenameKeyword", controller );
        this.keywordToRename = keyword;
    }


    @Override
    public void handle()
    {
        super.handle();
        Optional<String> newKeywordName = Alerts
            .showAskForStringDialog( "Keyword rename", "Enter new keyword name",
                keywordToRename.getValue() );
        newKeywordName.ifPresent( keywordName -> {
            Keyword keywordToAdd = new Keyword( keywordName );
            getDialogController().getCurrentCategoriesConfiguration()
                .isKeywordAlreadyExists( keywordToAdd ).ifPresentOrElse( keyword -> Alerts
                .showWaringDialog( "New keyword",
                    "Given keyword " + "already exists in categories configuration" ), () -> {
                keywordToRename.setValue( keywordName );
                getDialogController().getView().refreshView();
            } );

        } );
    }
}
