package pdfconverters;

import exceptions.BankStmtConverterNotFoundException;

import static utils.Constants.PKO;


public class BankStmtConverterFactory
{
    public BankStmtConverter match( String chosenBank )
    {
        if( PKO.equals( chosenBank ) )
        {
            return new PKOBankStmtConverter();
        }
        throw new BankStmtConverterNotFoundException();
    }

}
