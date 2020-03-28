package transformators;

import categories.OperationCategoryResolver;
import model.Category;
import model.Operation;
import model.RawOperation;
import operationtype.OperationType;
import operationtype.OperationTypeResolver;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static java.util.List.of;
import static java.util.logging.Logger.GLOBAL_LOGGER_NAME;
import static java.util.logging.Logger.getLogger;


public class OperationTransformer
{
    private final static Logger LOGGER = getLogger( GLOBAL_LOGGER_NAME );

    private OperationTypeResolver operationTypeResolver;
    private OperationCategoryResolver operationCategoryResolver;


    public OperationTransformer( OperationTypeResolver operationTypeResolver,
                                 OperationCategoryResolver operationCategoryResolver )
    {
        this.operationTypeResolver = operationTypeResolver;
        this.operationCategoryResolver = operationCategoryResolver;
    }


    public List<Operation> transform( List<RawOperation> rawOperations )
    {
        if( rawOperations != null){
            List<Operation> operations = new ArrayList<>();
            for( RawOperation rawOperation : rawOperations )
            {
                operations.add(transform( rawOperation ));
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

        return new Operation( rawOperation.getID(), rawOperation.getDate(), operationType,
            rawOperation.getAmount(), category );
    }
}
