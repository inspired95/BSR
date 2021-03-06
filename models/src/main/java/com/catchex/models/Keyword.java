package com.catchex.models;

import java.io.Serializable;
import java.util.Objects;


public class Keyword
    implements Serializable, Comparable<Keyword>, Cloneable
{
    private static final long serialVersionUID = -7588980448693010396L;

    private String value;


    public Keyword( String value )
    {
        this.value = convertIntoValidValue( value );
    }


    public String getValue()
    {
        return value;
    }


    public void setValue( String value )
    {
        this.value = convertIntoValidValue( value );
    }


    @Override
    public int compareTo( Keyword keyword )
    {
        return this.getValue().compareTo( keyword.getValue() );
    }


    @Override
    public boolean equals( Object o )
    {
        if( this == o )
            return true;
        if( !(o instanceof Keyword) )
            return false;
        Keyword keyword = (Keyword)o;
        return Objects.equals( value, keyword.value );
    }


    @Override
    public int hashCode()
    {
        return Objects.hash( value );
    }


    @Override
    public String toString()
    {
        return value;
    }


    @Override
    protected Object clone()
    {
        return new Keyword( value );
    }


    private String convertIntoValidValue( String value )
    {
        return value.toLowerCase();
    }
}
