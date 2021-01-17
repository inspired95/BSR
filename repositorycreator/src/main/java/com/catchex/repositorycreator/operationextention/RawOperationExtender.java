package com.catchex.repositorycreator.operationextention;

import com.catchex.logging.Log;
import com.catchex.models.Category;
import com.catchex.models.Operation;
import com.catchex.models.OperationType;
import com.catchex.models.RawOperation;
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
    private OperationCategoryResolver operationCategoryResolver;


    public RawOperationExtender(
        OperationTypeResolver operationTypeResolver,
        OperationCategoryResolver operationCategoryResolver )
    {
        this.operationTypeResolver = operationTypeResolver;
        this.operationCategoryResolver = operationCategoryResolver;
    }


    public Set<Operation> extend( List<RawOperation> rawOperations )
    {
        if( rawOperations != null )
        {
            Set<Operation> operations = new HashSet<>();
            for( RawOperation rawOperation : rawOperations )
            {
                operations.add( extend( rawOperation ) );
            }
            return operations;
        }
        Log.LOGGER.warning( "Cannot transform null" );
        return of();
    }


    public Operation extend( RawOperation rawOperation )
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
                category = operationCategoryResolver.resolve( rawOperation.getDesc() );
            }
            if( category.equals( Category.OTHER_CATEGORY ) || operationType.equals( NOT_RESOLVED ) )
            {
                Log.LOGGER.warning( "Cannot resolve category of\n" + rawOperation.getDesc() );
            }

            return new Operation( rawOperation, operationType, category );
        }
        Log.LOGGER.warning( "Cannot transform null" );
        return null;
    }
}
