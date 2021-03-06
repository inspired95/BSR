package com.catchex.configurationcreator.client.control.event;

import GuiHelpers.Alerts;
import com.catchex.configurationcreator.client.control.ConfigurationEditorDialogController;
import com.catchex.models.Category;
import dialogs.EventHandler;

import java.util.Optional;


public class AddCategoryEventHandler
    extends EventHandler<ConfigurationEditorDialogController>
{

    public AddCategoryEventHandler( ConfigurationEditorDialogController controller )
    {
        super( "AddCategory", controller );
    }


    @Override
    public void handle()
    {
        super.handle();
        Optional<String> newCategoryName =
            Alerts.showAskForStringDialog( "New category", "Enter new category's name", "" );
        newCategoryName.ifPresent( categoryName -> {
            Optional<Category> newCategory =
                getDialogController().getCurrentCategoriesConfiguration()
                    .addCategory( categoryName );
            newCategory
                .ifPresentOrElse( category -> getDialogController().getView().refreshView(), () -> {
                    Alerts.showWaringDialog(
                        "New category",
                        "Given category already exists in categories configuration" );
                    handle();
                } );
        } );
    }
}
