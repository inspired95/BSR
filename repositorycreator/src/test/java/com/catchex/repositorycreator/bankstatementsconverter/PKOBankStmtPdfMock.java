package com.catchex.repositorycreator.bankstatementsconverter;

import java.util.StringJoiner;

import static com.catchex.util.Constants.PKO_BANK_STMT_BALANCE_SUMMARY_TXT;
import static com.catchex.util.Constants.PKO_BANK_STMT_PAGE_BALANCE_SUMMARY_TXT;


public class PKOBankStmtPdfMock
{
    StringJoiner content;


    public PKOBankStmtPdfMock()
    {
        content = new StringJoiner( "\n" );
    }


    public PKOBankStmtPdfMock startBankStmt()
    {
        content.add( "WYCIĄG za okres 26.11.2019 - 23.12.2019" );
        content.add( "Nr: 12/2019 Data: 23.12.2019" );
        content.add( "PKO BP SA Oddział 99 w Miejscowość" );
        content.add( "ul. Długa 99" );
        content.add( "99-999 Miejscowość" );
        content.add( "NAZWISKO IMIĘ" );
        content.add( "UL. WĄSKA 99 M.99" );
        content.add( "99-999 MIEJSCOWOŚĆ" );
        content.add( "Nr rachunku/karty: 99 9999 9999 9999 9999 9999 9999" );
        content.add( "Nr IBAN: PL 99 9999 9999 9999 9999 9999 9999" );
        content.add( "Rodzaj rachunku: PKO KONTO DLA MŁODYCH" );
        content.add( "Waluta rachunku: PLN" );
        content.add( "Data rozliczenia odsetek rachunku: 31.12.2019" );
        content.add( "Stopa %: 0,00" );
        content.add( "Limit kredytu w rachunku bieżącym/" );
        content.add( "dopuszczalnego salda debetowego:" );
        content.add( "Stopa %: 10,00" );
        content.add( "Obroty MA" );
        content.add( "Obroty WN" );
        content.add( "Saldo poprzednie" );
        content.add( "99 999,99" );
        content.add( "-999 999,99" );
        content.add( "199 999,99" );
        content.add( "Data operacji Identyfikator operacji TYP OPERACJI Kwota operacji Saldo" );
        content.add( "Data waluty Opis operacji" );
        return this;
    }


    public PKOBankStmtPdfMock addEntry( PKOBankStmtEntryPdfMock entry )
    {
        content.add( entry.getFirstLine() );
        content.add( entry.getSecondLine() );
        if( !entry.getThirdLine().isEmpty() )
            content.add( entry.getThirdLine() );
        return this;
    }


    public PKOBankStmtPdfMock addPageSplitter()
    {
        addLine( PKO_BANK_STMT_PAGE_BALANCE_SUMMARY_TXT + " 9 999,99" );
        addLine( "strona 1/2" );
        addLine( "WYCIĄG za okres 26.11.2019 - 23.12.2019" );
        addLine( "Nr: 12/2019 Data: 23.12.2019" );
        addLine( "Saldo z przeniesienia 9 999,99" );
        return this;
    }


    public PKOBankStmtPdfMock summarize()
    {
        addLine( PKO_BANK_STMT_BALANCE_SUMMARY_TXT + " 9 999,99" );
        return this;
    }


    public String getContent()
    {

        return content.toString();
    }


    private PKOBankStmtPdfMock addLine( String line )
    {
        content.add( line );
        return this;
    }
}
