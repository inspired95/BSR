package client.control.event;

import client.control.RepositoryCreatorDialogController;
import client.view.model.AbstractTreeItem;
import client.view.model.OperationTreeItem;
import com.catchex.models.Category;
import com.catchex.models.Operation;

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
            Operation editedOperation = ((OperationTreeItem)editedRow).getOperation();

            removeOldOperation( event, editedOperation );

            updateOperationWithNewValue( (String)event.getNewValue(), editedOperation );

            int index = getIndexToPutUpdatedRow( event );
            controller.addOperation( editedOperation, index );
        }
    }


    private void removeOldOperation(
        CellEditEvent event, Operation editedOperation )
    {
        controller.getRepository().getOperations().remove( editedOperation );
        event.getRowValue().getParent().getChildren().remove( event.getRowValue() );
    }


    private int getIndexToPutUpdatedRow(
        CellEditEvent event )
    {
        return event.getRowValue().getParent().getChildren().indexOf( event.getRowValue() );
    }


    private void updateOperationWithNewValue( String newDescription, Operation operationToUpdate )
    {
        Category newCategory = resolveCategoryForDescription( newDescription );

        operationToUpdate.getRawOperation().setDesc( newDescription );
        operationToUpdate.setCategory( newCategory );
    }


    private Category resolveCategoryForDescription( String newOperationDescription )
    {
        return controller.getCategoryResolver().resolve( newOperationDescription );
    }
}
