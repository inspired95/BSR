package com.catchex.models;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


public class Repository
    implements Serializable
{
    private static final long serialVersionUID = 4L;
    private Set<Operation> operations;


    public Repository()
    {
        this( new HashSet<>() );
    }


    public Repository( Set<Operation> operations )
    {
        this.operations = operations;
    }


    public Set<Operation> getOperations()
    {
        return operations;
    }
}
