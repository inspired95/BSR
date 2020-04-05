package transformators;

import categories.OperationCategoryResolver;
import model.Category;
import model.Operation;
import model.RawOperation;
import operationtype.OperationType;
import operationtype.OperationTypeResolver;

import java.util.ArrayList;
import java.util.List;

import static app.Log.LOGGER;
import static java.util.List.of;
import static model.Category.OTHER_CATEGORY;
import static operationtype.OperationType.INCOME_TRANSFER;


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
        OperationType operationType = operationTypeResolver.resolve( rawOperation.getType() );
        Category category = operationCategoryResolver.resolve( rawOperation.getDesc() );
        if( category.getCategoryName().equals( OTHER_CATEGORY.getCategoryName() ) &&
            operationType.equals( INCOME_TRANSFER ) )
        {
            LOGGER.info( "Cannot resolve category of\n" + rawOperation.getDesc() );
        }

        return new Operation( rawOperation, operationType, category );
    }
}
