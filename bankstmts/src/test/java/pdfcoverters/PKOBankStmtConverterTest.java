package pdfcoverters;

import com.catchex.bankstmt.pdfconverters.BankStmtConverter;
import com.catchex.bankstmt.pdfconverters.BankStmtConverterFactory;
import com.catchex.models.RawOperation;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static com.catchex.util.Constants.DEBIT_CARD_PAYMENT_RESOLVER_TXT_PL;
import static com.catchex.util.Constants.PKO_DATE_FORMAT;
import static com.catchex.util.Util.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class PKOBankStmtConverterTest
{
    static BankStmtConverter bankStmtConverter;
    private static String dummyBankStatementFileName = "bankStatement.pdf";
    private static String bankName = "PKO";

    //Correct PKO Bank Statement data
    private static String correctID = "SOME_ID_123456789";
    private static String correctDate = "20.11.2018";
    private static String correctTypeDescription = DEBIT_CARD_PAYMENT_RESOLVER_TXT_PL;
    private static String correctAmount = "-4 655,45";
    private static String correctDescLine1 = "SOME_OPERATION_DESCRIPTION";
    private static String correctDescLine2 = "SOME_OPERATION_DESCRIPTION";


    @BeforeAll
    public static void setupSuite()
    {
        bankStmtConverter = new BankStmtConverterFactory().match( bankName ).get();
    }


    @Test
    public void should_Add_Entry_When_Correct_Entry_Given()
    {
        //GIVEN
        PKOBankStmtEntryPdfMock stmtEntryPdfMock = getCorrectEntry();

        PKOBankStmtPdfMock bankStmtPdfMock =
            new PKOBankStmtPdfMock().addEntry( stmtEntryPdfMock ).summarize();

        //WHEN
        List<RawOperation> converted =
            bankStmtConverter.convert( dummyBankStatementFileName, bankStmtPdfMock.getContent() );

        //THEN
        assertEquals( 1, converted.size() );
        RawOperation convertedEntry = converted.get( 0 );
        assertEquals( correctID, convertedEntry.getID() );
        assertEquals(
            parseDate( Optional.of( correctDate ), PKO_DATE_FORMAT ), convertedEntry.getDate() );
        assertEquals( getNumberBasedOnLocale( Optional.of( correctAmount ) ).doubleValue(),
            convertedEntry.getAmount() );
        assertEquals(
            combineString( Optional.of( correctDescLine1 ), Optional.of( correctDescLine2 ) ),
            convertedEntry.getDesc() );
    }


    private PKOBankStmtEntryPdfMock getCorrectEntry()
    {
        return new PKOBankStmtEntryPdfMock.Builder().setID( correctID ).setDate( correctDate )
            .setTypeDescription( correctTypeDescription ).setAmount( correctAmount )
            .setDescLine1( correctDescLine1 ).setDescLine2( correctDescLine2 ).build();
    }


    @ParameterizedTest
    @MethodSource( "incompletePKOStmtEntries" )
    public void should_Skip_Entry_When_Incomplete( PKOBankStmtEntryPdfMock stmtEntryPdfMock )
    {
        //GIVEN
        PKOBankStmtPdfMock bankStmtPdfMock =
            new PKOBankStmtPdfMock().addEntry( stmtEntryPdfMock ).summarize();

        //WHEN
        List<RawOperation> converted =
            bankStmtConverter.convert( dummyBankStatementFileName, bankStmtPdfMock.getContent() );

        //THEN
        assertTrue( converted.isEmpty() );
    }


    private static Stream<Arguments> incompletePKOStmtEntries()
    {
        return Stream
            .of( Arguments.of( getEntryWithWrongAmount() ), Arguments.of( getEntryWithWrongDate() ),
                Arguments.of( getEntryWithoutID() ), Arguments.of( getEntryWithoutDescription() ) );
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
            .setTypeDescription( correctTypeDescription ).setAmount( "WR0NG_AM0UNT" )
            .setDescLine1( correctDescLine1 ).setDescLine2( correctDescLine2 ).build();
    }


    private static PKOBankStmtEntryPdfMock getEntryWithoutDescription()
    {
        return new PKOBankStmtEntryPdfMock.Builder().setID( correctID ).setDate( correctDate )
            .setTypeDescription( correctTypeDescription ).setAmount( correctAmount )
            .setDescLine1( "" ).build();
    }
}
