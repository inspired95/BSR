package client.control.event;

import client.control.RepositoryCreatorDialogController;
import client.view.model.AbstractTreeItem;
import client.view.model.OperationTreeItem;

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
            controller.handleDescriptionChange( (OperationTreeItem)editedRow,
                (String)event.getNewValue() );
        }
    }

}
