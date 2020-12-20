package com.catchex.bankstmt.categories;

import com.catchex.models.CategoryV2;

import java.util.SortedSet;

import static com.catchex.models.CategoryV2.OTHER_CATEGORY;
import static com.catchex.util.Log.LOGGER;


public class OperationCategoryResolverImpl
    implements OperationCategoryResolver
{
    private SortedSet<CategoryV2> categories;


    public OperationCategoryResolverImpl( SortedSet<CategoryV2> categories )
    {
        this.categories = categories;
    }


    @Override
    public CategoryV2 resolve( String operationDescription )
    {
        if( operationDescription == null || operationDescription.isEmpty() )
        {
            LOGGER.info( "Operation description is null or empty" );
            return OTHER_CATEGORY;
        }
        String descriptionCaseLowered = operationDescription.toLowerCase();
        for( CategoryV2 category : categories )
        {
            if( category.getKeywords().stream().anyMatch(
                keyword -> descriptionCaseLowered.contains( keyword.getValue().toLowerCase() ) ) )
                return category;
        }
        LOGGER.warning( "Operation category of based on description cannot be resolved : " +
            operationDescription );
        return OTHER_CATEGORY;
    }
}
