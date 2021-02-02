package com.catchex.repositorycreator.operationextention;

import com.catchex.logging.Log;
import com.catchex.models.*;
import com.catchex.repositorycreator.categoryresolving.OperationCategoryResolver;
import com.catchex.repositorycreator.typeresolving.OperationTypeResolver;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.catchex.models.OperationType.NOT_RESOLVED;
import static java.util.Set.of;


public class RawOperationExtender
{
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
        Log.LOGGER.warning( "Cannot transform null" );
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
                Log.LOGGER.warning( "Cannot resolve category of\n" + rawOperation.getDesc() );
            }

            return new CurrentOperation( new Operation( rawOperation, operationType ), category );
        }
        Log.LOGGER.warning( "Cannot transform null" );
        return null;
    }
}
