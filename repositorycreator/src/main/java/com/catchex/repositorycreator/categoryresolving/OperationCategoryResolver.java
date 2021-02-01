package com.catchex.repositorycreator.categoryresolving;

import com.catchex.models.Category;
import com.catchex.models.OperationType;


public interface OperationCategoryResolver
{
    Category resolve( String operationDescription );

    Category resolve( String operationDescription, OperationType type );
}
