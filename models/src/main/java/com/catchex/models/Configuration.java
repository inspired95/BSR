package com.catchex.models;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;


public class Configuration
    implements Serializable
{
    private static final long serialVersionUID = -7588980448693010399L;

    private static Configuration instance;

    private CategoriesConfiguration categoriesConfiguration;

    private final PropertyChangeSupport categoriesConfigurationChangeSupport;


    private Configuration()
    {
        this.categoriesConfiguration = new CategoriesConfiguration();
        categoriesConfigurationChangeSupport = new PropertyChangeSupport( this );
    }


    public static Configuration getInstance()
    {
        if( instance == null )
        {
            instance = new Configuration();
        }
        return instance;
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
        categoriesConfigurationChangeSupport
            .firePropertyChange( "categoriesConfiguration", true, false );
    }


    public void addCategoriesConfigurationChangeListener(
        PropertyChangeListener propertyChangeListener )
    {
        categoriesConfigurationChangeSupport.addPropertyChangeListener( propertyChangeListener );
    }


    public void removeCategoriesConfigurationChangeListener(
        PropertyChangeListener propertyChangeListener )
    {
        categoriesConfigurationChangeSupport.removePropertyChangeListener( propertyChangeListener );
    }
}
