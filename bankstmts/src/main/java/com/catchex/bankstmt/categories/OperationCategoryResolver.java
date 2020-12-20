package com.catchex.bankstmt.categories;

import com.catchex.models.CategoryV2;


public interface OperationCategoryResolver
{
    CategoryV2 resolve( String operationDescription );
}
