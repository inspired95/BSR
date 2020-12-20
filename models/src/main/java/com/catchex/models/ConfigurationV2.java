package com.catchex.models;

import java.io.Serializable;


public class ConfigurationV2
    implements Serializable
{
    private static final long serialVersionUID = -7588980448693010399L;

    private static ConfigurationV2 INSTANCE;

    private CategoriesConfigurationV2 categoriesConfiguration;


    private ConfigurationV2()
    {
        this.categoriesConfiguration = new CategoriesConfigurationV2();
    }


    public static ConfigurationV2 getInstance()
    {
        if( INSTANCE == null )
        {
            INSTANCE = new ConfigurationV2();
        }
        return INSTANCE;
    }


    public void setConfiguration( ConfigurationV2 configuration )
    {
        setCategoriesConfiguration( configuration.categoriesConfiguration );
    }


    public CategoriesConfigurationV2 getCategoriesConfiguration()
    {
        return categoriesConfiguration;
    }


    public void setCategoriesConfiguration( CategoriesConfigurationV2 categoriesConfiguration )
    {
        this.categoriesConfiguration = categoriesConfiguration;
    }
}
