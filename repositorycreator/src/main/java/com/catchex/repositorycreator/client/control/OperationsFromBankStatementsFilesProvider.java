package com.catchex.repositorycreator.client.control;

import com.catchex.io.reader.PDFReader;
import com.catchex.models.Operation;
import com.catchex.models.RawOperation;
import com.catchex.repositorycreator.bankstatementsconverter.BankStmtConverter;
import com.catchex.repositorycreator.bankstatementsconverter.BankStmtConverterFactory;
import com.catchex.repositorycreator.typeresolving.OperationTypeResolver;
import com.catchex.repositorycreator.typeresolving.OperationTypeResolverFactory;

import java.io.File;
import java.util.*;


public class OperationsFromBankStatementsFilesProvider
    implements Provider<Operation>
{
    private final String bankName;
    private final List<File> bankStatementFiles;

    private Optional<OperationTypeResolver> typeResolver;


    public OperationsFromBankStatementsFilesProvider(
        String bankName, List<File> bankStatementsFiles )
    {
        this.bankName = bankName;
        this.bankStatementFiles = bankStatementsFiles;
        typeResolver = OperationTypeResolverFactory.getInstance().match( bankName );
    }


    @Override
    public Set<Operation> get()
    {
        Set<Operation> operations = new HashSet<>();
        typeResolver.ifPresent( resolver -> {
            for( File bankStatementFile : bankStatementFiles )
            {
                PDFReader.read( bankStatementFile.getAbsolutePath() ).ifPresent(
                    bankStatementContent -> createRawOperationsList( bankStatementFile.getName(),
                        bankStatementContent ).forEach( rawOperation -> operations.add(
                        new Operation( rawOperation,
                            resolver.resolve( rawOperation.getType() ) ) ) ) );
            }
        } );

        return operations;
    }


    private List<RawOperation> createRawOperationsList(
        String bankStatementFileName, String bankStatementFileContent )
    {
        List<RawOperation> rawOperations = Collections.emptyList();

        Optional<BankStmtConverter> converter = getBankStatementConverter( bankName );

        if( converter.isPresent() )
        {
            rawOperations =
                converter.get().convert( bankStatementFileName, bankStatementFileContent );
        }
        return rawOperations;
    }


    private Optional<BankStmtConverter> getBankStatementConverter( String bankChoiceDialog )
    {
        return BankStmtConverterFactory.match( bankChoiceDialog );
    }

}
