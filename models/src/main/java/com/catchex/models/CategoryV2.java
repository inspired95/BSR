package com.catchex.models;

import java.io.Serializable;
import java.util.Objects;
import java.util.SortedSet;
import java.util.TreeSet;


public class CategoryV2
    implements Comparable<CategoryV2>, Serializable, Cloneable
{
    static final long serialVersionUID = -7588980448693010398L;

    public static CategoryV2 OTHER_CATEGORY = new CategoryV2( "Other", new TreeSet<>() );
    public static CategoryV2 CASH_WITHDRAWAL = new CategoryV2( "Cash withdrawal", new TreeSet<>() );
    private String categoryName;
    private SortedSet<Keyword> keywords;


    public CategoryV2( String categoryName, SortedSet<Keyword> keywords )
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
    public int compareTo( CategoryV2 categoryV2 )
    {
        return this.categoryName.compareTo( categoryV2.categoryName );
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

        CategoryV2 other = (CategoryV2)obj;

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
        return new CategoryV2( categoryName, new TreeSet<>( keywords ) );
    }
}
