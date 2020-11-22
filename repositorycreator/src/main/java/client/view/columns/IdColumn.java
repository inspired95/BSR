package client.view.columns;

import client.control.RepositoryCreatorDialogController;
import javafx.beans.property.ReadOnlyObjectWrapper;

public class IdColumn extends RepositoryColumn<String> {
    public IdColumn() {
        super("ID");
    }

    @Override
    void init() {
        super.init();
        setPrefWidth(100);
        setVisible(false);
    }

    @Override
    void setCellValueFactory() {
        setCellValueFactory(
                p -> new ReadOnlyObjectWrapper<>(p.getValue().getValue().getID()));
    }
}
