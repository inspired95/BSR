package categories;

import model.Category;

import java.util.Arrays;
import java.util.logging.Logger;

import static java.util.logging.Logger.GLOBAL_LOGGER_NAME;
import static java.util.logging.Logger.getLogger;
import static model.Category.OTHER_CATEGORY;


public class OperationCategoryResolverImpl
    implements OperationCategoryResolver
{
    private final static Logger LOGGER = getLogger( GLOBAL_LOGGER_NAME );

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
