package com.catchex.configuration;

import com.catchex.models.CategoriesConfiguration;


public class Configuration
{
    private static CategoriesConfiguration categoriesConfiguration;


    public static CategoriesConfiguration getCategoriesConfiguration()
    {
        return categoriesConfiguration;
    }


    public static void setCategoriesConfiguration(
        CategoriesConfiguration categoriesConfiguration )
    {
        Configuration.categoriesConfiguration = categoriesConfiguration;
    }
}
