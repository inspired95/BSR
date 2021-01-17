package com.catchex.repositorycreator.typeresolving;

import com.catchex.models.OperationType;

import static com.catchex.util.Constants.LOSS;
import static com.catchex.util.Constants.PROFIT;


public class NotApplicableTypeResolver
    implements OperationTypeResolver
{
    @Override
    public OperationType resolve( String typeOperationDesc )
    {
        switch( typeOperationDesc )
        {
            case PROFIT:
                return OperationType.PROFIT;
            case LOSS:
                return OperationType.LOSS;
            default:
                return OperationType.NOT_RESOLVED;
        }
    }
}
