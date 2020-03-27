package categories;

import model.Category;


public interface OperationCategoryResolver
{
    Category resolve( String operationDescription );
}
