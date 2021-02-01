package com.catchex.repositorycreator.client.control.dialog;

import dialogs.DialogController;
import javafx.stage.Stage;


public class AddBankOperationDialogController
    extends DialogController
{

    public AddBankOperationDialogController()
    {
        super( "AddBankOperation" );
        view = new AddBankOperationDialogView( this );
    }


    @Override
    public void start( Stage stage ) throws Exception
    {
        super.start( stage );
        addOperationButtonActionEventHandling();
        stage.setOnHiding( actionEvent -> {
            logOnDialogClose();
        } );
    }


    @Override
    public void onClose()
    {
        logOnDialogClose();
        view = null;
        stage = null;
    }


    public void closeDialog()
    {
        stage.close();
    }


    public AddBankOperationDialogView getView()
    {
        return (AddBankOperationDialogView)view;
    }


    private void addOperationButtonActionEventHandling()
    {
        getView().getAddOperationBtn()
            .setOnAction( new AddBankOperationBtnEventHandler( this )::handle );
    }
}
