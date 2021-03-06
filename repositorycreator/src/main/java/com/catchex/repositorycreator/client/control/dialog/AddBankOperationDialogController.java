package com.catchex.repositorycreator.client.control.dialog;

import dialogs.DialogController;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class AddBankOperationDialogController
    extends DialogController
{
    private static final Logger logger =
        LoggerFactory.getLogger( AddBankOperationDialogController.class );


    public AddBankOperationDialogController()
    {
        super( "AddBankOperation" );
        view = new AddBankOperationDialogView( this );
    }


    @Override
    public void start( Stage stage ) throws Exception
    {
        super.start( stage );
    }


    @Override
    public void initSpecificHandlers()
    {
        addOperationButtonActionEventHandling();
    }


    @Override
    public void onClose()
    {
        logOnDialogClose();
        view = null;
    }


    public AddBankOperationDialogView getView()
    {
        return (AddBankOperationDialogView)view;
    }


    private void addOperationButtonActionEventHandling()
    {
        getView().getAddOperationBtn()
            .setOnAction( event -> new AddBankOperationBtnEventHandler( this ).handle() );
    }
}
