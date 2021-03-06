package com.catchex.configurationcreator.client.control.event;

import GuiHelpers.Alerts;
import com.catchex.configurationcreator.client.control.ConfigurationEditorDialogController;
import com.catchex.models.Category;
import com.catchex.models.Keyword;
import dialogs.EventHandler;

import java.util.Optional;


public class AddNewKeywordEventHandler
    extends EventHandler<ConfigurationEditorDialogController>
{

    public AddNewKeywordEventHandler( ConfigurationEditorDialogController controller )
    {
        super( "AddNewKeyword", controller );
    }


    @Override
    public void handle()
    {
        super.handle();
        if( !assertCategoriesSize() )
            return;

        Optional<Category> selectedCategory = Alerts
            .showChoiceFromListDialog( "New keyword", "Select category",
                getDialogController().getCurrentCategoriesConfiguration().getCategories() );
        selectedCategory.ifPresent( category -> {
            Optional<String> newKeyword =
                Alerts.showAskForStringDialog( "New keyword", "Enter new keyword's name", "" );
            newKeyword.ifPresent( keyword -> {
                if( !assertKeywordValue( keyword ) )
                    return;

                addNewKeyword( category, keyword );
            } );
        } );
    }


    private boolean assertCategoriesSize()
    {
        if( getDialogController().getCurrentCategoriesConfiguration().getCategories().isEmpty() )
        {
            Alerts.showWaringDialog( "Operation cannot be performed", "There are no categories" );
            return false;
        }
        return true;
    }


    private boolean assertKeywordValue( String value )
    {
        if( value.isEmpty() )
        {
            Alerts
                .showWaringDialog( "Operation cannot be performed", "Given value cannot be empty" );
            return false;
        }
        return true;
    }


    private void addNewKeyword( Category category, String newKeyword )
    {
        if( category != null )
        {
            Keyword keywordToAdd = new Keyword( newKeyword );
            getDialogController().getCurrentCategoriesConfiguration()
                .isKeywordAlreadyExists( keywordToAdd ).ifPresentOrElse( keyword -> Alerts
                .showWaringDialog( "New keyword",
                    "Given keyword " + "already exists in categories configuration" ), () -> {
                category.getKeywords().add( keywordToAdd );
                getDialogController().getView().refreshView();
            } );
        }
    }
}
