package pdfcoverters;

import model.BankStmtEntry;
import operationtype.OperationType;
import org.junit.jupiter.api.Test;
import pdfconverters.BankStmtConverter;
import pdfconverters.BankStmtConverterFactory;

import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static utils.Constants.DEBIT_CARD_PAYMENT_RESOLVER_TXT_PL;
import static utils.Constants.PKO_DATE_FORMAT;
import static utils.Util.*;

public class PKOBankStmtConverterTest {
    private String bankName = "PKO";
    @Test
    public void should_CorrectlyConvert_When_CorrectBankStmtPdfGiven(){
        //GIVEN
        //PKO Bank Statement data
        String id = "SOME_ID_123456789";
        String date = "20.11.2018";
        String typeDescription = DEBIT_CARD_PAYMENT_RESOLVER_TXT_PL;
        String amount = "-4 655,45";
        String descLine1 = "SOME_OPERATION_DESCRIPTION";
        String descLine2 = "SOME_OPERATION_DESCRIPTION";

        PKOBankStmtEntryPdfMock.Builder entryPdfMockBuilder = new PKOBankStmtEntryPdfMock.Builder();
        entryPdfMockBuilder.setDate( date ).setID( id ).setTypeDescription( typeDescription ).setAmount( amount );
        entryPdfMockBuilder.setDescLine1( descLine1 );
        entryPdfMockBuilder.setDescLine2(descLine2);

        PKOBankStmtEntryPdfMock stmtEntryPdfMock = entryPdfMockBuilder.build();

        PKOBankStmtPdfMock bankStmtPdfMock = new PKOBankStmtPdfMock().addEntry(stmtEntryPdfMock).summarize();
        BankStmtConverter bankStmtConverter = new BankStmtConverterFactory().match( bankName );

        //WHEN
        List<BankStmtEntry> converted = bankStmtConverter.convert( bankStmtPdfMock.getContent() );

        //THEN
        assertTrue(converted.size() == 1);
        BankStmtEntry convertedEntry = converted.get(0);
        assertEquals(id, convertedEntry.getID());
        assertEquals(parseDate(date, PKO_DATE_FORMAT), convertedEntry.getDate());
        assertEquals(OperationType.DEBIT_CARD_PAYMENT, convertedEntry.getType());
        assertEquals(getNumberBasedOnLocale(amount, Locale.FRANCE).doubleValue(),convertedEntry.getAmount());
        assertEquals( combineString( descLine1, descLine2), convertedEntry.getDesc() );
    }

    @Test
    public void should_Skip_Entry_When_No_Id_Found(){
        //GIVEN
        //PKO Bank Statement data
        String ID = "";
        String date = "20.11.2018";
        String typeDescription = "SOME_OPERATION_TYPE_DESCRIPTION";

        PKOBankStmtEntryPdfMock.Builder entryPdfMockBuilder = new PKOBankStmtEntryPdfMock.Builder();
        entryPdfMockBuilder.setID(ID).setDate( date ).setTypeDescription( typeDescription );

        PKOBankStmtEntryPdfMock stmtEntryPdfMock = entryPdfMockBuilder.build();

        PKOBankStmtPdfMock bankStmtPdfMock = new PKOBankStmtPdfMock().addEntry(stmtEntryPdfMock).summarize();
        BankStmtConverter bankStmtConverter = new BankStmtConverterFactory().match( bankName );

        //WHEN
        List<BankStmtEntry> converted = bankStmtConverter.convert( bankStmtPdfMock.getContent() );

        //THEN
        assertTrue(converted.isEmpty());
    }

    @Test
    public void should_Skip_Entry_When_Wron_Date_Given() {
        //GIVEN
        //PKO Bank Statement data
        String id = "SOME_ID_123456789";
        String date = "wr.on.gDate";
        String typeDescription = DEBIT_CARD_PAYMENT_RESOLVER_TXT_PL;

        PKOBankStmtEntryPdfMock.Builder entryPdfMockBuilder = new PKOBankStmtEntryPdfMock.Builder();
        entryPdfMockBuilder.setDate( date ).setID( id ).setTypeDescription( typeDescription );

        PKOBankStmtEntryPdfMock stmtEntryPdfMock = entryPdfMockBuilder.build();

        PKOBankStmtPdfMock bankStmtPdfMock = new PKOBankStmtPdfMock().addEntry(stmtEntryPdfMock).summarize();
        BankStmtConverter bankStmtConverter = new BankStmtConverterFactory().match( bankName );

        //WHEN
        List<BankStmtEntry> converted = bankStmtConverter.convert( bankStmtPdfMock.getContent() );

        //THEN
        assertTrue(converted.isEmpty());
    }

    @Test
    public void should(){
        //GIVEN
        //PKO Bank Statement data
        String id = "SOME_ID_123456789";
        String date = "20.11.2018";
        String typeDescription = DEBIT_CARD_PAYMENT_RESOLVER_TXT_PL;
        String amount = "WRONG_AMOUNT";

        PKOBankStmtEntryPdfMock.Builder entryPdfMockBuilder = new PKOBankStmtEntryPdfMock.Builder();
        entryPdfMockBuilder.setDate( date ).setID( id ).setTypeDescription( typeDescription ).setAmount( amount );

        PKOBankStmtEntryPdfMock stmtEntryPdfMock = entryPdfMockBuilder.build();

        PKOBankStmtPdfMock bankStmtPdfMock = new PKOBankStmtPdfMock().addEntry(stmtEntryPdfMock).summarize();
        BankStmtConverter bankStmtConverter = new BankStmtConverterFactory().match( bankName );

        //WHEN
        List<BankStmtEntry> converted = bankStmtConverter.convert( bankStmtPdfMock.getContent() );

        //THEN
        assertTrue(converted.isEmpty());
    }
}
