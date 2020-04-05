package pdfconverters;

import java.util.Optional;
import java.util.StringJoiner;

import static app.Log.LOGGER;
import static utils.Constants.PKO;


public class BankStmtConverterFactory
{
    public Optional<BankStmtConverter> match( String chosenBank )
    {
        if( PKO.equals( chosenBank ) )
        {
            return Optional.of( new PKOBankStmtConverter() );
        }
        LOGGER.warning( getErrorMessage( chosenBank ) );
        return Optional.empty();
    }


    private String getErrorMessage( String chosenBank )
    {
        return new StringJoiner( " " ).add( "Cannot match bank statement converter for" )
            .add( chosenBank ).toString();
    }
}
