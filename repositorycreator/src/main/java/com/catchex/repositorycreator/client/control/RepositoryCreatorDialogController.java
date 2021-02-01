package com.catchex.repositorycreator.client.control;

import com.catchex.repositorycreator.client.control.event.*;
import com.catchex.repositorycreator.client.model.CurrentRepositoryUtil;
import com.catchex.repositorycreator.client.view.RepositoryCreatorDialogView;
import dialogs.DialogController;
import javafx.stage.Stage;

import java.util.Optional;


public class RepositoryCreatorDialogController
    extends DialogController
{


    private CategoriesConfigurationChangeListener categoriesConfigurationChangeListener;


    public RepositoryCreatorDialogController()
    {
        super( "RepositoryCreator" );
        view = new RepositoryCreatorDialogView( this );
    }

    @Override
    public void start( Stage stage ) throws Exception
    {
        super.start( stage );
        initMenuBtnsEventHandler();
        initCurrentRepository();
        this.categoriesConfigurationChangeListener =
            new CategoriesConfigurationChangeListener( this );
        stage.show();
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
        getView().getLoadBankStatementsMenuItem()
            .setOnAction( new LoadBankStatementsBtnEventHandler( this )::handle );
    }


    private void initMenuBtnsEventHandler()
    {
        loadBankStatementsMenuItemActionEventHandling();
        loadRepositoryMenuItemActionEventHandling();
        addBankOperationManuallyMenuItemActionEventHandling();
        saveRepositoryMenuItemActionEventHandling();
        generateReportMenuItemActionEventHandling();
        editConfigurationMenuItemActionEventHandling();
    }


    private void initCurrentRepository()
    {

        new CurrentRepositoryUtil()
            .addCurrentRepositoryListener( Optional.of( new CurrentRepositoryListener( this ) ) );
    }


    private void loadRepositoryMenuItemActionEventHandling()
    {
        getView().getLoadRepositoryMenuItem()
            .setOnAction( new LoadRepositoryBtnEventHandler( this )::handle );
    }


    private void generateReportMenuItemActionEventHandling()
    {
        getView().getGenerateReportMenuItem()
            .setOnAction( new GenerateReportBtnEventHandler( this )::handle );
    }


    private void editConfigurationMenuItemActionEventHandling()
    {
        getView().getEditConfigurationMenuItem()
            .setOnAction( new EditConfigurationBtnEventHandler()::handle );
    }


    private void saveRepositoryMenuItemActionEventHandling()
    {
        getView().getSaveRepositoryMenuItem()
            .setOnAction( new SaveRepositoryBtnEventHandler( this )::handle );
    }


    private void addBankOperationManuallyMenuItemActionEventHandling()
    {
        getView().getAddBankOperationManuallyMenuItem()
            .setOnAction( new AddBankOperationManuallyBtnEventHandler()::handle );
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
