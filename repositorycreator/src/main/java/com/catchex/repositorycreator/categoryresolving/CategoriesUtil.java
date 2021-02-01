package com.catchex.repositorycreator.categoryresolving;

import com.catchex.models.CurrentRepository;


public class CategoriesUtil
    implements ICategoriesUtil
{
    private static CategoriesUtil INSTANCE;

    private OperationCategoryResolver categoryResolver;


    private CategoriesUtil()
    {
        this.categoryResolver = OperationCategoryResolverImpl.getInstance();
    }


    public static CategoriesUtil getInstance()
    {
        if( INSTANCE == null )
        {
            INSTANCE = new CategoriesUtil();
        }
        return INSTANCE;
    }


    @Override
    public void updateCategories( CurrentRepository repository )
    {
        repository.getOperations().forEach( operation -> operation.setCategory(
            categoryResolver.resolve( operation.getOperation().getRawOperation().getDesc() ) ) );
    }
}
