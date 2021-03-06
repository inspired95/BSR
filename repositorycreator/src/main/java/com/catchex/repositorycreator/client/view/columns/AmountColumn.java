package com.catchex.repositorycreator.client.view.columns;

import com.catchex.repositorycreator.client.control.RepositoryCreatorDialogController;
import com.catchex.util.Constants;
import javafx.beans.property.ReadOnlyObjectWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;


public class AmountColumn
    extends RepositoryColumn<String>
{
    private static final Logger logger = LoggerFactory.getLogger( AmountColumn.class );

    private NumberFormat numberFormatter = NumberFormat.getInstance( Locale.getDefault() );


    public AmountColumn( RepositoryCreatorDialogController controller )
    {
        super( Constants.AMOUNT, controller );
    }


    private void setComparator()
    {
        setComparator( ( s, t1 ) -> {
            if( s.isEmpty() || t1.isEmpty() )
            {
                return 0;
            }

            Double val1 = getDoubleFromString( s );
            Double val2 = getDoubleFromString( t1 );
            return val1.compareTo( val2 );
        } );
    }


    private Double getDoubleFromString( String s )
    {
        try
        {
            return numberFormatter.parse( s ).doubleValue();
        }
        catch( ParseException e )
        {
            logger.warn( "Cannot amount to double value:" + s );
        }
        return Double.NaN;
    }


    @Override
    void init()
    {
        setCellValueFactory();
        setDefaultWidth();
        setComparator();
    }


    @Override
    void setCellValueFactory()
    {
        setCellValueFactory( p -> {
            Double amount = p.getValue().getValue().getAmount();
            if( amount.equals( Double.NaN ) )
                return new ReadOnlyObjectWrapper<>( "" );
            return new ReadOnlyObjectWrapper<>( String.format( "%.2f", amount ) );
        } );
    }


    void setDefaultWidth()
    {
        setPrefWidth( 100 );
    }
}
