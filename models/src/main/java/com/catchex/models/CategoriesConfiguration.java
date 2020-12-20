package com.catchex.models;

import java.io.Serializable;
import java.util.Optional;
import java.util.SortedSet;
import java.util.TreeSet;


public class CategoriesConfiguration
    implements Serializable, Cloneable
{
    private static final long serialVersionUID = -7588980448693010397L;

    private SortedSet<Category> categories;


    public CategoriesConfiguration()
    {
        this.categories = new TreeSet<>();
    }


    public CategoriesConfiguration( SortedSet<Category> categories )
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
        return new CategoriesConfiguration( new TreeSet<>( categories ) );
    }
}
