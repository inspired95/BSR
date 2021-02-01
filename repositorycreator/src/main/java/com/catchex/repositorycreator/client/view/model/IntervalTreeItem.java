package com.catchex.repositorycreator.client.view.model;

import com.catchex.models.CurrentOperation;
import javafx.beans.property.SimpleDoubleProperty;

import java.beans.PropertyChangeEvent;
import java.time.LocalDate;


public class IntervalTreeItem
    extends AbstractTreeItem
{
    public IntervalTreeItem( LocalDate date )
    {
        super( CurrentOperation.DUMMY_CURRENT_OPERATION );
        this.date = date;
        overrideDefaultNanValue();
    }


    @Override
    public void propertyChange( PropertyChangeEvent propertyChangeEvent )
    {
        //left blank intentionally
    }


    private void overrideDefaultNanValue()
    {
        this.amount = new SimpleDoubleProperty( 0.0 );
    }
}
