package com.catchex.models;

import java.util.HashSet;
import java.util.Set;


public class CurrentRepository
{
    private Set<CurrentOperation> operations;


    public CurrentRepository()
    {
        this( new HashSet<>() );
    }


    public CurrentRepository( Set<CurrentOperation> operations )
    {
        this.operations = operations;
    }


    public Set<CurrentOperation> getOperations()
    {
        return operations;
    }
}
