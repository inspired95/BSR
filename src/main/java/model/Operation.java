package model;

import operationtype.OperationType;

import java.util.Comparator;
import java.util.Objects;


public class Operation
{
    private RawOperation rawOperation;
    private OperationType type;
    private Category category;


    public Operation(
        RawOperation rawOperation, OperationType type, Category category )
    {
        this.rawOperation = rawOperation;
        this.type = type;
        this.category = category;
    }


    public RawOperation getRawOperation()
    {
        return rawOperation;
    }


    public OperationType getType()
    {
        return type;
    }


    public Category getCategory()
    {
        return category;
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

        Operation other = (Operation)obj;

        if( rawOperation == null )
        {
            if( other.rawOperation != null )
                return false;
        }
        else if( !rawOperation.equals( other.rawOperation ) )
            return false;

        if( type == null )
        {
            if( other.type != null )
                return false;
        }
        else if( !type.equals( other.type ) )
            return false;

        if( category == null )
        {
            return other.category == null;
        }
        else if( !category.equals( other.category ) )
            return false;
        return true;
    }


    @Override
    public int hashCode()
    {
        return Objects.hash( rawOperation, type, category );
    }


    public static class OperationAmountComparator
        implements Comparator<Operation>
    {

        @Override
        public int compare( Operation o1, Operation o2 )
        {
            return o1.getRawOperation().getAmount().compareTo( o2.getRawOperation().getAmount() );
        }
    }


    public static class OperationTypeComparator
        implements Comparator<Operation>
    {

        @Override
        public int compare( Operation o1, Operation o2 )
        {
            return o1.getType().compareTo( o2.getType() );
        }
    }


    public static class OperationCategoryComparator
        implements Comparator<Operation>
    {

        @Override
        public int compare( Operation o1, Operation o2 )
        {
            return o1.getCategory().getCategoryName()
                .compareTo( o2.getCategory().getCategoryName() );
        }
    }


    public static class OperationDateComparator
        implements Comparator<Operation>
    {

        @Override
        public int compare( Operation o1, Operation o2 )
        {
            return o1.getRawOperation().getDate().compareTo( o2.getRawOperation().getDate() );
        }
    }
}
