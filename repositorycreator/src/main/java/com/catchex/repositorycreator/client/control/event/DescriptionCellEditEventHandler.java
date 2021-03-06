package com.catchex.repositorycreator.client.control.event;

import com.catchex.repositorycreator.client.control.CurrentOperationsUtil;
import com.catchex.repositorycreator.client.control.RepositoryCreatorDialogController;
import com.catchex.repositorycreator.client.view.model.AbstractTreeItem;
import com.catchex.repositorycreator.client.view.model.OperationTreeItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static javafx.scene.control.TreeTableColumn.CellEditEvent;


public class DescriptionCellEditEventHandler
    implements javafx.event.EventHandler<CellEditEvent<AbstractTreeItem, String>>
{
    private static final Logger logger =
        LoggerFactory.getLogger( DescriptionCellEditEventHandler.class );

    private final RepositoryCreatorDialogController controller;


    public DescriptionCellEditEventHandler( RepositoryCreatorDialogController controller )
    {
        this.controller = controller;
    }


    @Override
    public void handle( CellEditEvent event )
    {
        logger.info( "Operation description changed event" );
        Object editedRow = event.getRowValue().getValue();
        if( editedRow instanceof OperationTreeItem )
        {
            new CurrentOperationsUtil()
                .updateDescription( ((OperationTreeItem)editedRow).getOperation(),
                    (String)event.getNewValue() );
            controller.refreshView();
        }
    }

}
