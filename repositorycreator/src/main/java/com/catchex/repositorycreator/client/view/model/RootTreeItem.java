package com.catchex.repositorycreator.client.view.model;

import com.catchex.models.CurrentOperation;

import java.beans.PropertyChangeEvent;


public class RootTreeItem
    extends AbstractTreeItem
{

    public RootTreeItem()
    {
        super( CurrentOperation.DUMMY_CURRENT_OPERATION );
    }


    @Override
    public void propertyChange( PropertyChangeEvent propertyChangeEvent )
    {
        //left blank intentionally
    }
}
