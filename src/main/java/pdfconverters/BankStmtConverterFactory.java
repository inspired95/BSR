package pdfconverters;

import javax.swing.*;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.logging.Logger;

import static java.util.logging.Logger.GLOBAL_LOGGER_NAME;
import static java.util.logging.Logger.getLogger;
import static utils.Constants.PKO;


public class BankStmtConverterFactory
{
    private final static Logger LOGGER = getLogger( GLOBAL_LOGGER_NAME );


    public Optional<BankStmtConverter> match( String chosenBank )
    {
        if( PKO.equals( chosenBank ) )
        {
            return Optional.of( new PKOBankStmtConverter() );
        }
        reportError( getErrorMessage( chosenBank ) );
        return Optional.empty();
    }

    private String getErrorMessage( String chosenBank ){
        return new StringJoiner( " " ).add( "Cannot match bank statement converter for" )
            .add( chosenBank ).toString();
    }

    private void reportError( String errorMessage ){
        LOGGER.warning( errorMessage );
        JOptionPane.showMessageDialog( null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE );
    }

}
