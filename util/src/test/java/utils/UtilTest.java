package utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Optional;
import java.util.StringJoiner;

import static com.catchex.util.Util.*;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class UtilTest
{

    @Test
    public void should_Split_Correctly_When_NonNull_Given()
    {
        //GIVEN
        String ala = "Ala";
        String ma = "ma";
        String kota = "kota";
        String textToSplit = new StringJoiner( " " ).add( ala ).add( ma ).add( kota ).toString();

        //WHEN
        String[] splittedStrings = split( Optional.of( textToSplit ), " " );

        //THEN
        assertEquals( 3, splittedStrings.length );
        assertEquals( ala, splittedStrings[0] );
        assertEquals( ma, splittedStrings[1] );
        assertEquals( kota, splittedStrings[2] );
    }


    @Test
    public void should_Return_Empty_Array_When_Null_Given()
    {
        //WHEN
        String[] splittedStrings = split( Optional.empty(), " " );

        //THEN
        assertEquals( 0, splittedStrings.length );
    }


    @Test
    public void should_Parse_Corretly_When_Date_In_Format_Given()
    {
        //GIVEN
        String dateToParse = "01.01.2001";
        String format = "dd.MM.yyyy";

        //WHEN
        LocalDate date = parseDate( Optional.of( dateToParse ), format );

        //THEN
        assertEquals( LocalDate.of( 2001, 1, 1 ), date );
    }


    @Test
    public void should_Return_LocalDate_MIN_When_Wrong_Date_Given()
    {
        //GIVEN
        String dateToParse = "wr/on/gDate";
        String format = "dd.MM.yyyy";

        //WHEN&THEN
        Assertions.assertThrows( DateTimeParseException.class,
            () -> parseDate( Optional.of( dateToParse ), format ) );

    }


    @Test
    public void should_Return_LocalDate_MIN_When_Null_Given()
    {
        //GIVEN
        String format = "dd.MM.yyyy";

        //WHEN
        LocalDate date = parseDate( Optional.empty(), format );

        //THEN
        assertEquals( LocalDate.MIN, date );
    }


    @Test
    public void should_Return_False_When_Null_Given()
    {
        //GIVEN
        String format = "dd.MM.yyyy";

        //WHEN
        Boolean validity = isValidDate( Optional.empty(), format );

        //THEN
        assertEquals( false, validity );
    }


    @Test
    public void should_Return_False_When_Wrong_Date_Given()
    {
        //GIVEN
        String dateToParse = "wr/on/gDate";
        String format = "dd.MM.yyyy";

        //WHEN
        Boolean validity = isValidDate( Optional.of( dateToParse ), format );

        //THEN
        assertEquals( false, validity );
    }


    @Test
    public void should_Return_True_When_Date_In_Format_Given_Given()
    {
        //GIVEN
        String dateToParse = "01.01.2001";
        String format = "dd.MM.yyyy";

        //WHEN
        Boolean validity = isValidDate( Optional.of( dateToParse ), format );

        //THEN
        assertEquals( true, validity );
    }


    @Test
    public void should_Return_NaN_When_Wrong_Number_Given()
    {
        //GIVEN
        String numberToParse = "O.SD";

        //WHEN
        Number parsedNumber = getNumberBasedOnLocale( Optional.of( numberToParse ) );

        //THEN
        assertEquals( Double.NaN, parsedNumber );
    }


    @Test
    public void should_Combine_String_When_Correct_Parameters_Given()
    {
        //GIVEN
        String[] strings = { "Ala", "ma", "kota", "a", "ja", "mam", "psa" };

        //WHEN
        String combinedString = combineString( Optional.of( strings ), 1, 3 );

        //THEN
        assertEquals( "ma kota a", combinedString );
    }


    @Test
    public void should_Return_Empty_String_When_Null_As_Array_Given()
    {
        //WHEN
        String combinedString = combineString( Optional.empty(), 1, 3 );

        //THEN
        assertEquals( "", combinedString );
    }


    @Test
    public void should_Return_Empty_String_When_Given_LastIdx_Is_Lower_Then_FirstIdx()
    {
        //WHEN
        String combinedString = combineString( Optional.empty(), 4, 3 );

        //THEN
        assertEquals( "", combinedString );
    }


    @Test
    public void should_Combine_When_Strings_Given()
    {
        //GIVEN
        String string1 = "Ala";
        String string2 = "ma kota";

        //WHEN
        String combinedString = combineString( Optional.of( string1 ), Optional.of( string2 ) );

        //THEN
        assertEquals( "Ala ma kota", combinedString );
    }


    @Test
    public void should_Return_String1_When_Null_As_String2_Given()
    {
        //GIVEN
        String string1 = "Ala";

        //WHEN
        String combinedString = combineString( Optional.of( string1 ), Optional.empty() );

        //THEN
        assertEquals( "Ala", combinedString );
    }


    @Test
    public void should_Return_String2_When_Null_As_String1_Given()
    {
        //GIVEN
        String string2 = "ma kota";

        //WHEN
        String combinedString = combineString( Optional.empty(), Optional.of( string2 ) );

        //THEN
        assertEquals( "ma kota", combinedString );
    }


    @Test
    public void should_Return_Empty_String_When_Nulls_As_Strings_Given()
    {
        //WHEN
        String combinedString = combineString( Optional.empty(), Optional.empty() );

        //THEN
        assertEquals( "", combinedString );
    }

}
