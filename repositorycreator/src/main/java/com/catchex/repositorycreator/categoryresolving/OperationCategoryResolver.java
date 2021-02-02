package com.catchex.repositorycreator.categoryresolving;

import com.catchex.models.Category;
import com.catchex.models.Configuration;
import com.catchex.models.OperationType;

import static com.catchex.logging.Log.LOGGER;


public class OperationCategoryResolver
{
    public static Category resolve( String operationDescription )
    {
        if( !isDescriptionValid( operationDescription ) )
        {
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


    public static Category resolve( String operationDescription, OperationType type )
    {
        if( !isTypeValid( type ) )
        {
            return Category.OTHER_CATEGORY;
        }
        if( OperationType.CASH_WITHDRAWAL.equals( type ) )
        {
            return Category.CASH_WITHDRAWAL;
        }
        return resolve( operationDescription );
    }


    private static boolean isDescriptionValid( String operationDescription )
    {
        if( operationDescription == null || operationDescription.isEmpty() )
        {
            LOGGER.info( "Operation description is null or empty" );
            return false;
        }
        return true;
    }


    private static boolean isTypeValid( OperationType type )
    {
        if( type == null )
        {
            LOGGER.info( "Operation type is null" );
            return false;
        }
        return true;
    }
}
