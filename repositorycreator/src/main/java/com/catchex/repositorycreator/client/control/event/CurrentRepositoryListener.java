package com.catchex.repositorycreator.client.control.event;

import com.catchex.repositorycreator.client.control.RepositoryCreatorDialogController;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;


public class CurrentRepositoryListener
    implements PropertyChangeListener
{
    private RepositoryCreatorDialogController controller;


    public CurrentRepositoryListener( RepositoryCreatorDialogController controller )
    {
        this.controller = controller;
    }


    @Override
    public void propertyChange( PropertyChangeEvent propertyChangeEvent )
    {
        controller.onCurrentRepositoryChange();
    }
}
