package com.catchex.repositorycreator.client.model.repository;

import com.catchex.models.CurrentRepository;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;


public class CurrentRepositoryHolder
    implements Holder<CurrentRepository>
{
    private static CurrentRepositoryHolder instance;

    private final CurrentRepository currentRepository;

    private PropertyChangeSupport currentRepositoryChangeSupport;


    private CurrentRepositoryHolder()
    {
        currentRepository = new CurrentRepository();
    }


    public static CurrentRepositoryHolder getInstance()
    {
        if( instance == null )
        {
            instance = new CurrentRepositoryHolder();
        }
        return instance;
    }


    public CurrentRepository get()
    {
        return currentRepository;
    }


    public void addListener( PropertyChangeListener propertyChangeListener )
    {
        if( currentRepositoryChangeSupport == null )
        {
            currentRepositoryChangeSupport = new PropertyChangeSupport( instance );
        }
        currentRepositoryChangeSupport.addPropertyChangeListener( propertyChangeListener );
    }


    public void notifyCurrentRepositorySizeChanged()
    {
        currentRepositoryChangeSupport
            .firePropertyChange( "CurrentRepositoryChanged", false, true );
    }
}
