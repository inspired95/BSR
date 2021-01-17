package com.catchex.repositorycreator.categoryresolving;

import com.catchex.models.Repository;


public class CategoriesUtil
    implements ICategoriesUtil
{
    private static CategoriesUtil INSTANCE;

    private OperationCategoryResolver categoryResolver;


    private CategoriesUtil()
    {
        this.categoryResolver = new OperationCategoryResolverImpl();
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
    public void updateCategories( Repository repository )
    {
        repository.getOperations().forEach( operation -> operation
            .setCategory( categoryResolver.resolve( operation.getRawOperation().getDesc() ) ) );
    }
}
