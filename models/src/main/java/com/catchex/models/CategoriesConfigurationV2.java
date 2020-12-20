package com.catchex.models;

import java.io.Serializable;
import java.util.Optional;
import java.util.SortedSet;
import java.util.TreeSet;


public class CategoriesConfigurationV2
    implements Serializable, Cloneable
{
    private static final long serialVersionUID = -7588980448693010397L;

    private SortedSet<CategoryV2> categories;


    public CategoriesConfigurationV2()
    {
        this.categories = new TreeSet<>();
    }


    public CategoriesConfigurationV2( SortedSet<CategoryV2> categories )
    {
        this.categories = categories;
    }


    public Optional<CategoryV2> addCategory( String newCategoryName )
    {
        CategoryV2 newCategory = new CategoryV2( newCategoryName, new TreeSet<>() );
        this.categories.add( newCategory );
        return Optional.of( newCategory );
    }


    public SortedSet<CategoryV2> getCategories()
    {
        return categories;
    }


    public void setCategories( SortedSet<CategoryV2> categories )
    {
        this.categories = new TreeSet<>( categories );
    }


    @Override
    public Object clone()
    {
        return new CategoriesConfigurationV2( new TreeSet<>( categories ) );
    }
}
