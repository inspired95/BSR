package com.catchex.repositorycreator.client.view;

import com.catchex.repositorycreator.client.view.model.AbstractTreeItem;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeTableRow;


public class OperationRowFactory
    extends TreeTableRow<AbstractTreeItem>
{

    ContextMenu contextMenu;


    public OperationRowFactory()
    {
        super();
        initContextMenu();
    }


    @Override
    protected void updateItem( AbstractTreeItem item, boolean empty )
    {
        super.updateItem( item, empty );
        if( empty )
        {
            setContextMenu( null );
        }
        else
        {
            setContextMenu( contextMenu );
        }

    }


    private void initContextMenu()
    {
        contextMenu = new ContextMenu();
        MenuItem removeOperationMenuItem = new MenuItem( "Remove item" );
        removeOperationMenuItem.setOnAction( evt -> {
            this.getTreeItem().getParent().getChildren().remove( this.getTreeItem() );
        } );
        contextMenu.getItems().add( removeOperationMenuItem );
    }
}
