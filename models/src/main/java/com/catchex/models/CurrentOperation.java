package com.catchex.models;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Comparator;
import java.util.Objects;


public class CurrentOperation
{
    public static final CurrentOperation DUMMY_CURRENT_OPERATION =
        new CurrentOperation( Operation.DUMMY_OPERATION, Category.OTHER_CATEGORY );
    private PropertyChangeSupport propertyChangeSupport;
    private Operation operation;
    private Category category;


    public CurrentOperation( Operation operation, Category category )
    {
        this.operation = operation;
        this.category = category;
        propertyChangeSupport = new PropertyChangeSupport( this );
    }


    public Operation getOperation()
    {
        return operation;
    }


    public Category getCategory()
    {
        return category;
    }


    public void setCategory( Category newCategory )
    {
        if( !this.category.equals( newCategory ) )
        {
            Category oldCategory = this.category;
            this.category = newCategory;
            fireCategoryChange( "Category", oldCategory, newCategory );
        }
    }


    public void setDescription( String description )
    {
        String oldDescription = this.operation.getRawOperation().getDesc();
        this.operation.getRawOperation().setDesc( description );
        fireCategoryChange( "Description", oldDescription, description );
    }


    @Override
    public String toString()
    {
        return "CurrentOperation{ operation=" + operation + ", category=" +
            category.getCategoryName() + '}';
    }


    @Override
    public boolean equals( Object obj )
    {
        if( this == obj )
            return true;
        if( obj == null )
            return false;
        if( getClass() != obj.getClass() )
            return false;

        CurrentOperation other = (CurrentOperation)obj;
        if( !operation.equals( other.getOperation() ) )
        {
            return false;
        }
        return category.equals( other.getCategory() );
    }


    @Override
    public int hashCode()
    {
        return Objects.hash( operation.hashCode(), category );
    }


    public void addCategoryChangeListener( PropertyChangeListener listener )
    {
        propertyChangeSupport.addPropertyChangeListener( listener );
    }


    protected void fireCategoryChange( String propertyName, Object oldValue, Object newValue )
    {
        propertyChangeSupport.firePropertyChange( propertyName, oldValue, newValue );
    }


    public static class CurrentOperationAmountComparator
        implements Comparator<CurrentOperation>
    {

        @Override
        public int compare( CurrentOperation o1, CurrentOperation o2 )
        {
            return o1.getOperation().getRawOperation().getAmount()
                .compareTo( o2.getOperation().getRawOperation().getAmount() );
        }
    }


    public static class CurrentOperationTypeComparator
        implements Comparator<CurrentOperation>
    {

        @Override
        public int compare( CurrentOperation o1, CurrentOperation o2 )
        {
            return o1.getOperation().getType().compareTo( o2.getOperation().getType() );
        }
    }


    public static class CurrentOperationCategoryComparator
        implements Comparator<CurrentOperation>
    {

        @Override
        public int compare( CurrentOperation o1, CurrentOperation o2 )
        {
            return o1.category.getCategoryName().compareTo( o2.category.getCategoryName() );
        }
    }


    public static class CurrentOperationDateComparator
        implements Comparator<CurrentOperation>
    {

        @Override
        public int compare( CurrentOperation o1, CurrentOperation o2 )
        {
            return o1.getOperation().getRawOperation().getDate()
                .compareTo( o2.getOperation().getRawOperation().getDate() );
        }
    }
}
