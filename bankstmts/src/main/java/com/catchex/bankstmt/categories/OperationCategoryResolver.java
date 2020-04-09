package com.catchex.bankstmt.categories;

import com.catchex.models.Category;


public interface OperationCategoryResolver
{
    Category resolve( String operationDescription );
}
