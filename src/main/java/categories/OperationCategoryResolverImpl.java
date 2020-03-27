package categories;

import model.Category;

import java.util.Arrays;

import static model.Category.OTHER_CATEGORY;


public class OperationCategoryResolverImpl
    implements OperationCategoryResolver
{
    Category[] categories;

    public OperationCategoryResolverImpl( Category[] categories ){
        this.categories = categories;
    }

    @Override
    public Category resolve( String operationDescription )
    {
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
