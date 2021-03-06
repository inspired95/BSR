package com.catchex.models;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Optional;
import java.util.SortedSet;
import java.util.TreeSet;


public class CategoriesConfiguration
    implements Serializable, Cloneable
{
    private static final Logger logger = LoggerFactory.getLogger( CategoriesConfiguration.class );

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
        logger.info( "Adding new category {}", newCategoryName );
        String validNewCategoryName = newCategoryName.toUpperCase();
        Optional<Category> foundCategory = getCategoryOfName( validNewCategoryName );
        if( foundCategory.isPresent() )
        {
            return Optional.empty();
        }
        Category newCategory = new Category( validNewCategoryName, new TreeSet<>() );
        this.categories.add( newCategory );
        return Optional.of( newCategory );
    }


    public Optional<Category> getCategoryOfName( String categoryName )
    {
        return this.categories.stream()
            .filter( category -> category.getCategoryName().equalsIgnoreCase( categoryName ) )
            .findAny();
    }


    public Optional<Keyword> isKeywordAlreadyExists( Keyword keywordToCheck )
    {
        return this.categories.stream().flatMap( category -> category.getKeywords().stream() )
            .filter( keyword -> keyword.equals( keywordToCheck ) ).findAny();
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
