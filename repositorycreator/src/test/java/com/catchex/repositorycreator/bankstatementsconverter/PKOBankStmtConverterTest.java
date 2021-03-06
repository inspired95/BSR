package com.catchex.repositorycreator.bankstatementsconverter;

import com.catchex.models.RawOperation;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static com.catchex.util.Constants.*;
import static com.catchex.util.Util.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class PKOBankStmtConverterTest
{
    static BankStmtConverter bankStmtConverter;
    private static String dummyBankStatementFileName = "bankStatement.pdf";

    //Correct PKO Bank Statement data
    private static String correctID = "SOME_ID_123456789";
    private static String correctID2 = "SOME_ID_999999999";
    private static String correctDate = "20.11.2018";
    private static String correctTypeDescription = DEBIT_CARD_PAYMENT_RESOLVER_TXT_PL;
    private static String correctAmount = "4 655,45";
    private static String correctDescLine1 = "SOME_OPERATION_DESCRIPTION";
    private static String correctDescLine2 = "SOME_OPERATION_DESCRIPTION";


    @BeforeAll
    public static void setupSuite()
    {
        bankStmtConverter = new BankStmtConverterFactory().match( PKO ).get();
    }


    private static Stream<Arguments> incompletePKOStmtEntries()
    {
        return Stream.of( Arguments.of( "Wrong amount", getEntryWithWrongAmount() ),
            Arguments.of( "Wrong date", getEntryWithWrongDate() ),
            Arguments.of( "No ID", getEntryWithoutID() ),
            Arguments.of( "Wrong description", getEntryWithoutDescription() ) );
    }


    private static PKOBankStmtEntryPdfMock getEntryWithoutID()
    {
        return new PKOBankStmtEntryPdfMock.Builder().setID( "" ).setDate( correctDate )
            .setTypeDescription( correctTypeDescription ).setAmount( correctAmount )
            .setDescLine1( correctDescLine1 ).setDescLine2( correctDescLine2 ).build();
    }


    private static PKOBankStmtEntryPdfMock getEntryWithWrongDate()
    {
        return new PKOBankStmtEntryPdfMock.Builder().setID( correctID ).setDate( "wr.on.gDate" )
            .setTypeDescription( correctTypeDescription ).setAmount( correctAmount )
            .setDescLine1( correctDescLine1 ).setDescLine2( correctDescLine2 ).build();
    }


    private static PKOBankStmtEntryPdfMock getEntryWithWrongAmount()
    {
        return new PKOBankStmtEntryPdfMock.Builder().setID( correctID ).setDate( correctDate )
            .setTypeDescription( correctTypeDescription ).setAmount( "999999.99" )
            .setDescLine1( correctDescLine1 ).setDescLine2( correctDescLine2 ).build();
    }


    private static PKOBankStmtEntryPdfMock getEntryWithoutDescription()
    {
        return new PKOBankStmtEntryPdfMock.Builder().setID( correctID ).setDate( correctDate )
            .setTypeDescription( correctTypeDescription ).setAmount( correctAmount )
            .setDescLine1( "" ).build();
    }


    @Test
    public void should_Add_Entry_When_Correct_Entry_Given()
    {
        //GIVEN
        PKOBankStmtEntryPdfMock stmtEntryPdfMock = getCorrectEntry( correctID );
        PKOBankStmtEntryPdfMock stmtEntryPdfMock2 = getCorrectEntry( correctID2 );

        PKOBankStmtPdfMock bankStmtPdfMock =
            new PKOBankStmtPdfMock().startBankStmt().addEntry( stmtEntryPdfMock ).addPageSplitter()
                .addEntry( stmtEntryPdfMock2 ).summarize();

        //WHEN
        List<RawOperation> converted =
            bankStmtConverter.convert( dummyBankStatementFileName, bankStmtPdfMock.getContent() );

        //THEN
        assertEquals( 2, converted.size() );
        RawOperation convertedEntry = converted.get( 0 );
        RawOperation convertedEntry2 = converted.get( 1 );
        assertEquals( correctID, convertedEntry.getID() );
        assertEquals( correctID2, convertedEntry2.getID() );
        assertEquals(
            parseDate( Optional.of( correctDate ), PKO_DATE_FORMAT ), convertedEntry.getDate() );
        assertEquals(
            parseDate( Optional.of( correctDate ), PKO_DATE_FORMAT ), convertedEntry2.getDate() );
        assertEquals( getNumberBasedOnLocale( Optional.of( correctAmount ) ).doubleValue(),
            convertedEntry.getAmount() );
        assertEquals( getNumberBasedOnLocale( Optional.of( correctAmount ) ).doubleValue(),
            convertedEntry2.getAmount() );
        assertEquals(
            combineString( Optional.of( correctDescLine1 ), Optional.of( correctDescLine2 ) ),
            convertedEntry.getDesc() );
        assertEquals(
            combineString( Optional.of( correctDescLine1 ), Optional.of( correctDescLine2 ) ),
            convertedEntry2.getDesc() );
    }


    @ParameterizedTest
    @MethodSource( "incompletePKOStmtEntries" )
    public void should_Skip_Entry_When_Incomplete(
        String testName, PKOBankStmtEntryPdfMock stmtEntryPdfMock )
    {
        //GIVEN
        PKOBankStmtPdfMock bankStmtPdfMock =
            new PKOBankStmtPdfMock().startBankStmt().addEntry( stmtEntryPdfMock ).summarize();

        //WHEN
        List<RawOperation> converted =
            bankStmtConverter.convert( dummyBankStatementFileName, bankStmtPdfMock.getContent() );

        //THEN
        assertTrue( converted.isEmpty() );
    }


    private PKOBankStmtEntryPdfMock getCorrectEntry( String entryID )
    {
        return new PKOBankStmtEntryPdfMock.Builder().setID( entryID ).setDate( correctDate )
            .setTypeDescription( correctTypeDescription ).setAmount( correctAmount )
            .setDescLine1( correctDescLine1 ).setDescLine2( correctDescLine2 ).build();
    }
}
