package com.catchex.repositorycreator.client.control.dialog;

import com.catchex.util.Util;
import javafx.scene.control.Toggle;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class AddBankOperationDialogValidator
    extends Validator<AddBankOperationDialogController>
{
    private String errorMessage = "";


    @Override
    public Validator valid( AddBankOperationDialogController dialog )
    {
        LocalDate date = dialog.getView().getDatePicker().getValue();
        String amountString = dialog.getView().getAmountField().getText();
        String description = dialog.getView().getDescriptionField().getText();
        Toggle operationTypeToggle =
            dialog.getView().getOperationTypeRadioGroup().getSelectedToggle();

        List<String> errorMessages = Stream.of(
            new LocalDateValidator().valid( date ).getErrorMessage(),
            new AmountValidator().valid( amountString ).getErrorMessage(),
            new DescriptionValidator().valid( description ).getErrorMessage(),
            new OperationTypeValidator().valid( operationTypeToggle ).getErrorMessage() )
            .filter( errorMessage -> !errorMessage.isEmpty() ).collect( Collectors.toList() );

        if( errorMessages.size() > 0 )
        {
            errorMessage = Util.joinString( "\n", errorMessages );
        }
        return this;
    }


    public String getErrorMessage()
    {
        return errorMessage;
    }


    static class LocalDateValidator
        extends Validator<LocalDate>
    {
        public Validator valid( LocalDate date )
        {
            if( date == null )
            {
                errorMessage = "Date must be selected";
            }
            return this;
        }
    }


    static class AmountValidator
        extends Validator<String>
    {

        @Override
        public Validator valid( String amount )
        {
            if( amount.isEmpty() )
            {
                errorMessage = "Amount must be entered";
            }
            return this;
        }
    }


    static class DescriptionValidator
        extends Validator<String>
    {
        @Override
        public Validator valid( String description )
        {
            if( description.isEmpty() )
            {
                errorMessage = "Description must be entered";
            }
            return this;
        }
    }


    static class OperationTypeValidator
        extends Validator<Toggle>
    {

        @Override
        Validator valid( Toggle operationType )
        {
            if( operationType == null )
            {
                errorMessage = "Operation type must be selected";
            }
            return this;
        }
    }
}
