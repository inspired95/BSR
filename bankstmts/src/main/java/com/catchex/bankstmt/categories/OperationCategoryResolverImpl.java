package com.catchex.bankstmt.categories;

import com.catchex.models.Category;
import com.catchex.models.Configuration;

import static com.catchex.models.Category.OTHER_CATEGORY;
import static com.catchex.util.Log.LOGGER;


public class OperationCategoryResolverImpl
    implements OperationCategoryResolver
{

    public OperationCategoryResolverImpl()
    {
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
        for( Category category : Configuration.getInstance().getCategoriesConfiguration()
            .getCategories() )
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
