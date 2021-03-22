package com.catchex.repositorycreator.client.control.event;

import com.catchex.models.Configuration;
import com.catchex.repositorycreator.client.control.RepositoryCreatorDialogController;
import com.catchex.repositorycreator.client.model.CurrentRepositoryUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;


public class CategoriesConfigurationChangeListener
    implements PropertyChangeListener
{
    private static final Logger logger =
        LoggerFactory.getLogger( CategoriesConfigurationChangeListener.class );

    private final RepositoryCreatorDialogController controller;


    public CategoriesConfigurationChangeListener( RepositoryCreatorDialogController controller )
    {
        this.controller = controller;
        Configuration.getInstance().addCategoriesConfigurationChangeListener( this );
    }


    @Override
    public void propertyChange( PropertyChangeEvent propertyChangeEvent )
    {
        logger.info( "Categories configuration changed event" );
        new CurrentRepositoryUtil().recalculateCategories();
        controller.refreshView();
    }


    public void stopListen()
    {
        Configuration.getInstance().removeCategoriesConfigurationChangeListener( this );
    }
}
