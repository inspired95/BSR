package com.catchex.repositorycreator.categoryresolving;

import com.catchex.models.Category;
import com.catchex.models.Configuration;
import com.catchex.models.OperationType;

import static com.catchex.logging.Log.LOGGER;


public class OperationCategoryResolverImpl
    implements OperationCategoryResolver
{

    private static OperationCategoryResolverImpl INSTANCE;


    private OperationCategoryResolverImpl()
    {
    }


    public static OperationCategoryResolverImpl getInstance()
    {
        if( INSTANCE == null )
        {
            INSTANCE = new OperationCategoryResolverImpl();
        }
        return INSTANCE;
    }


    @Override
    public Category resolve( String operationDescription, OperationType type )
    {
        if( OperationType.CASH_WITHDRAWAL.equals( type ) )
        {
            return Category.CASH_WITHDRAWAL;
        }
        return resolve( operationDescription );
    }


    public Category resolve( String operationDescription )
    {
        if( operationDescription == null || operationDescription.isEmpty() )
        {
            LOGGER.info( "Operation description is null or empty" );
            return Category.OTHER_CATEGORY;
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
        return Category.OTHER_CATEGORY;
    }
}
