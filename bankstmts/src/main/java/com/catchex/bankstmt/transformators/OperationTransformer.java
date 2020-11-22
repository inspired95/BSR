package com.catchex.bankstmt.transformators;

import com.catchex.bankstmt.categories.OperationCategoryResolver;
import com.catchex.bankstmt.operationtype.OperationTypeResolver;
import com.catchex.models.Category;
import com.catchex.models.Operation;
import com.catchex.models.OperationType;
import com.catchex.models.RawOperation;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.catchex.models.Category.OTHER_CATEGORY;
import static com.catchex.models.OperationType.CASH_WITHDRAWAL;
import static com.catchex.models.OperationType.NOT_RESOLVED;
import static com.catchex.util.Log.LOGGER;
import static java.util.Set.of;


public class OperationTransformer
{
    private OperationTypeResolver operationTypeResolver;
    private OperationCategoryResolver operationCategoryResolver;


    public OperationTransformer(
        OperationTypeResolver operationTypeResolver,
        OperationCategoryResolver operationCategoryResolver )
    {
        this.operationTypeResolver = operationTypeResolver;
        this.operationCategoryResolver = operationCategoryResolver;
    }


    public Set<Operation> transform( List<RawOperation> rawOperations )
    {
        if( rawOperations != null )
        {
            Set<Operation> operations = new HashSet<>();
            for( RawOperation rawOperation : rawOperations )
            {
                operations.add( transform( rawOperation ) );
            }
            return operations;
        }
        LOGGER.warning( "Cannot transform null" );
        return of();
    }


    private Operation transform( RawOperation rawOperation )
    {
        OperationType operationType = operationTypeResolver.resolve( rawOperation.getType() );
        Category category;
        if( operationType.equals( CASH_WITHDRAWAL ) )
        {
            category = Category.CASH_WITHDRAWAL;
        }
        else
        {
            category = operationCategoryResolver.resolve( rawOperation.getDesc() );
        }
        if( category.equals( OTHER_CATEGORY ) || operationType.equals( NOT_RESOLVED ) )
        {
            LOGGER.warning( "Cannot resolve category of\n" + rawOperation.getDesc() );
        }

        return new Operation( rawOperation, operationType, category );
    }
}
