package com.catchex.models;

import java.io.Serializable;
import java.util.Objects;
import java.util.SortedSet;
import java.util.TreeSet;


public class Category
    implements Comparable<Category>, Serializable, Cloneable
{
    private static final long serialVersionUID = -7588980448693010398L;

    public static Category OTHER_CATEGORY = new Category( "OTHER", new TreeSet<>() );
    public static Category CASH_WITHDRAWAL = new Category( "CASH WITHDRAWAL", new TreeSet<>() );
    private String categoryName;
    private SortedSet<Keyword> keywords;


    public Category( String categoryName, SortedSet<Keyword> keywords )
    {
        this.categoryName = categoryName.toUpperCase();
        this.keywords = keywords;
    }


    public String getCategoryName()
    {
        return categoryName;
    }


    public void setCategoryName( String newCategoryName )
    {
        this.categoryName = newCategoryName.toUpperCase();
    }


    public SortedSet<Keyword> getKeywords()
    {
        return keywords;
    }


    @Override
    public int compareTo( Category category )
    {
        return this.categoryName.compareTo( category.categoryName );
    }


    @Override
    public boolean equals( Object obj )
    {
        if( this == obj )
            return true;
        if( obj == null )
            return false;
        if( getClass() != obj.getClass() )
            return false;

        Category other = (Category)obj;

        if( categoryName == null )
        {
            if( other.categoryName != null )
                return false;
        }
        else if( !categoryName.equals( other.categoryName ) )
            return false;

        if( keywords == null )
        {
            return other.keywords == null;
        }
        else
            return keywords.equals( other.keywords );

    }


    @Override
    public int hashCode()
    {
        int result = Objects.hash( categoryName );
        result = 31 * result + keywords.hashCode();
        return result;
    }


    @Override
    protected Object clone()
    {
        return new Category( categoryName, new TreeSet<>( keywords ) );
    }
}
