package client.view.columns;

import client.control.RepositoryCreatorDialogController;
import javafx.beans.property.ReadOnlyObjectWrapper;


public class BankNameColumn
    extends RepositoryColumn<String>
{
    public BankNameColumn( RepositoryCreatorDialogController controller )
    {
        super( "Bank", controller );
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
            p -> new ReadOnlyObjectWrapper<>( p.getValue().getValue().getBankName() ) );
    }
}
