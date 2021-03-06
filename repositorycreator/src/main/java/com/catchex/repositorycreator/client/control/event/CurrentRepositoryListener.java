package com.catchex.repositorycreator.client.control.event;

import com.catchex.repositorycreator.client.control.RepositoryCreatorDialogController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;


public class CurrentRepositoryListener
    implements PropertyChangeListener
{
    private static final Logger logger = LoggerFactory.getLogger( CurrentRepositoryListener.class );

    private final RepositoryCreatorDialogController controller;


    public CurrentRepositoryListener( RepositoryCreatorDialogController controller )
    {
        this.controller = controller;
    }


    @Override
    public void propertyChange( PropertyChangeEvent propertyChangeEvent )
    {
        logger.info( "Current repository changed event" );
        controller.onCurrentRepositoryChange();
    }
}
