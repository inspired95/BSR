package com.catchex.bankstmt.categories;

import com.catchex.models.Category;

import java.util.Arrays;

import static com.catchex.models.Category.OTHER_CATEGORY;
import static com.catchex.util.Log.LOGGER;


public class OperationCategoryResolverImpl
    implements OperationCategoryResolver
{
    private Category[] categories;


    public OperationCategoryResolverImpl( Category[] categories )
    {
        this.categories = categories;
    }


    @Override
    public Category resolve( String operationDescription )
    {
        if( operationDescription == null || operationDescription.isEmpty() )
        {
            LOGGER.info( "Operation description is null or empty" );
            return OTHER_CATEGORY;
        }
        String descriptionCaseLowered = operationDescription.toLowerCase();
        for( Category category : categories )
        {
            if( Arrays.stream( category.getKeywords() ).parallel()
                .anyMatch( keyword -> descriptionCaseLowered.contains( keyword.toLowerCase() ) ) )
                return category;
        }
        return OTHER_CATEGORY;
    }
}
