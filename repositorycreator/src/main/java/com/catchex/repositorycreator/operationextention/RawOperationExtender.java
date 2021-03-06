package com.catchex.repositorycreator.operationextention;

import com.catchex.io.reader.ConfigurationReader;
import com.catchex.models.*;
import com.catchex.repositorycreator.categoryresolving.OperationCategoryResolver;
import com.catchex.repositorycreator.typeresolving.OperationTypeResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.catchex.models.OperationType.NOT_RESOLVED;
import static java.util.Set.of;


public class RawOperationExtender
{
    private static final Logger logger = LoggerFactory.getLogger( ConfigurationReader.class );

    private OperationTypeResolver operationTypeResolver;


    public RawOperationExtender(
        OperationTypeResolver operationTypeResolver )
    {
        this.operationTypeResolver = operationTypeResolver;
    }


    public Set<CurrentOperation> extend( List<RawOperation> rawOperations )
    {
        if( rawOperations != null )
        {
            Set<CurrentOperation> operations = new HashSet<>();
            for( RawOperation rawOperation : rawOperations )
            {
                operations.add( extend( rawOperation ) );
            }
            return operations;
        }
        logger.warn( "Cannot transform null" );
        return of();
    }


    public CurrentOperation extend( RawOperation rawOperation )
    {
        if( rawOperation != null )
        {
            OperationType operationType = operationTypeResolver.resolve( rawOperation.getType() );
            Category category;
            if( operationType.equals( OperationType.CASH_WITHDRAWAL ) )
            {
                category = Category.CASH_WITHDRAWAL;
            }
            else
            {
                category =
                    OperationCategoryResolver.resolve( rawOperation.getDesc(), operationType );
            }
            if( category.equals( Category.OTHER_CATEGORY ) || operationType.equals( NOT_RESOLVED ) )
            {
                logger.warn( "Cannot resolve category of\n" + rawOperation.getDesc() );
            }

            return new CurrentOperation( new Operation( rawOperation, operationType ), category );
        }
        logger.warn( "Cannot transform null" );
        return null;
    }
}
