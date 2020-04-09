package com.catchex.bankstmt.operationtype;

import com.catchex.models.OperationType;


public interface OperationTypeResolver
{
    OperationType resolve( String typeOperationDesc );
}
