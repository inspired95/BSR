package com.catchex.models;

import java.io.Serializable;


public class Configuration
    implements Serializable
{
    private static final long serialVersionUID = -7588980448693010399L;

    private static Configuration INSTANCE;

    private CategoriesConfiguration categoriesConfiguration;


    private Configuration()
    {
        this.categoriesConfiguration = new CategoriesConfiguration();
    }


    public static Configuration getInstance()
    {
        if( INSTANCE == null )
        {
            INSTANCE = new Configuration();
        }
        return INSTANCE;
    }


    public void setConfiguration( Configuration configuration )
    {
        setCategoriesConfiguration( configuration.categoriesConfiguration );
    }


    public CategoriesConfiguration getCategoriesConfiguration()
    {
        return categoriesConfiguration;
    }


    public void setCategoriesConfiguration( CategoriesConfiguration categoriesConfiguration )
    {
        this.categoriesConfiguration = categoriesConfiguration;
    }
}
