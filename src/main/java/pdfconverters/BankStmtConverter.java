package pdfconverters;

import model.Operation;

import java.util.List;


public interface BankStmtConverter
{
    List<Operation> convert( String statement );
}
