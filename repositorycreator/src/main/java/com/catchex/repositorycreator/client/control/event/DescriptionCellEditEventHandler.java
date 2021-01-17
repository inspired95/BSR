package com.catchex.repositorycreator.client.control.event;

import com.catchex.repositorycreator.client.control.RepositoryCreatorDialogController;
import com.catchex.repositorycreator.client.view.model.AbstractTreeItem;
import com.catchex.repositorycreator.client.view.model.OperationTreeItem;

import static javafx.scene.control.TreeTableColumn.CellEditEvent;


public class DescriptionCellEditEventHandler
    implements javafx.event.EventHandler<CellEditEvent<AbstractTreeItem, String>>
{

    private RepositoryCreatorDialogController controller;


    public DescriptionCellEditEventHandler( RepositoryCreatorDialogController controller )
    {
        this.controller = controller;
    }


    @Override
    public void handle( CellEditEvent event )
    {
        Object editedRow = event.getRowValue().getValue();
        if( editedRow instanceof OperationTreeItem )
        {
            controller.updateCategory( (OperationTreeItem)editedRow, (String)event.getNewValue() );
        }
    }

}
