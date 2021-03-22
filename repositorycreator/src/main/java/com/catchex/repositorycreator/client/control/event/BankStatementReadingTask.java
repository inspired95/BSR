package com.catchex.repositorycreator.client.control.event;

import com.catchex.models.CurrentOperation;
import com.catchex.models.Operation;
import com.catchex.repositorycreator.client.control.CurrentOperationsUtil;
import com.catchex.repositorycreator.client.control.OperationsFromBankStatementsFilesProvider;
import com.catchex.repositorycreator.client.model.CurrentRepositoryUtil;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;


class BankStatementReadingTask
    implements Runnable
{
    private static final Logger logger = LoggerFactory.getLogger( BankStatementReadingTask.class );

    private final ExecutorService executor = Executors.newFixedThreadPool( 5 );

    private final String bankName;
    private final List<File> selectedBankStatementsFiles;

    List<CompletableFuture<Set<CurrentOperation>>> futures = new ArrayList<>();


    public BankStatementReadingTask(
        String bankName, List<File> selectedBankStatementsFiles )
    {
        this.bankName = bankName;
        this.selectedBankStatementsFiles = selectedBankStatementsFiles;
    }


    @Override
    public void run()
    {
        prepareReadingFutures();

        CompletableFuture<List<Set<CurrentOperation>>> listCompletableFuture = waitForAllAndJoin();

        Set<CurrentOperation> currentRepository = sumResults( listCompletableFuture );

        new CurrentRepositoryUtil().addCurrentOperations( currentRepository );
    }


    private void prepareReadingFutures()
    {
        for( File selectedBankStatementsFile : selectedBankStatementsFiles )
        {
            CompletableFuture<Set<CurrentOperation>> handle =
                getBankStatementHandlingFuture( selectedBankStatementsFile )
                    .handle( ( result, throwable ) -> {
                        if( throwable != null )
                        {
                            logger.error(
                                "Exception during reading file {} {}",
                                selectedBankStatementsFile.getAbsolutePath(), throwable );
                            return Collections.emptySet();
                        }
                        return result;
                    } );
            futures.add( handle );
        }
    }


    private CompletableFuture<List<Set<CurrentOperation>>> waitForAllAndJoin()
    {
        CompletableFuture<Void> allRead =
            CompletableFuture.allOf( futures.toArray( new CompletableFuture[futures.size()] ) );

        return allRead.thenApply( v -> futures.stream().
            map( CompletableFuture::join ).
            collect( Collectors.<Set<CurrentOperation>>toList() ) );
    }


    private Set<CurrentOperation> sumResults(
        CompletableFuture<List<Set<CurrentOperation>>> listCompletableFuture )
    {
        Set<CurrentOperation> currentRepository = new HashSet<>();
        try
        {
            listCompletableFuture.get().forEach( currentRepository::addAll );
        }
        catch( InterruptedException e )
        {
            logger.error( ExceptionUtils.getStackTrace( e ) );
            Thread.currentThread().interrupt();
        }
        catch( ExecutionException e )
        {
            logger.error( ExceptionUtils.getStackTrace( e ) );
        }
        return currentRepository;
    }


    private CompletableFuture<Set<CurrentOperation>> getBankStatementHandlingFuture( File file )
    {
        return CompletableFuture.supplyAsync( () -> {
            Set<Operation> operations =
                new OperationsFromBankStatementsFilesProvider( bankName, file ).get();

            return new CurrentOperationsUtil().mapToCurrentOperations( operations );
        }, executor );
    }
}
