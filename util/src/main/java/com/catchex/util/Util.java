package com.catchex.util;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Collection;
import java.util.Optional;
import java.util.StringJoiner;

import static com.catchex.util.Constants.CONFIGURATION_PATH;


public class Util
{
    private static final Logger logger = LoggerFactory.getLogger( Util.class );


    private Util()
    {

    }


    public static LocalDate parseDate( Optional<String> date, String format )
        throws DateTimeParseException
    {
        if( date.isPresent() )
        {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern( format );
            return LocalDate.parse( date.get(), formatter );
        }
        return LocalDate.MIN;
    }


    public static boolean isValidDate( Optional<String> date, String format )
    {
        if( date.isPresent() )
        {
            LocalDate parsedDate = LocalDate.MIN;
            try
            {
                parsedDate = parseDate( date, format );
            }
            catch( DateTimeParseException e )
            {

            }
            if( !parsedDate.equals( LocalDate.MIN ) )
                return true;
        }
        return false;
    }


    public static Number getNumberBasedOnLocale( Optional<String> number )
    {
        if( number.isPresent() )
        {
            NumberFormat format = NumberFormat.getInstance();
            try
            {
                return format.parse( number.get().replaceAll( "\\s", "" ) );
            }
            catch( ParseException e )
            {
                logger.warn( "Cannot parse number: {}", number.get() );
            }
        }
        return Double.NaN;
    }


    public static String combineString(
        String[] words, int firstWordIdx, int lastWordIdx )
    {
        if( lastWordIdx < firstWordIdx )
        {
            logger.warn( "Given last index is lower than first index" );
            return "";
        }
        StringJoiner combinedString = new StringJoiner( " " );
        for( int currentWordIdx = firstWordIdx; currentWordIdx <= lastWordIdx; currentWordIdx++ )
        {
            combinedString.add( words[currentWordIdx] );
        }
        return combinedString.toString();
    }


    public static String combineString( Optional<String> string1, Optional<String> string2 )
    {
        if( string1.isPresent() && string2.isPresent() )
        {
            return new StringJoiner( " " ).add( string1.get() ).add( string2.get() ).toString();
        }
        else if( string1.isPresent() )
        {
            logger.warn( "Second string is null" );
            return string1.get();
        }
        else if( string2.isPresent() )
        {
            logger.warn( "First string is null" );
            return string2.get();
        }
        logger.warn( "Cannot combine nulls" );
        return "";
    }


    public static String joinString( String delimiter, Collection<String> validationErrorMessages )
    {
        StringJoiner joiner = new StringJoiner( delimiter );
        for( String validationErrorMessage : validationErrorMessages )
        {
            joiner.add( validationErrorMessage );
        }
        return joiner.toString();
    }


    public static String getIntervalName( LocalDate date )
    {
        return date.format( Constants.intervalTreeItemFormatter );
    }


    public static boolean createDirectoryIfNeeded()
    {
        File bsrConfigurationDirectory = new File( CONFIGURATION_PATH );
        if( !bsrConfigurationDirectory.exists() )
        {
            try
            {
                return bsrConfigurationDirectory.mkdir();
            }
            catch( SecurityException e )
            {
                logger.error(
                    "Error during creating BSR configuration directory {}",
                    ExceptionUtils.getStackTrace( e ) );
            }

        }
        return true;
    }

}
