package com.catchex.repositorycreator.bankstatementsconverter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.StringJoiner;

import static com.catchex.util.Constants.PKO;


public class BankStmtConverterFactory
{
    private static final Logger logger = LoggerFactory.getLogger( BankStmtConverterFactory.class );


    public static Optional<BankStmtConverter> match( String chosenBank )
    {
        if( PKO.equals( chosenBank ) )
        {
            return Optional.of( new PKOBankStmtConverter() );
        }
        logger.warn( getErrorMessage( chosenBank ) );
        return Optional.empty();
    }


    private static String getErrorMessage( String chosenBank )
    {
        return new StringJoiner( " " ).add( "Cannot match bank statement converter for" )
            .add( chosenBank ).toString();
    }
}
