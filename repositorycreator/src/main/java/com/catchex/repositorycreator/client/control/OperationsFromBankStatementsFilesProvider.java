package com.catchex.repositorycreator.client.control;

import com.catchex.io.reader.PDFReader;
import com.catchex.models.Operation;
import com.catchex.models.RawOperation;
import com.catchex.repositorycreator.bankstatementsconverter.BankStmtConverter;
import com.catchex.repositorycreator.bankstatementsconverter.BankStmtConverterFactory;
import com.catchex.repositorycreator.typeresolving.OperationTypeResolver;
import com.catchex.repositorycreator.typeresolving.OperationTypeResolverFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.*;


public class OperationsFromBankStatementsFilesProvider
    implements Provider<Operation>
{
    private static final Logger logger =
        LoggerFactory.getLogger( OperationsFromBankStatementsFilesProvider.class );

    private final String bankName;
    private final File bankStatementFile;

    private final OperationTypeResolver typeResolver;


    public OperationsFromBankStatementsFilesProvider(
        String bankName, File bankStatementsFile )
    {
        this.bankName = bankName;
        this.bankStatementFile = bankStatementsFile;
        typeResolver = OperationTypeResolverFactory.getInstance().match( bankName );
    }


    @Override
    public Set<Operation> get()
    {
        Set<Operation> operations = new HashSet<>();
        PDFReader.read( bankStatementFile.getAbsolutePath() ).ifPresentOrElse(
            bankStatementContent -> handleBankStatementFileContent( operations, bankStatementFile,
                bankStatementContent ), () -> logger
                .warn( "File {} could not be read", bankStatementFile.getAbsolutePath() ) );
        logger.info( "size: {}", operations.size() );
        return operations;
    }


    private void handleBankStatementFileContent(
        Set<Operation> operations, File bankStatementFile, String bankStatementContent )
    {
        createRawOperationsList( bankStatementFile.getName(), bankStatementContent ).forEach(
            rawOperation -> operations.add(
                new Operation( rawOperation, typeResolver.resolve( rawOperation.getType() ) ) ) );
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
