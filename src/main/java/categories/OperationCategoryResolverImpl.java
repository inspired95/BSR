package categories;

import app.Configuration;
import model.CategoriesConfiguration;
import model.Category;

import java.util.Arrays;

import static model.Category.*;


public class OperationCategoryResolverImpl
    implements OperationCategoryResolver
{
    Category[] categories = Configuration.getCategoriesConfiguration().getCategories();


    @Override
    public Category resolve( String operationDescription )
    {
        String descriptionCaseLowered = operationDescription.toLowerCase();
        for( Category category : categories )
        {
            if( Arrays.stream(category.getKeywords()).parallel().anyMatch(
                keyword -> descriptionCaseLowered.contains( keyword.toLowerCase() ) ) )
            return category;
        }
        return OTHER_CATEGORY;
    }
}
