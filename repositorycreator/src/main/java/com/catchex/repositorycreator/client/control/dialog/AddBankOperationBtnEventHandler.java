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
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.Set;

import static com.catchex.util.Constants.LOSS;
import static com.catchex.util.Constants.NOT_APPLICABLE;


public class AddBankOperationBtnEventHandler
    extends EventHandler<AddBankOperationDialogController>
{
    private static final Logger logger =
        LoggerFactory.getLogger( AddBankOperationBtnEventHandler.class );

    private final OperationTypeResolver typeResolver = new NotApplicableTypeResolver();


    public AddBankOperationBtnEventHandler( AddBankOperationDialogController controller )
    {
        super( "AddOperation", controller );
    }


    @Override
    public void handle()
    {
        super.handle();
        String errorMessage =
            new AddBankOperationDialogValidator().valid( getDialogController() ).getErrorMessage();

        if( !errorMessage.isEmpty() )
        {
            logger.warn( errorMessage );
            Alerts.showAlert( Alert.AlertType.ERROR, "Error", "Validation error", errorMessage );
            return;
        }

        String description = getDialogController().getView().getDescriptionField().getText();
        String operationType =
            ((RadioButton)getDialogController().getView().getOperationTypeRadioGroup()
                .getSelectedToggle()).getText();
        Double amount =
            Double.parseDouble( getDialogController().getView().getAmountField().getText() );
        if( operationType.equals( LOSS ) )
            amount *= -1;

        RawOperation.Builder builder = new RawOperation.Builder( NOT_APPLICABLE );
        builder.setDate( getDialogController().getView().getDatePicker().getValue() );
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
            getDialogController().getView().getStage().close();
        }
        catch( Exception e )
        {
            logger
                .error( "Error during application staring {}", ExceptionUtils.getStackTrace( e ) );
        }
    }
}
