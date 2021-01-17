package com.catchex.repositorycreator.typeresolving;

import com.catchex.models.OperationType;


public interface OperationTypeResolver
{
    OperationType resolve( String typeOperationDesc );
}
