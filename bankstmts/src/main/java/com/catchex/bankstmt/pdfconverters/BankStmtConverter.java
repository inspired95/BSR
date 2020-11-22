package com.catchex.bankstmt.pdfconverters;

import com.catchex.models.RawOperation;

import java.util.List;


public interface BankStmtConverter
{
    List<RawOperation> convert( String name, String statement );
}
