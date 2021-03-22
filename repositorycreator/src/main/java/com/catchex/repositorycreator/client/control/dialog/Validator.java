package com.catchex.repositorycreator.client.control.dialog;

public abstract class Validator<T>
{
    protected String errorMessage = "";


    public String getErrorMessage()
    {
        return errorMessage;
    }


    abstract Validator<T> valid( T object );
}
