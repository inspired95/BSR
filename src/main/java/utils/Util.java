package utils;

import javax.swing.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.logging.Logger;

import static java.util.logging.Logger.GLOBAL_LOGGER_NAME;
import static java.util.logging.Logger.getLogger;
import static utils.Constants.*;


public class Util
{
    private final static Logger LOGGER = getLogger( GLOBAL_LOGGER_NAME );


    public static String[] split( Optional<String> string, String regex )
    {
        if( string.isPresent() )
            return string.get().split( regex );
        LOGGER.warning( "Can not split null" );
        return new String[0];
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
                LOGGER.warning( "Cannot parse number:" + number.get() );
            }
        }
        return Double.NaN;
    }


    public static String combineString(
        Optional<String[]> words, int firstWordIdx, int lastWordIdx )
    {
        if( lastWordIdx < firstWordIdx )
        {
            LOGGER.warning( "Given last index is lower than first index" );
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
        LOGGER.warning( "Cannot combine null" );
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
            LOGGER.warning( "Second string is null" );
            return string1.get();
        }
        else if( !string1.isPresent() && string2.isPresent() )
        {
            LOGGER.warning( "First string is null" );
            return string2.get();
        }
        LOGGER.warning( "Cannot combine nulls" );
        return "";

    }

    public static void writeToFile( String content, Path path) throws IOException
    {
            Files.write( path, content.getBytes());
    }

    public static void showError(String msg){
        JOptionPane.showMessageDialog( null, msg, "Error", JOptionPane.ERROR_MESSAGE );
    }

    public static void showInformation(String msg){
        JOptionPane.showMessageDialog( null, msg, "Information", JOptionPane.INFORMATION_MESSAGE );
    }
}
