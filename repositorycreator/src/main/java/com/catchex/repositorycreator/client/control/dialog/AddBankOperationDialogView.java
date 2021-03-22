package com.catchex.repositorycreator.client.control.dialog;

import dialogs.DialogView;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class AddBankOperationDialogView
    extends DialogView
{
    private DatePicker datePicker;
    private TextField amountField;
    private TextField descriptionField;
    private ToggleGroup operationTypeRadioGroup;

    private Button addOperationBtn;

    private AddBankOperationDialogController controller;


    public AddBankOperationDialogView( AddBankOperationDialogController controller )
    {
        this.controller = controller;
    }


    @Override
    public void initView( Stage stage )
    {
        super.initView( stage );
        Label operationDateLabel = new Label( "Operation date" );
        datePicker = new DatePicker();

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

        container.getChildren().add( addOperationBtn );

        container.setPadding( new Insets( 10 ) );
        container.setSpacing( 10 );

        stage.setScene( new Scene( container, 400, 270 ) );

        stage.setOnHiding( actionEvent -> controller.onClose() );
    }


    @Override
    public void refreshView()
    {
        //implement if needed
    }


    public DatePicker getDatePicker()
    {
        return datePicker;
    }


    public TextField getAmountField()
    {
        return amountField;
    }


    public TextField getDescriptionField()
    {
        return descriptionField;
    }


    public ToggleGroup getOperationTypeRadioGroup()
    {
        return operationTypeRadioGroup;
    }


    public Button getAddOperationBtn()
    {
        return addOperationBtn;
    }
}
