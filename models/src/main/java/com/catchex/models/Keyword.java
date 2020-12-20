package com.catchex.models;

import java.io.Serializable;


public class Keyword
    implements Serializable, Comparable<Keyword>, Cloneable
{
    private static final long serialVersionUID = -7588980448693010396L;

    private String value;


    public Keyword( String value )
    {
        this.value = value.toLowerCase();
    }


    public String getValue()
    {
        return value;
    }


    public void setValue( String value )
    {
        this.value = value.toLowerCase();
    }


    @Override
    public int compareTo( Keyword keyword )
    {
        return this.getValue().compareTo( keyword.getValue() );
    }
}
