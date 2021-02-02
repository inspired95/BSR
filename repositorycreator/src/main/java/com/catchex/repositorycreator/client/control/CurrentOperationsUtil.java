package com.catchex.repositorycreator.client.control;

import com.catchex.models.CurrentOperation;
import com.catchex.models.Operation;
import com.catchex.repositorycreator.categoryresolving.OperationCategoryResolver;

import java.util.Set;
import java.util.stream.Collectors;


public class CurrentOperationsUtil
{

    public void updateDescription( CurrentOperation currentOperation, String newDescription )
    {
        currentOperation.setDescription( newDescription );
        recalculateCategory( currentOperation );
    }


    public Set<CurrentOperation> mapToCurrentOperations( Set<Operation> operations )
    {
        return operations.stream().map( operation -> new CurrentOperation( operation,
            OperationCategoryResolver
                .resolve( operation.getRawOperation().getDesc(), operation.getType() ) ) )
            .collect( Collectors.toSet() );
    }


    public CurrentOperation mapToCurrentOperation( Operation operation )
    {
        return new CurrentOperation( operation, OperationCategoryResolver
            .resolve( operation.getRawOperation().getDesc(), operation.getType() ) );
    }


    public void recalculateCategory( CurrentOperation currentOperation )
    {
        currentOperation.setCategory( OperationCategoryResolver
            .resolve( currentOperation.getOperation().getRawOperation().getDesc() ) );
    }

}
