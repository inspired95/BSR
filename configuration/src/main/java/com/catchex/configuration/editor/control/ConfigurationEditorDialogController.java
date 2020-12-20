package com.catchex.configuration.editor.control;

import com.catchex.configuration.Configuration;
import com.catchex.configuration.ConfigurationUtil;
import com.catchex.configuration.editor.view.ConfigurationEditorDialogView;
import com.catchex.models.CategoriesConfigurationV2;
import com.catchex.models.CategoryV2;
import com.catchex.models.Keyword;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.File;
import java.util.Optional;


public class ConfigurationEditorDialogController
{
    private CategoriesConfigurationV2 currentCategoriesConfiguration;
    private ConfigurationEditorDialogView view;

    private Stage stage;


    public ConfigurationEditorDialogController()
    {
        this.view = new ConfigurationEditorDialogView( this );
        this.currentCategoriesConfiguration = new CategoriesConfigurationV2();

    }


    public void init( Stage stage )
    {
        this.stage = stage;
        view.initView( stage );
        stage.show();
    }


    public CategoriesConfigurationV2 getCategoriesConfiguration()
    {
        return this.currentCategoriesConfiguration;
    }


    @Deprecated
    public void loadOldConfiguration()
    {
        currentCategoriesConfiguration
            .setCategories( Configuration.getCategoriesConfiguration().getCategories() );
        view.refreshView();
    }


    public void loadDefaultConfiguration()
    {
        ConfigurationUtil.getDefaultConfiguration().ifPresentOrElse( categoriesConfiguration -> {
            this.currentCategoriesConfiguration = categoriesConfiguration;
            view.refreshView();
        }, () -> view.showWaringDialog( "Load default configuration",
            "Error during default configuration loading\n" +
                "Check logs to get to know issue root cause" ) );
    }


    public void setConfigurationAsDefault()
    {
        ConfigurationUtil.setConfigurationAsDefault( currentCategoriesConfiguration );
    }


    public void addNewCategory()
    {
        Optional<String> newCategoryName =
            view.showAskForStringDialog( "New category", "Enter new category's name", "" );
        newCategoryName.ifPresent( categoryName -> {
            Optional<CategoryV2> newCategory =
                currentCategoriesConfiguration.addCategory( categoryName );
            newCategory.ifPresentOrElse( category -> view.refreshView(), () -> {
                view.showWaringDialog( "New category",
                    "Given category already exists in categories configuration" );
                addNewCategory();
            } );
        } );
    }


    public void renameCategory( CategoryV2 category )
    {
        Optional<String> newCategoryName = view.showAskForStringDialog( "Category rename",
            "Enter new " + "name for " + category.getCategoryName(), "" );
        newCategoryName.ifPresent( categoryName -> {
            Optional<CategoryV2> categoryWithName = findCategoryWithName( categoryName );
            if( categoryWithName.isEmpty() )
            {
                category.setCategoryName( categoryName );
                view.refreshView();
            }
            else
            {
                view.showWaringDialog(
                    "Category rename",
                    "Given category name already exists in categories configuration. \n Please " +
                        "enter other name" );
                renameCategory( category );
            }
        } );
    }


    public void removeCategory()
    {
        if( assertCategoriesSize() )
            return;

        Optional<String> categoryNameToRemove =
            view.showChoiceFromListDialog( "Remove category", "Select " + "category to remove",
                this.currentCategoriesConfiguration.getCategories() );
        categoryNameToRemove.ifPresent( categoryName -> {
            Optional<ButtonType> choice = view.showConfirmationDialog( "Remove category",
                "Category " + categoryName + " will be removed with all keywords. \nDo you " +
                    "confirm this operation?" );
            if( choice.isPresent() && choice.get() == ButtonType.OK )
            {
                Optional<CategoryV2> categoryToRemove = findCategoryWithName( categoryName );
                categoryToRemove.ifPresent(
                    categoryV2 -> this.currentCategoriesConfiguration.getCategories()
                        .remove( categoryV2 ) );

                view.refreshView();
            }
        } );
    }


    public void addNewKeyword()
    {
        if( assertCategoriesSize() )
            return;

        Optional<String> selectedCategoryName =
            view.showChoiceFromListDialog( "New keyword", "Select category",
                this.currentCategoriesConfiguration.getCategories() );
        selectedCategoryName.ifPresent( categoryName -> {
            Optional<CategoryV2> selectedCategory = findCategoryWithName( categoryName );
            selectedCategory.ifPresentOrElse(
                category -> {
                    Optional<String> newKeyword =
                        view.showAskForStringDialog( "New keyword", "Enter new keyword's name",
                            "" );
                    newKeyword.ifPresent( keyword -> addNewKeyword( category, keyword ) );
                }, () -> view
                    .showWaringDialog( "New keyword", "Category with given name already exists" ) );
        } );
    }


    public void renameKeyword( Keyword keyword )
    {
        Optional<String> newKeywordName =
            view.showAskForStringDialog( "Keyword rename", "Enter new keyword name",
                keyword.getValue() );
        newKeywordName.ifPresent( newKeyword -> {
            keyword.setValue( newKeyword );
            view.refreshView();
        } );
    }


    public void removeKeyword( CategoryV2 category, Keyword keyword )
    {
        category.getKeywords().remove( keyword );
        view.refreshView();
    }


    public void importConfiguration()
    {
        Optional<File> configurationFileToImport =
            view.showOpenFileChooser( "Select configuration to import" );
        configurationFileToImport.flatMap(
            configurationFile -> ConfigurationUtil.getConfiguration( configurationFile.toPath() ) )
            .ifPresent( categoriesConfiguration -> {
                this.currentCategoriesConfiguration = categoriesConfiguration;
                view.refreshView();
            } );
    }


    public void exportConfiguration()
    {
        Optional<File> configurationFileToImport =
            view.showSaveFileChooser( "Select file to export current configuration" );
        configurationFileToImport.ifPresent( configurationFile -> {
            System.out.println( configurationFile.toPath() );
            ConfigurationUtil.saveConfiguration( this.currentCategoriesConfiguration,
                configurationFile.toPath() );
        } );
    }


    public void exitApplication()
    {
        stage.close();
    }


    private boolean assertCategoriesSize()
    {
        if( this.currentCategoriesConfiguration.getCategories().size() == 0 )
        {
            view.showWaringDialog( "Operation cannot be performed", "There are no categories" );
            return true;
        }
        return false;
    }


    private void addNewKeyword( CategoryV2 category, String newKeyword )
    {
        if( category != null )
        {
            if( !isKeywordAlreadyExists( newKeyword ) )
            {
                category.getKeywords().add( new Keyword( newKeyword ) );
                view.refreshView();
            }
            else
            {
                view.showWaringDialog( "New keyword",
                    "Given keyword " + "already exists in categories configuration" );
            }

        }
    }


    private boolean isKeywordAlreadyExists( String keywordToCheck )
    {
        for( CategoryV2 category : this.currentCategoriesConfiguration.getCategories() )
        {
            for( Keyword keyword : category.getKeywords() )
            {
                if( keyword.getValue().equals( keywordToCheck ) )
                    return true;
            }
        }
        return false;
    }


    private Optional<CategoryV2> findCategoryWithName( String categoryName )
    {
        for( CategoryV2 category : this.currentCategoriesConfiguration.getCategories() )
        {
            if( category.getCategoryName().equals( categoryName.toUpperCase() ) )
            {
                return Optional.of( category );
            }
        }
        return Optional.empty();
    }
}

