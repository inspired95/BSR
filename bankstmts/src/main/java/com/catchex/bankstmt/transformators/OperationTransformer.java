package com.catchex.bankstmt.transformators;

import com.catchex.bankstmt.categories.OperationCategoryResolver;
import com.catchex.models.Category;
import com.catchex.models.Operation;
import com.catchex.models.OperationType;
import com.catchex.models.RawOperation;
import com.catchex.bankstmt.operationtype.OperationTypeResolver;

import java.util.ArrayList;
import java.util.List;

import static com.catchex.models.OperationType.*;
import static java.util.List.of;
import static com.catchex.models.Category.OTHER_CATEGORY;
import static com.catchex.util.Log.LOGGER;


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


    public List<Operation> transform( List<RawOperation> rawOperations )
    {
        if( rawOperations != null )
        {
            List<Operation> operations = new ArrayList<>();
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
        OperationType operationType = operationTypeResolver.resolve( rawOperation.getType().getValue() );
        Category category;
        if (operationType.equals(CASH_WITHDRAWAL)){
            category = Category.CASH_WITHDRAWAL;
        }else{
            category = operationCategoryResolver.resolve( rawOperation.getDesc().getValue() );
        }
        if( category.equals( OTHER_CATEGORY ) ||
            operationType.equals( NOT_RESOLVED ) )
        {
            LOGGER.warning( "Cannot resolve category of\n" + rawOperation.getDesc() );
        }

        return new Operation( rawOperation, operationType, category );
    }
}
