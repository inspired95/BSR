package utils;

import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;
import java.util.StringJoiner;

public class Util {
    public static String[] splitIntoLines(String bankStmt) {
        return bankStmt.split("\\r?\\n");
    }

    public static String[] splitIntoWord(String line) {
        return line.split(" ");
    }

    public static LocalDate parseDate(String dateStr, String format) throws DateTimeParseException{
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return LocalDate.parse(dateStr, formatter);
    }

    public static boolean isValidDate(String dateStr, String format) {
        try {
            parseDate(dateStr, format);
        } catch (DateTimeParseException e) {
            return false;
        }
        return true;
    }

    public static Number getNumberBasedOnLocale(String number, Locale locale)
    {
        NumberFormat format = NumberFormat.getInstance( locale );
        try {
            return format.parse(number.replaceAll("\\s", ""));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return Double.NaN;
    }

    public static String combineString(String[] words, int firstWordIdx, int lastWordIdx) {
        StringJoiner combinedString = new StringJoiner (" ");
        for (int currentWordIdx = firstWordIdx; currentWordIdx <= lastWordIdx; currentWordIdx++){
            combinedString.add(words[currentWordIdx]);
        }
        return combinedString.toString();
    }

    public static String combineString( String string1, String string2 ){
        return new StringJoiner( " ").add(string1).add(string2).toString();
    }
}
