package com.catchex.repositorycreator.client.control.event;

import com.catchex.models.Configuration;
import com.catchex.repositorycreator.client.control.RepositoryCreatorDialogController;
import com.catchex.repositorycreator.client.model.CurrentRepositoryUtil;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;


public class CategoriesConfigurationChangeListener
    implements PropertyChangeListener
{
    private RepositoryCreatorDialogController controller;


    public CategoriesConfigurationChangeListener( RepositoryCreatorDialogController controller )
    {
        this.controller = controller;
        Configuration.getInstance().addCategoriesConfigurationChangeListener( this );
    }


    @Override
    public void propertyChange( PropertyChangeEvent propertyChangeEvent )
    {
        new CurrentRepositoryUtil().recalculateCategories();
        controller.refreshView();
    }


    public void stopListen()
    {
        Configuration.getInstance().removeCategoriesConfigurationChangeListener( this );
    }
}
