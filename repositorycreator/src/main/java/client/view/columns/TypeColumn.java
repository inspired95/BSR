package client.view.columns;

import client.control.RepositoryCreatorDialogController;
import client.view.model.OperationTreeItem;
import javafx.beans.property.ReadOnlyObjectWrapper;

public class TypeColumn extends RepositoryColumn<String> {
    public TypeColumn( RepositoryCreatorDialogController controller ) {
        super("Type", controller);
    }

    @Override
    void init() {
        setCellValueFactory();
        setPrefWidth(100);
    }

    @Override
    void setCellValueFactory() {
        setCellValueFactory(
                p -> {
                    if (p.getValue().getValue() instanceof OperationTreeItem){
                        return new ReadOnlyObjectWrapper<>(p.getValue().getValue().getType());
                    }
                    return new ReadOnlyObjectWrapper<>("");
                });
    }
}
