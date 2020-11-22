package client.view.columns;

import client.control.RepositoryCreatorDialogController;
import client.view.model.AbstractTreeItem;
import javafx.scene.control.TreeTableColumn;


public abstract class RepositoryColumn<T>
    extends TreeTableColumn<AbstractTreeItem, T>
{

    protected RepositoryCreatorDialogController controller;


    public RepositoryColumn( String columnName, RepositoryCreatorDialogController controller )
    {
        super( columnName );
        this.controller = controller;
        init();
    }


    abstract void init();

    abstract void setCellValueFactory();
}
