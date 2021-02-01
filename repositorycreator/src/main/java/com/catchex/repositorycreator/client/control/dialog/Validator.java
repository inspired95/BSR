package com.catchex.repositorycreator.client.control.dialog;

public abstract class Validator<T>
{
    protected String errorMessage = "";


    public String getErrorMessage()
    {
        return errorMessage;
    }


    abstract Validator valid( T object );
}
