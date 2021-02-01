package com.catchex.models;

import java.io.Serializable;
import java.util.Objects;


public class Operation
    implements Serializable
{
    public static final Operation DUMMY_OPERATION =
        new Operation( RawOperation.DUMMY_RAW_OPERATION, OperationType.NOT_RESOLVED );
    protected RawOperation rawOperation;
    protected OperationType type;


    public Operation()
    {

    }


    public Operation(
        RawOperation rawOperation, OperationType type )
    {
        this.rawOperation = rawOperation;
        this.type = type;
    }


    public RawOperation getRawOperation()
    {
        return rawOperation;
    }


    public OperationType getType()
    {
        return type;
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
        return true;
    }


    @Override
    public int hashCode()
    {
        return Objects.hash( rawOperation, type );
    }


    @Override
    public String toString()
    {
        return "Operation{" + "rawOperation=" + rawOperation + ", type=" + type + '}';
    }
}
