package com.catchex.repositorycreator.client.model.repository;

import com.catchex.models.CurrentRepository;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;


public class CurrentRepositoryHolder
    implements Holder<CurrentRepository>
{
    private static CurrentRepositoryHolder INSTANCE;

    private final CurrentRepository currentRepository;

    private PropertyChangeSupport currentRepositoryChangeSupport;


    private CurrentRepositoryHolder()
    {
        currentRepository = new CurrentRepository();
    }


    public static CurrentRepositoryHolder getInstance()
    {
        if( INSTANCE == null )
        {
            INSTANCE = new CurrentRepositoryHolder();
        }
        return INSTANCE;
    }


    public CurrentRepository get()
    {
        return currentRepository;
    }


    public void addListener( PropertyChangeListener propertyChangeListener )
    {
        if( currentRepositoryChangeSupport == null )
        {
            currentRepositoryChangeSupport = new PropertyChangeSupport( INSTANCE );
        }
        currentRepositoryChangeSupport.addPropertyChangeListener( propertyChangeListener );
    }


    public void notifyCurrentRepositorySizeChanged()
    {
        currentRepositoryChangeSupport
            .firePropertyChange( "CurrentRepositoryChanged", false, true );
    }
}
