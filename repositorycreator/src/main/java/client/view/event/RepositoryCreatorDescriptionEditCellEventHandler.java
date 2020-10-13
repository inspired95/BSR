package client.view.event;

import client.RepositoryCreatorDialogController;
import client.view.RepositoryCreatorOperationTreeItem;
import com.catchex.models.Category;
import com.catchex.models.Operation;
import javafx.scene.control.TreeItem;

import static javafx.scene.control.TreeTableColumn.CellEditEvent;

public class RepositoryCreatorDescriptionEditCellEventHandler implements CellEditEventHandler {

    private RepositoryCreatorDialogController controller;

    public RepositoryCreatorDescriptionEditCellEventHandler( RepositoryCreatorDialogController controller ) {
        this.controller = controller;
    }

    @Override
    public void handle( CellEditEvent event ) {
        Object treeItemToEdit = event.getRowValue().getValue();
        if (treeItemToEdit instanceof RepositoryCreatorOperationTreeItem){
            RepositoryCreatorOperationTreeItem operationTreeItemToEdit = (RepositoryCreatorOperationTreeItem) treeItemToEdit;
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
