package com.catchex.repositorycreator.typeresolving;

import com.catchex.models.OperationType;


public class NotApplicableTypeResolver
    implements OperationTypeResolver
{
    @Override
    public OperationType resolve( String typeOperationDesc )
    {
        return OperationType.NOT_APPLICABLE;
    }
}
