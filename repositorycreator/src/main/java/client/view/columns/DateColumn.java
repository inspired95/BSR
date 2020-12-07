package client.view.columns;

import client.control.RepositoryCreatorDialogController;
import client.view.model.OperationTreeItem;
import com.catchex.util.Constants;
import javafx.beans.property.ReadOnlyObjectWrapper;

import java.time.LocalDate;


public class DateColumn
    extends RepositoryColumn<String>
{
    public DateColumn( RepositoryCreatorDialogController controller )
    {
        super( Constants.DATE, controller );
    }


    @Override
    void init()
    {
        setCellValueFactory();
        setPrefWidth( 80 );
    }


    @Override
    void setCellValueFactory()
    {
        setCellValueFactory( p -> {
            if( p.getValue().getValue() instanceof OperationTreeItem )
            {
                LocalDate date = p.getValue().getValue().getDate();
                return new ReadOnlyObjectWrapper<>( date.toString() );
            }
            return new ReadOnlyObjectWrapper<>( "" );
        } );
    }
}
