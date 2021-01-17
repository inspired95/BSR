package com.catchex.models;

import java.io.Serializable;
import java.util.Optional;
import java.util.SortedSet;
import java.util.TreeSet;


public class CategoriesConfigurationV2
    implements Serializable, Cloneable
{
    private static final long serialVersionUID = -7588980448693010397L;

    private SortedSet<Category> categories;


    public CategoriesConfigurationV2()
    {
        this.categories = new TreeSet<>();
    }


    public CategoriesConfigurationV2( SortedSet<Category> categories )
    {
        this.categories = categories;
    }


    public Optional<Category> addCategory( String newCategoryName )
    {
        Category newCategory = new Category( newCategoryName, new TreeSet<>() );
        this.categories.add( newCategory );
        return Optional.of( newCategory );
    }


    public SortedSet<Category> getCategories()
    {
        return categories;
    }


    public void setCategories( SortedSet<Category> categories )
    {
        this.categories = new TreeSet<>( categories );
    }


    @Override
    public Object clone()
    {
        return new CategoriesConfigurationV2( new TreeSet<>( categories ) );
    }
}
