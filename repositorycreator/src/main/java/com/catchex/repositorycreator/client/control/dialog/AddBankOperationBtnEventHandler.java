package com.catchex.repositorycreator.client.control.dialog;

import GuiHelpers.Alerts;
import com.catchex.models.CurrentOperation;
import com.catchex.models.Operation;
import com.catchex.models.RawOperation;
import com.catchex.repositorycreator.client.control.CurrentOperationsUtil;
import com.catchex.repositorycreator.client.model.CurrentRepositoryUtil;
import com.catchex.repositorycreator.typeresolving.NotApplicableTypeResolver;
import com.catchex.repositorycreator.typeresolving.OperationTypeResolver;
import dialogs.EventHandler;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;

import java.util.Optional;
import java.util.Set;

import static com.catchex.logging.Log.LOGGER;
import static com.catchex.util.Constants.LOSS;
import static com.catchex.util.Constants.NOT_APPLICABLE;


public class AddBankOperationBtnEventHandler
    extends EventHandler<ActionEvent>
{
    private AddBankOperationDialogController dialog;

    private OperationTypeResolver typeResolver = new NotApplicableTypeResolver();


    public AddBankOperationBtnEventHandler( AddBankOperationDialogController dialog )
    {
        super( "AddBankOperation" );
        this.dialog = dialog;
    }


    @Override
    public void handle( ActionEvent event )
    {
        super.handle( event );
        String errorMessage =
            new AddBankOperationDialogValidator().valid( dialog ).getErrorMessage();

        if( !errorMessage.isEmpty() )
        {
            LOGGER.warning( errorMessage );
            Alerts.showAlert( Alert.AlertType.ERROR, "Error", "Validation error", errorMessage );
            return;
        }

        String description = dialog.getView().getDescriptionField().getText();
        String operationType =
            ((RadioButton)dialog.getView().getOperationTypeRadioGroup().getSelectedToggle())
                .getText();
        Double amount = Double.parseDouble( dialog.getView().getAmountField().getText() );
        if( operationType.equals( LOSS ) )
            amount *= -1;

        RawOperation.Builder builder = new RawOperation.Builder( NOT_APPLICABLE );
        builder.setDate( dialog.getView().getDatePicker().getValue() );
        builder.setAmount( amount );
        builder.setDesc( description );
        builder.setBank( NOT_APPLICABLE );
        builder.setFileName( NOT_APPLICABLE );
        builder.setType( NOT_APPLICABLE );
        Operation operation = new Operation( builder.build(), typeResolver.resolve( description ) );
        CurrentOperation currentOperation =
            new CurrentOperationsUtil().mapToCurrentOperation( operation );
        new CurrentRepositoryUtil()
            .addCurrentOperations( Optional.of( Set.of( currentOperation ) ) );
        try
        {
            dialog.closeDialog();
        }
        catch( Exception e )
        {
            LOGGER.warning( "Issue during closing dialog " + dialog.getDIALOG_NAME() );
            e.printStackTrace();
        }
    }
}
