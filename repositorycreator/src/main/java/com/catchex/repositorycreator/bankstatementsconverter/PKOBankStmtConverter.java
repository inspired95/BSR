package com.catchex.repositorycreator.bankstatementsconverter;

import com.catchex.io.reader.ConfigurationReader;
import com.catchex.models.RawOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
import static com.catchex.util.Util.*;


public class PKOBankStmtConverter
    implements BankStmtConverter
{
    private static final Logger logger = LoggerFactory.getLogger( ConfigurationReader.class );

    public static final String BANK_NAME = PKO;

    private String myFileName;
    private String[] myBankStmtLines;

    private int lineToCheck = 0;


    @Override
    public List<RawOperation> convert( String name, String bankStatementPdf )
    {
        myFileName = name;
        myBankStmtLines = bankStatementPdf.split( "\\r?\\n" );
        logger.info( "Converting bank statement: {} started", myFileName );
        return convert();
    }


    private List<RawOperation> convert()
    {
        List<RawOperation> rawOperations = new ArrayList<>();
        while( lineToCheck < myBankStmtLines.length )
        {
            if( isFirstRowInEntry( myBankStmtLines[lineToCheck] ) )
            {
                RawOperationLines rawOperationLines = readNextRawOperationLines();
                RawOperation rawOperation = buildRawOperation( rawOperationLines );
                if( rawOperation.isValid() )
                {
                    rawOperations.add( rawOperation );
                }
                else
                {
                    logger.warn( "Bank statement entry is not valid: \n{}", rawOperationLines );
                }
            }
            else
            {
                lineToCheck++;

            }
        }
        logger.info( "Converting bank statement: {} finished", myFileName );
        if( rawOperations.isEmpty() )
        {
            logger.info( "File doesn't contain any PKO bank operations entries" );
        }
        return rawOperations;
    }


    private RawOperationLines readNextRawOperationLines()
    {
        String firstLine = myBankStmtLines[lineToCheck];
        String secondLine = myBankStmtLines[++lineToCheck];
        RawOperationLines rawOperationLines = new RawOperationLines( firstLine, secondLine );
        lineToCheck++;
        for( int i = 0; i < 2; i++ )
        {
            if( lineToCheck != myBankStmtLines.length )
            {
                String line = myBankStmtLines[lineToCheck];
                if( !isFirstRowInEntry( line ) && !isBankStmtPageBalanceSummary( line ) &&
                    !isBankStmtBalanceSummary( line ) )
                {
                    if( i == 0 )
                    {
                        rawOperationLines.setLine3( line );
                    }
                    else
                    {
                        rawOperationLines.setLine4( line );
                    }
                    lineToCheck++;
                }
                else
                {
                    break;
                }
            }
        }
        return rawOperationLines;
    }


    private RawOperation buildRawOperation(
        RawOperationLines rawOperationLines )
    {
        String[] firstLineWords = splitLineIntoWords( rawOperationLines.getMyLine1() );
        return new RawOperation.Builder( firstLineWords[1] ).setFileName( myFileName )
            .setDate( getDate( firstLineWords[0] ) )
            .setType( getOperationTypeDesc( firstLineWords ) )
            .setAmount( getAmount( rawOperationLines.getMyLine1() ) )
            .setDesc( getDescription( rawOperationLines ) ).setBank( BANK_NAME ).build();
    }


    private String getDescription(
        RawOperationLines lines )
    {
        StringJoiner desc = new StringJoiner( " " );
        desc.add( lines.getMyLine2().split( " ", 2 )[1] );
        lines.getMyLine3().ifPresent( desc::add );
        lines.getMyLine4().ifPresent( desc::add );
        return desc.toString();
    }


    private LocalDate getDate( String date )
    {
        try
        {
            return parseDate( Optional.ofNullable( date ), PKO_DATE_FORMAT );
        }
        catch( DateTimeParseException e )
        {
            logger.warn( "Cannot parse date: {}", date );
            return LocalDate.MIN;
        }
    }


    private boolean isFirstRowInEntry( String line )
    {
        String[] splittedWords = splitLineIntoWords( line );
        if( splittedWords.length < 3 )
            return false;
        return isFirstWordDate( splittedWords[0] ) && isSecondWordOperationID( splittedWords[1] );
    }


    private String[] splitLineIntoWords( String line )
    {
        return line.split( " " );
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
        List<MatchResult> matches = Pattern.compile( REGEX_AMOUNT2 ).matcher( line ).results()
            .collect( Collectors.toList() );
        if( matches.size() == 2 )
        {
            Optional<String> numberToParse = Optional.ofNullable( matches.get( 0 ).group( 0 ) );
            return getNumberBasedOnLocale( numberToParse ).doubleValue();
        }
        logger.warn( "Amount cannot be read correctly." );
        return Double.NaN;
    }


    private String getOperationTypeDesc( String[] splittedLineIntoWords )
    {
        int lastWordIdx = findLastWordIndexOfOperationTypeDesc( 2, splittedLineIntoWords );
        return combineString( splittedLineIntoWords, 2, lastWordIdx );
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


    private class RawOperationLines
    {
        private final String myLine1;
        private final String myLine2;
        private Optional<String> myLine3 = Optional.empty();
        private Optional<String> myLine4 = Optional.empty();


        public RawOperationLines( String line1, String line2 )
        {
            myLine1 = line1;
            myLine2 = line2;
        }


        public void setLine3( String line )
        {
            myLine3 = Optional.of( line );
        }


        public void setLine4( String line )
        {
            myLine4 = Optional.of( line );
        }


        public String getMyLine1()
        {
            return myLine1;
        }


        public String getMyLine2()
        {
            return myLine2;
        }


        public Optional<String> getMyLine3()
        {
            return myLine3;
        }


        public Optional<String> getMyLine4()
        {
            return myLine4;
        }


        @Override
        public String toString()
        {
            return myLine1 + '\n' + myLine2 + '\n' + myLine3.orElse( "" ) + '\n' +
                myLine4.orElse( "" );
        }
    }
}
