package com.catchex.configurationcreator.client.control.event;

import GuiHelpers.Alerts;
import com.catchex.configurationcreator.client.control.ConfigurationEditorDialogController;
import com.catchex.models.Keyword;
import dialogs.EventHandler;

import java.util.Optional;


public class RenameKeywordEventHandler
    extends EventHandler<ConfigurationEditorDialogController>
{
    private final Keyword keyword;


    public RenameKeywordEventHandler(
        ConfigurationEditorDialogController controller, Keyword keyword )
    {
        super( "RenameKeyword", controller );
        this.keyword = keyword;
    }


    @Override
    public void handle()
    {
        super.handle();
        Optional<String> newKeywordName = Alerts
            .showAskForStringDialog( "Keyword rename", "Enter new keyword name",
                keyword.getValue() );
        newKeywordName.ifPresent( keywordName -> {
            Keyword keywordToAdd = new Keyword( keywordName );
            getDialogController().getCurrentCategoriesConfiguration()
                .isKeywordAlreadyExists( keywordToAdd ).ifPresentOrElse( keyword -> Alerts
                .showWaringDialog( "New keyword",
                    "Given keyword " + "already exists in categories configuration" ), () -> {
                keyword.setValue( keywordName );
                getDialogController().getView().refreshView();
            } );

        } );
    }
}
