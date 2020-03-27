package app;

import model.CategoriesConfiguration;


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
