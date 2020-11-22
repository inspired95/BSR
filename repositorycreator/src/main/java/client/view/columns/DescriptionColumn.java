package client.view.columns;

import client.control.RepositoryCreatorDialogController;
import client.control.event.DescriptionCellEditEventHandler;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.scene.control.cell.TextFieldTreeTableCell;

public class DescriptionColumn extends RepositoryColumn<String>  {

    private RepositoryCreatorDialogController controller;

    public DescriptionColumn( RepositoryCreatorDialogController controller ) {
        super("Description");
        this.controller = controller;
    }

    void init() {
        super.init();
        setCellFactory();
        setPrefWidth(810);
        initEventHandler();
    }

    void setCellValueFactory() {
        setCellValueFactory(
                p -> new ReadOnlyObjectWrapper<>(p.getValue().getValue().getDesc()));
    }

    private void setCellFactory() {
        setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());
    }

    private void initEventHandler() {
        new DescriptionCellEditEventHandler(controller);
    }



}
