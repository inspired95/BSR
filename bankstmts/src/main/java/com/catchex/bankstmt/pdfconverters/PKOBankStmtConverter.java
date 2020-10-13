package com.catchex.bankstmt.pdfconverters;

import com.catchex.models.RawOperation;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.catchex.util.Constants.*;
import static com.catchex.util.Log.LOGGER;
import static com.catchex.util.Util.*;


public class PKOBankStmtConverter
    implements BankStmtConverter
{
    public static final String BANK_NAME = PKO;

    public PKOBankStmtConverter()
    {
    }


    @Override
    public List<RawOperation> convert(String name, String bankStatementPdf )
    {
        //LOGGER.info( "Converting started" );
        String[] bankStmtLines = split( Optional.ofNullable( bankStatementPdf ), "\\r?\\n" );
        List<RawOperation> bankStmtEntries = new ArrayList<>();

        for( int currentLineNumber = 0;
             currentLineNumber < bankStmtLines.length; currentLineNumber++ )
        {
            String currentLine = bankStmtLines[currentLineNumber];
            if( isFirstRowInEntry( currentLine ) )
            {
                String[] splittedFirstLineIntoWords =
                    split( Optional.ofNullable( currentLine ), " " );

                RawOperation.Builder operationBuilder =
                    new RawOperation.Builder( splittedFirstLineIntoWords[1] );
                operationBuilder.setFileName(name);
                operationBuilder.setDate( getDate( splittedFirstLineIntoWords[0] ) );
                operationBuilder.setType(
                    getOperationTypeDesc( Optional.ofNullable( splittedFirstLineIntoWords ) ) );
                operationBuilder.setAmount( getAmount( currentLine ) );

                currentLineNumber++;
                String[] splittedSecondLineIntoWords =
                    split( Optional.ofNullable( bankStmtLines[currentLineNumber] ), " " );

                operationBuilder.setDesc(
                    getDescription( currentLineNumber, splittedSecondLineIntoWords,
                        bankStmtLines ) );
                operationBuilder.setBank(BANK_NAME);

                if( operationBuilder.isValid() )
                    bankStmtEntries.add( operationBuilder.build() );
                //else
                    //LOGGER.warning( "Cannot convert entry: \n" + currentLine );
            }
        }
        //LOGGER.info( "Converting finished" );
        return bankStmtEntries;
    }


    private String getDescription(
        int lineNumber, String[] splittedSecondLineIntoWords, String[] bankStmtLines )
    {
        StringJoiner desc = new StringJoiner( " " );
        for( int i = 1; i < splittedSecondLineIntoWords.length; i++ )
        {
            desc.add( splittedSecondLineIntoWords[i] );
        }

        //Read next line until not the end of bank statement entry
        while( !isFirstRowInEntry( bankStmtLines[lineNumber + 1] ) &&
            !isBankStmtPageBalanceSummary( bankStmtLines[lineNumber + 1] ) &&
            !isBankStmtBalanceSummary( bankStmtLines[lineNumber + 1] ) )
        {
            lineNumber++;
            String nextLine = bankStmtLines[lineNumber];
            desc.add( nextLine );
        }
        return desc.toString();
    }


    private LocalDate getDate( String date )
    {
        LocalDate parsedDate = LocalDate.MIN;
        try
        {
            parsedDate = parseDate( Optional.ofNullable( date ), PKO_DATE_FORMAT );
        }
        catch( DateTimeParseException e )
        {
            LOGGER.warning( "Cannot parse date: " + date );
        }
        if( parsedDate.equals( LocalDate.MIN ) )
            LOGGER.warning( "Cannot parse date: " + date );
        return parseDate( Optional.ofNullable( date ), PKO_DATE_FORMAT );
    }


    private boolean isFirstRowInEntry( String line )
    {
        String[] splittedWords = split( Optional.ofNullable( line ), " " );
        if( splittedWords.length < 3 )
            return false;
        return isFirstWordDate( splittedWords[0] ) && isSecondWordOperationID( splittedWords[1] );
    }


    private boolean isFirstWordDate( String date )
    {
        return isValidDate( Optional.ofNullable( date ), PKO_DATE_FORMAT );
    }


    private boolean isSecondWordOperationID( String word )
    {
        return word.length() == 17;
    }


    private boolean isBankStmtPageBalanceSummary( String line )
    {
        return line.contains( PKO_BANK_STMT_PAGE_BALANCE_SUMMARY_TXT );
    }


    private boolean isBankStmtBalanceSummary( String line )
    {
        return line.contains( PKO_BANK_STMT_BALANCE_SUMMARY_TXT );
    }


    private Double getAmount( String line )
    {
        List<MatchResult> matches = Pattern.compile( REGEX_AMOUNT ).matcher( line ).results()
            .collect( Collectors.toList() );
        if( matches.size() == 2 )
        {
            Optional<String> numberToParse = Optional.ofNullable( matches.get( 0 ).group( 0 ) );
            return getNumberBasedOnLocale( numberToParse ).doubleValue();
        }
        LOGGER.warning( "Amount cannot be read correctly." );
        return Double.NaN;
    }


    private String getOperationTypeDesc( Optional<String[]> splittedLineIntoWords )
    {
        if( splittedLineIntoWords.isPresent() )
        {
            int lastWordIdx =
                findLastWordIndexOfOperationTypeDesc( 2, splittedLineIntoWords.get() );
            return combineString( splittedLineIntoWords, 2, lastWordIdx );
        }
        LOGGER.warning( "Cannot get description" );
        return "";
    }


    private int findLastWordIndexOfOperationTypeDesc( int firstWordIdx, String[] words )
    {
        int currentWordIdx;
        for( currentWordIdx = firstWordIdx; currentWordIdx < words.length; currentWordIdx++ )
        {
            if( words[currentWordIdx].matches( ".*\\d+.*" ) )
            {
                currentWordIdx--;
                break;
            }
        }
        return currentWordIdx;
    }
}
