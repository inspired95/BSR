package com.catchex.repositorycreator.client.view.model;

import com.catchex.models.Category;
import com.catchex.models.CurrentOperation;

import java.beans.PropertyChangeEvent;
import java.io.Serializable;


public class OperationTreeItem
    extends AbstractTreeItem
    implements Serializable
{
    public OperationTreeItem()
    {
        super();
    }


    public OperationTreeItem( CurrentOperation operation )
    {
        super( operation );
    }


    public void setDesc( String desc )
    {
        this.desc.set( desc );
    }


    public void setCategory( Category category )
    {
        this.category.set( category.getCategoryName() );
    }


    public CurrentOperation getOperation()
    {
        return this.operation;
    }


    @Override
    public void propertyChange( PropertyChangeEvent propertyChangeEvent )
    {
        Object newValue = propertyChangeEvent.getNewValue();
        if( propertyChangeEvent.getPropertyName().equals( "Description" ) )
        {
            setDesc( (String)newValue );
        }
        else if( propertyChangeEvent.getPropertyName().equals( "Category" ) )
        {
            setCategory( (Category)newValue );
        }
    }


    @Override
    public String toString()
    {
        return "OperationTreeItem{" + "operation=" + operation + ", date=" + date + ", ID=" + ID +
            ", type=" + type + ", amount=" + amount + ", desc=" + desc + ", category=" + category +
            ", fileName=" + fileName + ", bankName=" + bankName + '}';
    }
}
