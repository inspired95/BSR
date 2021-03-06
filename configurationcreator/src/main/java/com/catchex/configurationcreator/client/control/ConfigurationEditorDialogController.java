package com.catchex.configurationcreator.client.control;

import com.catchex.configurationcreator.client.control.event.*;
import com.catchex.configurationcreator.client.view.ConfigurationEditorDialogView;
import com.catchex.models.CategoriesConfiguration;
import dialogs.DialogController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ConfigurationEditorDialogController
    extends DialogController
{
    private static final Logger logger =
        LoggerFactory.getLogger( ConfigurationEditorDialogController.class );

    private CategoriesConfiguration currentCategoriesConfiguration;


    public ConfigurationEditorDialogController()
    {
        super( "ConfigurationEditor" );
        view = new ConfigurationEditorDialogView( this );
        this.currentCategoriesConfiguration = new CategoriesConfiguration();
    }


    @Override
    public void initSpecificHandlers()
    {
        loadDefaultConfigurationMenuItemActionEventHandling();
        exportConfigurationMenuItemActionEventHandling();
        setCurrentConfigurationAsDefaultMenuItemActionEventHandling();
        addNewCategoryMenuItemActionEventHandling();
        removeCategoryMenuItemActionEventHandling();
        addNewKeywordMenuItemActionEventHandling();
        importConfigurationMenuItemActionEventHandling();
        exitMenuItemActionEventHandling();
    }


    public CategoriesConfiguration getCurrentCategoriesConfiguration()
    {
        return this.currentCategoriesConfiguration;
    }


    public void setCurrentCategoriesConfiguration( CategoriesConfiguration categoriesConfiguration )
    {
        this.currentCategoriesConfiguration = categoriesConfiguration;
    }


    public ConfigurationEditorDialogView getView()
    {
        return (ConfigurationEditorDialogView)view;
    }


    @Override
    public void onClose()
    {
        logOnDialogClose();
        this.view = null;
        this.currentCategoriesConfiguration = null;
    }


    private void loadDefaultConfigurationMenuItemActionEventHandling()
    {
        logger.debug( "Load default configuration menu item handler initialization" );
        getView().getLoadDefaultConfigurationMenuItem()
            .setOnAction( event -> new LoadDefaultConfigurationEventHandler( this ).handle() );
    }


    private void exportConfigurationMenuItemActionEventHandling()
    {
        logger.debug( "Export configuration menu item handler initialization" );
        getView().getExportConfigurationMenuItem()
            .setOnAction( event -> new ExportConfigurationEventHandler( this ).handle() );
    }


    private void setCurrentConfigurationAsDefaultMenuItemActionEventHandling()
    {
        logger.debug( "Set current configuration as default menu item handler initialization" );
        getView().getSetCurrentConfigurationAsDefaultMenuItem().setOnAction(
            event -> new SetCurrentConfigurationAsDefaultEventHandler( this ).handle() );
    }


    private void addNewCategoryMenuItemActionEventHandling()
    {
        logger.debug( "Add new category menu item handler initialization" );
        getView().getAddNewCategoryMenuItem()
            .setOnAction( event -> new AddCategoryEventHandler( this ).handle() );
    }


    private void removeCategoryMenuItemActionEventHandling()
    {
        logger.debug( "Remove category menu item handler initialization" );
        getView().getRemoveCategoryMenuItem()
            .setOnAction( event -> new RemoveCategoryEventHandler( this ).handle() );
    }


    private void addNewKeywordMenuItemActionEventHandling()
    {
        logger.debug( "Add new keyword menu item handler initialization" );
        getView().getAddNewKeywordMenuItem()
            .setOnAction( event -> new AddNewKeywordEventHandler( this ).handle() );
    }


    private void importConfigurationMenuItemActionEventHandling()
    {
        logger.debug( "Import configuration menu item handler initialization" );
        getView().getImportConfigurationMenuItem()
            .setOnAction( event -> new ImportConfigurationEventHandler( this ).handle() );
    }


    private void exitMenuItemActionEventHandling()
    {
        logger.debug( "Exit menu item handler initialization" );
        getView().getExitMenuItem().setOnAction( event -> new ExitEventHandler( this ).handle() );
    }
}

