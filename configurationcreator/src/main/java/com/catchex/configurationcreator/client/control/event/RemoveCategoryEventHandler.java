package com.catchex.configurationcreator.client.control.event;

import GuiHelpers.Alerts;
import com.catchex.configurationcreator.client.control.ConfigurationEditorDialogController;
import com.catchex.models.Category;
import dialogs.EventHandler;
import javafx.scene.control.ButtonType;

import java.util.Optional;


public class RemoveCategoryEventHandler
    extends EventHandler<ConfigurationEditorDialogController>
{

    public RemoveCategoryEventHandler( ConfigurationEditorDialogController controller )
    {
        super( "RemoveCategoryEventHandler", controller );
    }


    @Override
    public void handle()
    {
        super.handle();
        if( assertCategoriesSize() )
            return;

        Optional<Category> categoryToRemove = Alerts
            .showChoiceFromListDialog( "Remove category", "Select " + "category to remove",
                getDialogController().getCurrentCategoriesConfiguration().getCategories() );
        categoryToRemove.ifPresent( category -> {
            Optional<ButtonType> choice = Alerts.showConfirmationDialog( "Remove category",
                "Category " + category + " will be removed with all keywords. \nDo you " +
                    "confirm this operation?" );
            if( choice.isPresent() && choice.get() == ButtonType.OK )
            {
                categoryToRemove.ifPresent(
                    categoryV2 -> getDialogController().getCurrentCategoriesConfiguration()
                        .getCategories().remove( categoryV2 ) );

                getDialogController().getView().refreshView();
            }
        } );
    }


    private boolean assertCategoriesSize()
    {
        if( getDialogController().getCurrentCategoriesConfiguration().getCategories().isEmpty() )
        {
            Alerts.showWaringDialog( "Operation cannot be performed", "There are no categories" );
            return true;
        }
        return false;
    }
}
