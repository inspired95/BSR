package com.catchex.repositorycreator.client.view.model;

import com.catchex.models.Operation;
import javafx.beans.property.SimpleDoubleProperty;

import java.time.LocalDate;


public class IntervalTreeItem
    extends AbstractTreeItem
{
    public IntervalTreeItem( LocalDate date )
    {
        super( Operation.DUMMY_OPERATION );
        this.date = date;
        overrideDefaultNanValue();
    }


    private void overrideDefaultNanValue()
    {
        this.amount = new SimpleDoubleProperty( 0.0 );
    }
}
