package com.catchex.repositorycreator.client.control;

import com.catchex.repositorycreator.client.control.event.*;
import com.catchex.repositorycreator.client.model.CurrentRepositoryUtil;
import com.catchex.repositorycreator.client.view.RepositoryCreatorDialogView;
import dialogs.DialogController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;


public class RepositoryCreatorDialogController
    extends DialogController
{
    private static final Logger logger =
        LoggerFactory.getLogger( RepositoryCreatorDialogController.class );

    private CategoriesConfigurationChangeListener categoriesConfigurationChangeListener;


    public RepositoryCreatorDialogController()
    {
        super( "RepositoryCreator" );
        view = new RepositoryCreatorDialogView( this );
    }


    @Override
    public void initSpecificHandlers()
    {
        initMenuBtnsEventHandler();
        initCurrentRepository();
        this.categoriesConfigurationChangeListener =
            new CategoriesConfigurationChangeListener( this );
    }


    public void onCurrentRepositoryChange()
    {
        getView().applyCurrentRepository();
    }


    public void refreshView()
    {
        getView().refresh();
    }


    public RepositoryCreatorDialogView getView()
    {
        return (RepositoryCreatorDialogView)view;
    }


    @Override
    public void onClose()
    {
        logOnDialogClose();
        categoriesConfigurationChangeListener.stopListen();
        categoriesConfigurationChangeListener = null;
        this.view = null;
    }


    private void loadBankStatementsMenuItemActionEventHandling()
    {
        logger.debug( "Load bank statements menu item handler initialization" );
        getView().getLoadBankStatementsMenuItem()
            .setOnAction( event -> new LoadBankStatementsBtnEventHandler( this ).handle() );
    }


    private void initMenuBtnsEventHandler()
    {
        logger.debug( "Menu buttons handlers initialization" );
        loadBankStatementsMenuItemActionEventHandling();
        loadRepositoryMenuItemActionEventHandling();
        addBankOperationManuallyMenuItemActionEventHandling();

        saveRepositoryMenuItemActionEventHandling();
        generateReportMenuItemActionEventHandling();
        editConfigurationMenuItemActionEventHandling();
    }


    private void initCurrentRepository()
    {
        logger.debug( "Subscribe on current repository" );
        new CurrentRepositoryUtil()
            .addCurrentRepositoryListener( Optional.of( new CurrentRepositoryListener( this ) ) );
    }


    private void loadRepositoryMenuItemActionEventHandling()
    {
        logger.debug( "Load repository menu item handler initialization" );
        getView().getLoadRepositoryMenuItem()
            .setOnAction( event -> new LoadRepositoryBtnEventHandler( this ).handle() );
    }


    private void generateReportMenuItemActionEventHandling()
    {
        logger.debug( "Generate report menu item handler initialization" );
        getView().getGenerateReportMenuItem()
            .setOnAction( event -> new GenerateReportBtnEventHandler( this ).handle() );
    }


    private void editConfigurationMenuItemActionEventHandling()
    {
        logger.debug( "Edit configuration menu item handler initialization" );
        getView().getEditConfigurationMenuItem()
            .setOnAction( event -> new EditConfigurationBtnEventHandler( this ).handle() );
    }


    private void saveRepositoryMenuItemActionEventHandling()
    {
        logger.debug( "Save repository menu item handler initialization" );
        getView().getSaveRepositoryMenuItem()
            .setOnAction( event -> new SaveRepositoryBtnEventHandler( this ).handle() );
    }


    private void addBankOperationManuallyMenuItemActionEventHandling()
    {
        logger.debug( "Add bank operation manually menu item handler initialization" );
        getView().getAddBankOperationManuallyMenuItem()
            .setOnAction( event -> new AddBankOperationManuallyBtnEventHandler( this ).handle() );
    }

    /*private String getSortedByColumnName()
    {
        Optional<String> sortedByColumn =
            getView().getTreeTableView().getSortOrder().stream().map( TreeTableColumn::getText )
                .findFirst();
        if( sortedByColumn.isPresent() )
        {
            return sortedByColumn.get();
        }
        else
        {
            return "";
        }
    }*/
}
