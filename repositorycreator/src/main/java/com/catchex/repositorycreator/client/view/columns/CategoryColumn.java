package com.catchex.repositorycreator.client.view.columns;

import com.catchex.repositorycreator.client.control.RepositoryCreatorDialogController;
import com.catchex.repositorycreator.client.view.CategoryColumnCellFactory;
import com.catchex.repositorycreator.client.view.model.AbstractTreeItem;
import com.catchex.repositorycreator.client.view.model.OperationTreeItem;
import com.catchex.util.Constants;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.scene.control.TreeTableColumn;


public class CategoryColumn
    extends RepositoryColumn<String>
{

    public CategoryColumn( RepositoryCreatorDialogController controller )
    {
        super( Constants.CATEGORY, controller );
    }


    @Override
    void init()
    {
        setCellValueFactory();
        setCellFactory();
        setPrefWidth( 75 );
    }


    @Override
    void setCellValueFactory()
    {
        setCellValueFactory( p -> {
            if( p.getValue().getValue() instanceof OperationTreeItem )
            {
                return new ReadOnlyObjectWrapper<>( p.getValue().getValue().getCategory() );
            }
            return new ReadOnlyObjectWrapper<>( "" );
        } );
    }


    private void setCellFactory()
    {
        setCellFactory(
            ( TreeTableColumn<AbstractTreeItem, String> param ) -> new CategoryColumnCellFactory() );
    }
}
