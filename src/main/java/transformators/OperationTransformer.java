package transformators;

import categories.OperationCategoryResolver;
import categories.OperationCategoryResolverImpl;
import model.Category;
import model.Operation;
import model.RawOperation;
import operationtype.OperationType;
import operationtype.OperationTypeResolver;


public class OperationTransformer
{
    private OperationTypeResolver operationTypeResolver;
    private OperationCategoryResolver operationCategoryResolver;

    public OperationTransformer( OperationTypeResolver operationTypeResolver ){
        this.operationTypeResolver = operationTypeResolver;
        this.operationCategoryResolver = new OperationCategoryResolverImpl();
    }

    public Operation transform( RawOperation rawOperation ){

        OperationType operationType = operationTypeResolver.resolve( rawOperation.getType() );
        Category category = operationCategoryResolver.resolve( rawOperation.getDesc() );

        return new Operation( rawOperation.getID(), rawOperation.getDate(), operationType,
            rawOperation.getAmount(), category );
    }
}
