package client.view.columns;

import client.view.model.AbstractTreeItem;
import javafx.scene.control.TreeTableColumn;


public abstract class RepositoryColumn<T> extends TreeTableColumn<AbstractTreeItem, T> {

    public RepositoryColumn( String columnName){
        super(columnName);
        init();
    }

    void init(){
        setCellValueFactory();
    }

    abstract void setCellValueFactory();
}
