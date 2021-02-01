package com.catchex.repositorycreator.client.control.event;

import com.catchex.repositorycreator.client.control.dialog.AddBankOperationDialogController;
import dialogs.EventHandler;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.stage.Stage;


public class AddBankOperationManuallyBtnEventHandler
    extends EventHandler<ActionEvent>
{
    public AddBankOperationManuallyBtnEventHandler()
    {
        super( "AddBankOperationManually" );
    }


    @Override
    public void handle( ActionEvent event )
    {
        super.handle( event );
        Platform.runLater( () -> {
            try
            {
                new AddBankOperationDialogController().start( new Stage() );
            }
            catch( Exception e )
            {
                e.printStackTrace();
            }
        } );

    }


    /*class AddBankOperationDialog
        extends Application
    {
        private DatePicker datePicker;
        private TextField amountField;
        private TextField descriptionField;
        private ToggleGroup operationTypeRadioGroup;

        private Button addOperationBtn;

        private OperationTypeResolver typeResolver = new NotApplicableTypeResolver();


        @Override
        public void start( Stage stage ) throws Exception
        {
            Label operationDateLabel = new Label( "Operation date" );
            datePicker = new DatePicker();

            stage.setOnHiding( event -> actionCancelled() );

            Label operationAmountLabel = new Label( "Operation amount" );
            amountField = new TextField();
            amountField.textProperty().addListener( ( observableValue, s, t1 ) -> {
                if( !t1.matches( "\\d{0,7}([\\.]\\d{0,4})?" ) )
                {
                    amountField.setText( s );
                }
            } );

            Label operationDescriptionLabel = new Label( "Operation description" );
            descriptionField = new TextField();

            Label operationTypeLabel = new Label( "Operation type" );
            operationTypeRadioGroup = new ToggleGroup();
            RadioButton profitRadioBtn = new RadioButton( "Profit" );
            profitRadioBtn.setToggleGroup( operationTypeRadioGroup );
            RadioButton lossRadioBtn = new RadioButton( "Loss" );
            lossRadioBtn.setToggleGroup( operationTypeRadioGroup );

            HBox operationTypesContainer = new HBox( profitRadioBtn, lossRadioBtn );

            VBox container = new VBox();

            container.getChildren().add( operationDateLabel );
            container.getChildren().add( datePicker );

            container.getChildren().add( operationAmountLabel );
            container.getChildren().add( amountField );

            container.getChildren().add( operationDescriptionLabel );
            container.getChildren().add( descriptionField );

            container.getChildren().add( operationTypeLabel );
            container.getChildren().add( operationTypesContainer );
            container.setAlignment( Pos.CENTER );

            addOperationBtn = new Button( "Add operation" );
            addOperationBtn.setOnAction( actionEvent -> {
                LocalDate date = datePicker.getValue();
                String amountString = amountField.getText();
                String description = descriptionField.getText();
                Toggle operationTypeToggle = operationTypeRadioGroup.getSelectedToggle();

                if( !isDialogValid( date, amountString, description, operationTypeToggle ) )
                    return;

                String operationType = ((RadioButton)operationTypeToggle).getText();
                Double amount = Double.parseDouble( amountString );
                if( operationType.equals( LOSS ) )
                    amount *= -1;

                RawOperation.Builder builder = new RawOperation.Builder( NOT_APPLICABLE );
                builder.setDate( date );
                builder.setAmount( amount );
                builder.setDesc( description );
                builder.setBank( NOT_APPLICABLE );
                builder.setFileName( NOT_APPLICABLE );
                builder.setType( NOT_APPLICABLE );
                Operation operation =
                    new Operation( builder.build(), typeResolver.resolve( description ) );
                CurrentOperation currentOperation =
                    new CurrentOperationsUtil().mapToCurrentOperation( operation );
                new CurrentRepositoryUtil()
                    .addCurrentOperations( Optional.of( Set.of( currentOperation ) ) );
                stage.close();
            } );
            container.getChildren().add( addOperationBtn );

            container.setPadding( new Insets( 10 ) );
            container.setSpacing( 10 );

            stage.setScene( new Scene( container, 400, 270 ) );
            stage.show();
        }


        private boolean isDialogValid(
            LocalDate date, String amount, String description, Toggle operationType )
        {
            List<String> validationErrorMessages = new ArrayList<>();
            if( date == null )
            {
                validationErrorMessages.add( "Date must be selected" );
            }

            if( amount.isEmpty() )
            {
                validationErrorMessages.add( "Amount must be entered" );
            }

            if( description.isEmpty() )
            {
                validationErrorMessages.add( "Description must be entered" );
            }

            if( operationType == null )
            {
                validationErrorMessages.add( "Operation type must be selected" );
            }
            if( validationErrorMessages.size() > 0 )
            {
                StringJoiner joiner = new StringJoiner( "\n" );
                for( String validationErrorMessage : validationErrorMessages )
                {
                    joiner.add( validationErrorMessage );
                }
                LOGGER.warning( joiner.toString() );
                Alerts.showAlert( Alert.AlertType.ERROR, "Error", "Validation error",
                    joiner.toString() );
                return false;
            }
            return true;
        }
    }*/
}
