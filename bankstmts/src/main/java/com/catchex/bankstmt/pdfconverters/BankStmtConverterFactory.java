package com.catchex.bankstmt.pdfconverters;

import java.util.Optional;
import java.util.StringJoiner;

import static com.catchex.util.Constants.PKO;
import static com.catchex.util.Log.LOGGER;


public class BankStmtConverterFactory
{
    public static Optional<BankStmtConverter> match( String chosenBank )
    {
        if( PKO.equals( chosenBank ) )
        {
            return Optional.of( new PKOBankStmtConverter() );
        }
        LOGGER.warning( getErrorMessage( chosenBank ) );
        return Optional.empty();
    }


    private static String getErrorMessage( String chosenBank )
    {
        return new StringJoiner( " " ).add( "Cannot match bank statement converter for" )
            .add( chosenBank ).toString();
    }
}
