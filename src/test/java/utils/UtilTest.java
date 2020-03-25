package utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Locale;
import java.util.Optional;
import java.util.StringJoiner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static utils.Util.*;

public class UtilTest {

    @Test
    public void should_Split_Correctly_When_NonNull_Given(){
        //GIVEN
        String ala = "Ala";
        String ma = "ma";
        String kota = "kota";
        String textToSplit = new StringJoiner(" ").add(ala).add(ma).add(kota).toString();

        //WHEN
        String[] splittedStrings = split(Optional.of(textToSplit), " ");

        //THEN
        assertEquals(3, splittedStrings.length);
        assertEquals(ala, splittedStrings[0]);
        assertEquals(ma, splittedStrings[1]);
        assertEquals(kota, splittedStrings[2]);
    }

    @Test
    public void should_Return_Empty_Array_When_Null_Given(){
        //GIVEN
        String textToSplit = null;
        //WHEN
        String[] splittedStrings = split(Optional.ofNullable(textToSplit), " ");

        //THEN
        assertEquals(0, splittedStrings.length);
    }

    @Test
    public void should_Parse_Corretly_When_Date_In_Format_Given(){
        //GIVEN
        String dateToParse = "01.01.2001";
        String format = "dd.MM.yyyy";

        //WHEN
        LocalDate date = parseDate(Optional.of(dateToParse), format);

        //THEN
        assertEquals(LocalDate.of(2001, 1, 1), date);
    }

    @Test
    public void should_Return_LocalDate_MIN_When_Wrong_Date_Given(){
        //GIVEN
        String dateToParse = "wr/on/gDate";
        String format = "dd.MM.yyyy";

        //WHEN&THEN
        Assertions.assertThrows(DateTimeParseException.class, () -> {
            parseDate(Optional.of(dateToParse), format);
        });

    }

    @Test
    public void should_Return_LocalDate_MIN_When_Null_Given(){
        //GIVEN
        String dateToParse = null;
        String format = "dd.MM.yyyy";

        //WHEN
        LocalDate date = parseDate(Optional.ofNullable(dateToParse), format);

        //THEN
        assertEquals(LocalDate.MIN, date);
    }

    @Test
    public void should_Return_False_When_Null_Given(){
        //GIVEN
        String dateToParse = null;
        String format = "dd.MM.yyyy";

        //WHEN
        Boolean validity = isValidDate(Optional.ofNullable(dateToParse), format);

        //THEN
        assertEquals(false, validity);
    }

    @Test
    public void should_Return_False_When_Wrong_Date_Given(){
        //GIVEN
        String dateToParse = "wr/on/gDate";
        String format = "dd.MM.yyyy";

        //WHEN
        Boolean validity = isValidDate(Optional.ofNullable(dateToParse), format);

        //THEN
        assertEquals(false, validity);
    }

    @Test
    public void should_Return_True_When_Date_In_Format_Given_Given(){
        //GIVEN
        String dateToParse = "01.01.2001";
        String format = "dd.MM.yyyy";

        //WHEN
        Boolean validity = isValidDate(Optional.ofNullable(dateToParse), format);

        //THEN
        assertEquals(true, validity);
    }

    @Test
    public void should_Parse_Correctly_When_Number_In_Locale_Given(){
        //GIVEN
        String numberToParse = "9 999,99";
        Locale locale = Locale.FRANCE;

        //WHEN
        Number parsedNumber = getNumberBasedOnLocale(Optional.of(numberToParse), locale);

        //THEN
        assertEquals(9999.99, parsedNumber);
    }
}
