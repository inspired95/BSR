package client.view.columns;

import client.control.RepositoryCreatorDialogController;
import client.view.CategoryColumnCellFactory;
import client.view.model.AbstractTreeItem;
import client.view.model.OperationTreeItem;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.scene.control.TreeTableColumn;


public class CategoryColumn
    extends RepositoryColumn<String>
{

    public CategoryColumn( RepositoryCreatorDialogController controller )
    {
        super( "Category", controller );
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
