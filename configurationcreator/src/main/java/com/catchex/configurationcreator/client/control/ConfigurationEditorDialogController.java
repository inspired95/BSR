package com.catchex.configurationcreator.client.control;

import GuiHelpers.Alerts;
import com.catchex.configurationcreator.client.view.ConfigurationEditorDialogView;
import com.catchex.core.configuration.ConfigurationUtil;
import com.catchex.models.CategoriesConfiguration;
import com.catchex.models.Category;
import com.catchex.models.Keyword;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.File;
import java.util.Optional;


public class ConfigurationEditorDialogController
{
    private CategoriesConfiguration currentCategoriesConfiguration;
    private ConfigurationEditorDialogView view;

    private Stage stage;


    public ConfigurationEditorDialogController()
    {
        this.view = new ConfigurationEditorDialogView( this );
        this.currentCategoriesConfiguration = new CategoriesConfiguration();

    }


    public void init( Stage stage )
    {
        this.stage = stage;
        view.initView( stage );
        stage.show();
    }


    public CategoriesConfiguration getCategoriesConfiguration()
    {
        return this.currentCategoriesConfiguration;
    }


    /*@Deprecated
    public void loadOldConfiguration()
    {
        currentCategoriesConfiguration
            .setCategories( Configuration.getCategoriesConfiguration().getCategories() );
        view.refreshView();
    }*/


    public void loadDefaultConfiguration()
    {
        ConfigurationUtil.getDefaultConfiguration().ifPresentOrElse( categoriesConfiguration -> {
            this.currentCategoriesConfiguration = categoriesConfiguration;
            view.refreshView();
        }, () -> Alerts.showWaringDialog( "Load default configuration",
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
            Alerts.showAskForStringDialog( "New category", "Enter new category's name", "" );
        newCategoryName.ifPresent( categoryName -> {
            Optional<Category> newCategory =
                currentCategoriesConfiguration.addCategory( categoryName );
            newCategory.ifPresentOrElse( category -> view.refreshView(), () -> {
                Alerts.showWaringDialog( "New category",
                    "Given category already exists in categories configuration" );
                addNewCategory();
            } );
        } );
    }


    public void renameCategory( Category category )
    {
        Optional<String> newCategoryName = Alerts.showAskForStringDialog( "Category rename",
            "Enter new " + "name for " + category.getCategoryName(), "" );
        newCategoryName.ifPresent( categoryName -> {
            Optional<Category> categoryWithName = findCategoryWithName( categoryName );
            if( categoryWithName.isEmpty() )
            {
                category.setCategoryName( categoryName );
                view.refreshView();
            }
            else
            {
                Alerts.showWaringDialog(
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

        Optional<String> categoryNameToRemove = Alerts
            .showChoiceFromListDialog( "Remove category", "Select " + "category to remove",
                this.currentCategoriesConfiguration.getCategories() );
        categoryNameToRemove.ifPresent( categoryName -> {
            Optional<ButtonType> choice = Alerts.showConfirmationDialog( "Remove category",
                "Category " + categoryName + " will be removed with all keywords. \nDo you " +
                    "confirm this operation?" );
            if( choice.isPresent() && choice.get() == ButtonType.OK )
            {
                Optional<Category> categoryToRemove = findCategoryWithName( categoryName );
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

        Optional<String> selectedCategoryName = Alerts
            .showChoiceFromListDialog( "New keyword", "Select category",
                this.currentCategoriesConfiguration.getCategories() );
        selectedCategoryName.ifPresent( categoryName -> {
            Optional<Category> selectedCategory = findCategoryWithName( categoryName );
            selectedCategory.ifPresentOrElse(
                category -> {
                    Optional<String> newKeyword = Alerts
                        .showAskForStringDialog( "New keyword", "Enter new keyword's name", "" );
                    newKeyword.ifPresent( keyword -> addNewKeyword( category, keyword ) );
                }, () -> Alerts
                    .showWaringDialog( "New keyword", "Category with given name already exists" ) );
        } );
    }


    public void renameKeyword( Keyword keyword )
    {
        Optional<String> newKeywordName = Alerts
            .showAskForStringDialog( "Keyword rename", "Enter new keyword name",
                keyword.getValue() );
        newKeywordName.ifPresent( newKeyword -> {
            keyword.setValue( newKeyword );
            view.refreshView();
        } );
    }


    public void removeKeyword( Category category, Keyword keyword )
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
            Alerts.showWaringDialog( "Operation cannot be performed", "There are no categories" );
            return true;
        }
        return false;
    }


    private void addNewKeyword( Category category, String newKeyword )
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
                Alerts.showWaringDialog( "New keyword",
                    "Given keyword " + "already exists in categories configuration" );
            }

        }
    }


    private boolean isKeywordAlreadyExists( String keywordToCheck )
    {
        for( Category category : this.currentCategoriesConfiguration.getCategories() )
        {
            for( Keyword keyword : category.getKeywords() )
            {
                if( keyword.getValue().equals( keywordToCheck ) )
                    return true;
            }
        }
        return false;
    }


    private Optional<Category> findCategoryWithName( String categoryName )
    {
        for( Category category : this.currentCategoriesConfiguration.getCategories() )
        {
            if( category.getCategoryName().equals( categoryName.toUpperCase() ) )
            {
                return Optional.of( category );
            }
        }
        return Optional.empty();
    }
}

