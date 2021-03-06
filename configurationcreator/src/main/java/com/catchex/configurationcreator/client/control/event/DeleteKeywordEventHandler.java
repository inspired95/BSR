package com.catchex.configurationcreator.client.control.event;

import com.catchex.configurationcreator.client.control.ConfigurationEditorDialogController;
import com.catchex.models.Category;
import com.catchex.models.Keyword;
import dialogs.EventHandler;


public class DeleteKeywordEventHandler
    extends EventHandler<ConfigurationEditorDialogController>
{
    private final Category category;
    private final Keyword keyword;


    public DeleteKeywordEventHandler(
        ConfigurationEditorDialogController controller, Category category, Keyword keyword )
    {
        super( "DeleteKeyword", controller );
        this.category = category;
        this.keyword = keyword;
    }


    @Override
    public void handle()
    {
        super.handle();
        category.getKeywords().remove( keyword );
        getDialogController().getView().refreshView();
    }
}
