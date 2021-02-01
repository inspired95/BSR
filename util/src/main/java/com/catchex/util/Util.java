package com.catchex.util;

import com.catchex.logging.Log;

import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Collection;
import java.util.Optional;
import java.util.StringJoiner;


public class Util
{
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
                parsedDate = parseDate( Optional.of( date.get() ), format );
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
                Log.LOGGER.warning( "Cannot parse number:" + number.get() );
            }
        }
        return Double.NaN;
    }


    public static String combineString(
        Optional<String[]> words, int firstWordIdx, int lastWordIdx )
    {
        if( lastWordIdx < firstWordIdx )
        {
            Log.LOGGER.warning( "Given last index is lower than first index" );
            return "";
        }
        if( words.isPresent() )
        {
            StringJoiner combinedString = new StringJoiner( " " );
            for( int currentWordIdx = firstWordIdx;
                 currentWordIdx <= lastWordIdx; currentWordIdx++ )
            {
                combinedString.add( words.get()[currentWordIdx] );
            }
            return combinedString.toString();
        }
        Log.LOGGER.warning( "Cannot combine null" );
        return "";
    }


    public static String combineString( Optional<String> string1, Optional<String> string2 )
    {
        if( string1.isPresent() && string2.isPresent() )
        {
            return new StringJoiner( " " ).add( string1.get() ).add( string2.get() ).toString();
        }
        else if( string1.isPresent() && !string2.isPresent() )
        {
            Log.LOGGER.warning( "Second string is null" );
            return string1.get();
        }
        else if( !string1.isPresent() && string2.isPresent() )
        {
            Log.LOGGER.warning( "First string is null" );
            return string2.get();
        }
        Log.LOGGER.warning( "Cannot combine nulls" );
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
}
