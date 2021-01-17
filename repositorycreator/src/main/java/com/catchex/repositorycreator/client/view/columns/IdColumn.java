package com.catchex.repositorycreator.client.view.columns;

import com.catchex.repositorycreator.client.control.RepositoryCreatorDialogController;
import com.catchex.util.Constants;
import javafx.beans.property.ReadOnlyObjectWrapper;


public class IdColumn
    extends RepositoryColumn<String>
{
    public IdColumn( RepositoryCreatorDialogController controller )
    {
        super( Constants.ID, controller );
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
        setCellValueFactory( p -> new ReadOnlyObjectWrapper<>( p.getValue().getValue().getID() ) );
    }
}
