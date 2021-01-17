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


    public PKOBankStmtPdfMock addEntry( PKOBankStmtEntryPdfMock entry )
    {
        content.add( entry.getFirstLine() );
        content.add( entry.getSecondLine() );
        if( !entry.getThirdLine().isEmpty() )
            content.add( entry.getThirdLine() );
        return this;
    }


    public PKOBankStmtPdfMock summarizePage()
    {
        addLine( PKO_BANK_STMT_PAGE_BALANCE_SUMMARY_TXT );
        return this;
    }


    public PKOBankStmtPdfMock summarize()
    {
        addLine( PKO_BANK_STMT_BALANCE_SUMMARY_TXT );
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
