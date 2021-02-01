package com.catchex.repositorycreator.client.view.columns;

import com.catchex.repositorycreator.client.control.RepositoryCreatorDialogController;
import com.catchex.repositorycreator.client.view.model.AbstractTreeItem;
import javafx.scene.control.TreeTableColumn;


public abstract class RepositoryColumn<T>
    extends TreeTableColumn<AbstractTreeItem, T>
{

    protected RepositoryCreatorDialogController controller;


    protected RepositoryColumn( String columnName, RepositoryCreatorDialogController controller )
    {
        super( columnName );
        this.controller = controller;
        init();
    }


    abstract void init();

    abstract void setCellValueFactory();
}
