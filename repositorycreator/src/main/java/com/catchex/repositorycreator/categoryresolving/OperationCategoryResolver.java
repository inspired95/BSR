package com.catchex.repositorycreator.categoryresolving;

import com.catchex.models.Category;


public interface OperationCategoryResolver
{
    Category resolve( String operationDescription );
}
