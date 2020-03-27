package transformators;

import app.Configuration;
import categories.OperationCategoryResolver;
import categories.OperationCategoryResolverImpl;
import model.Category;
import model.Operation;
import model.RawOperation;
import operationtype.OperationType;
import operationtype.OperationTypeResolver;

import java.util.ArrayList;
import java.util.List;


public class OperationTransformer
{
    private OperationTypeResolver operationTypeResolver;
    private OperationCategoryResolver operationCategoryResolver;


    public OperationTransformer( OperationTypeResolver operationTypeResolver, Category[] categories )
    {
        this.operationTypeResolver = operationTypeResolver;
        this.operationCategoryResolver = new OperationCategoryResolverImpl( categories );
    }


    public List<Operation> transform( List<RawOperation> rawOperations )
    {
        List<Operation> operations = new ArrayList<>();
        for( RawOperation rawOperation : rawOperations )
        {
            operations.add(transform( rawOperation ));
        }

        return operations;
    }


    private Operation transform( RawOperation rawOperation )
    {
        OperationType operationType = operationTypeResolver.resolve( rawOperation.getType() );
        Category category = operationCategoryResolver.resolve( rawOperation.getDesc() );

        return new Operation( rawOperation.getID(), rawOperation.getDate(), operationType,
            rawOperation.getAmount(), category );
    }
}
