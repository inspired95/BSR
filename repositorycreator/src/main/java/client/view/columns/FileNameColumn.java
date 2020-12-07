package client.view.columns;

import client.control.RepositoryCreatorDialogController;
import com.catchex.util.Constants;
import javafx.beans.property.ReadOnlyObjectWrapper;


public class FileNameColumn
    extends RepositoryColumn<String>
{
    public FileNameColumn( RepositoryCreatorDialogController controller )
    {
        super( Constants.FILE_NAME, controller );
    }


    @Override
    void init()
    {
        setCellValueFactory();
        setPrefWidth( 100 );
        setVisible( false );
    }


    @Override
    void setCellValueFactory()
    {
        setCellValueFactory(
            p -> new ReadOnlyObjectWrapper<>( p.getValue().getValue().getFileName() ) );
    }
}
