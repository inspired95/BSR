package client.control.event;

import client.control.RepositoryCreatorDialogController;
import client.view.model.AbstractTreeItem;
import client.view.model.OperationTreeItem;
import com.catchex.models.Category;
import com.catchex.models.Operation;
import javafx.scene.control.TreeItem;

import static javafx.scene.control.TreeTableColumn.CellEditEvent;

public class DescriptionCellEditEventHandler implements javafx.event.EventHandler<CellEditEvent<AbstractTreeItem, String>> {

    private RepositoryCreatorDialogController controller;

    public DescriptionCellEditEventHandler( RepositoryCreatorDialogController controller ) {
        this.controller = controller;
    }

    @Override
    public void handle( CellEditEvent event ) {
        Object treeItemToEdit = event.getRowValue().getValue();
        if (treeItemToEdit instanceof OperationTreeItem){
            OperationTreeItem operationTreeItemToEdit = (OperationTreeItem) treeItemToEdit;
            Operation operationToUpdate = operationTreeItemToEdit.getOperation();

            TreeItem intervalTreeItem = event.getRowValue().getParent();

            int currentIndex = intervalTreeItem.getChildren().indexOf(event.getRowValue());

            controller.getRepository().getOperations().remove(operationToUpdate);
            intervalTreeItem.getChildren().remove(event.getRowValue());

            String newOperationDescription = (String)event.getNewValue();
            operationToUpdate.getRawOperation().setDesc(newOperationDescription);

            Category newOperationCategory = controller.getCategoryResolver().resolve(newOperationDescription);
            operationToUpdate.setCategory(newOperationCategory);

            controller.addOperation(operationToUpdate, currentIndex);
        }
    }
}
