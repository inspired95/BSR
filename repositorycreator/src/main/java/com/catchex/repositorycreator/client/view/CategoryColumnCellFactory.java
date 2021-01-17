package com.catchex.repositorycreator.client.view;

import com.catchex.models.Category;
import com.catchex.repositorycreator.client.view.model.AbstractTreeItem;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableRow;


public class CategoryColumnCellFactory
    extends TreeTableCell<AbstractTreeItem, String>
{

    @Override
    protected void updateItem( String item, boolean empty )
    {
        super.updateItem( item, empty );
        TreeTableRow<AbstractTreeItem> ttr = getTreeTableRow();
        if( item == null || empty )
        {
            setText( null );
            ttr.setStyle( "" );
            setStyle( "" );
        }
        else
        {
            if( item.equals( Category.OTHER_CATEGORY.getCategoryName() ) )
            {
                setStyle( "-fx-background-color:rgba(228, 117, 27, 0.36);" );
            }
            else
            {
                setStyle( "" );
            }
            setText( item );
        }
    }
}
