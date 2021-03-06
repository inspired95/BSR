package com.catchex.configurationcreator.client.control.event;

import GuiHelpers.Alerts;
import com.catchex.configurationcreator.client.control.ConfigurationEditorDialogController;
import com.catchex.models.Category;
import dialogs.EventHandler;

import java.util.Optional;


public class RenameCategoryEventHandler
    extends EventHandler<ConfigurationEditorDialogController>
{
    private final Category category;


    public RenameCategoryEventHandler(
        ConfigurationEditorDialogController controller, Category category )
    {
        super( "RenameCategory", controller );
        this.category = category;
    }


    @Override
    public void handle()
    {
        super.handle();
        Optional<String> newCategoryName = Alerts.showAskForStringDialog( "Category rename",
            "Enter new " + "name for " + category.getCategoryName(), "" );
        newCategoryName.ifPresent( categoryName -> {
            Optional<Category> categoryWithName =
                getDialogController().getCurrentCategoriesConfiguration()
                    .getCategoryOfName( categoryName );
            if( categoryWithName.isEmpty() )
            {
                category.setCategoryName( categoryName );
                getDialogController().getView().refreshView();
            }
            else
            {
                Alerts.showWaringDialog(
                    "Category rename",
                    "Given category name already exists in categories configuration. \n Please " +
                        "enter other name" );
                handle();
            }
        } );
    }
}
