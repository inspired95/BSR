package com.catchex.models;

import java.util.Arrays;
import java.util.Objects;


public class Category
{
    public static Category OTHER_CATEGORY = new Category( "Other", new String[] {} );
    private String categoryName;
    private String[] keywords;


    public Category( String categoryName, String[] keywords )
    {
        this.categoryName = categoryName;
        this.keywords = keywords;
    }


    public String getCategoryName()
    {
        return categoryName;
    }


    public String[] getKeywords()
    {
        return keywords;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;

        Category other = (Category) obj;

        if (categoryName == null) {
            if (other.categoryName != null)
                return false;
        } else if (!categoryName.equals(other.categoryName))
            return false;

        if (keywords == null) {
            if (other.keywords != null)
                return false;
        } else if (!keywords.equals(other.keywords))
            return false;


        return true;
    }


    @Override
    public int hashCode()
    {
        int result = Objects.hash( categoryName );
        result = 31 * result + Arrays.hashCode( keywords );
        return result;
    }
}
