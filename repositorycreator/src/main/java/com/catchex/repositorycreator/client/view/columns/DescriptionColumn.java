package com.catchex.repositorycreator.client.view.columns;

import com.catchex.repositorycreator.client.control.RepositoryCreatorDialogController;
import com.catchex.repositorycreator.client.control.event.DescriptionCellEditEventHandler;
import com.catchex.util.Constants;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.scene.control.cell.TextFieldTreeTableCell;


public class DescriptionColumn
    extends RepositoryColumn<String>
{

    public DescriptionColumn( RepositoryCreatorDialogController controller )
    {
        super( Constants.DESCRIPTION, controller );
        this.controller = controller;
    }


    void init()
    {
        setCellValueFactory();
        setCellFactory();
        setPrefWidth( 810 );
        initEventHandler();
    }


    void setCellValueFactory()
    {
        setCellValueFactory(
            p -> new ReadOnlyObjectWrapper<>( p.getValue().getValue().getDesc() ) );
    }


    private void setCellFactory()
    {
        setCellFactory( TextFieldTreeTableCell.forTreeTableColumn() );
    }


    private void initEventHandler()
    {
        setOnEditCommit( new DescriptionCellEditEventHandler( controller ) );
    }

}
