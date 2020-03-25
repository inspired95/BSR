package pdfconverters;

import operationtype.PKOOperationTypeResolver;

import static utils.Constants.PKO;


public class BankStmtConverterFactory
{

    public BankStmtConverter match( String chosenBank )
    {
        if( PKO.equals( chosenBank ) )
        {
            return new PKOBankStmtConverter( new PKOOperationTypeResolver() );
        }
        throw new RuntimeException();
    }

}
