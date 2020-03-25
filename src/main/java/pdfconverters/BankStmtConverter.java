package pdfconverters;

import model.BankStmtEntry;

import java.util.List;


public interface BankStmtConverter
{
    List<BankStmtEntry> convert( String statement );
}
