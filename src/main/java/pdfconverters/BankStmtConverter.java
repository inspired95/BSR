package pdfconverters;

import model.RawOperation;

import java.util.List;


public interface BankStmtConverter
{
    List<RawOperation> convert( String statement );
}
