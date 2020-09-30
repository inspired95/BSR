package com.catchex.models;


import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDate;
import java.util.Objects;


public class RawOperation
{
    private LocalDate date;
    private StringProperty ID;
    private StringProperty type;
    private DoubleProperty amount;
    private StringProperty desc;
    private StringProperty fileName;


    public LocalDate getDate()
    {
        return date;
    }


    public String getID()
    {
        return ID.getValue();
    }


    public String getType()
    {
        return type.getValue();
    }


    public Double getAmount()
    {
        return amount.getValue();
    }

    public String getFileName() {
        return fileName.getValue();
    }

    public StringProperty fileNameProperty() {
        return fileName;
    }

    public String getDesc()
    {
        return desc.getValue();
    }


    protected RawOperation(
        LocalDate date, StringProperty ID, StringProperty type, DoubleProperty amount, StringProperty desc, StringProperty fileName )
    {
        this.date = date;
        this.ID = ID;
        this.type = type;
        this.amount = amount;
        this.desc = desc;
        this.fileName = fileName;
    }


    public static final class Builder
    {
        private LocalDate date;
        private StringProperty ID;
        private StringProperty type;
        private DoubleProperty amount;
        private StringProperty desc;
        private StringProperty fileName;


        public Builder( String ID )
        {
            this.ID = new SimpleStringProperty(ID);
        }


        public Builder setDate( LocalDate date )
        {
            this.date = date;
            return this;
        }

        public Builder setFileName( String fileName )
        {
            this.fileName = new SimpleStringProperty(fileName);
            return this;
        }


        public Builder setType( String type )
        {
            this.type = new SimpleStringProperty(type);
            return this;
        }


        public Builder setAmount( Double amount )
        {
            this.amount = new SimpleDoubleProperty(amount);
            return this;
        }


        public Builder setDesc( String desc )
        {
            this.desc = new SimpleStringProperty(desc);
            return this;
        }


        public boolean isValid()
        {
            return validID() && validOperationType() && validAmount() && validDescription() && validFileName();
        }


        private boolean validDescription()
        {
            return !desc.get().isEmpty();
        }


        private boolean validAmount()
        {
            return !amount.equals( Double.NaN );
        }


        private boolean validOperationType()
        {
            return !type.get().isEmpty();
        }


        private boolean validID()
        {
            return ID.get().length() == 17;
        }

        private boolean validFileName(){
            return !fileName.get().isEmpty();
        }


        public RawOperation build()
        {
            return new RawOperation( date, ID, type, amount, desc, fileName );
        }
    }


    @Override
    public String toString()
    {
        return "BankStmtEntry{" + "\n\tdate=" + date + "\n\tID='" + ID + '\'' + "\n\ttype='" +
            type + '\'' + "\n\tamount=" + amount + "\n\tdesc='" + desc + '\'' + "\n}";
    }

    @Override
    public int hashCode()
    {
        return Objects.hash( ID, date, desc, type, amount );
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

        RawOperation other = (RawOperation)obj;

        if( ID == null )
        {
            if( other.ID != null )
                return false;
        }
        else if( !ID.equals( other.ID ) )
            return false;

        if( date == null )
        {
            if( other.date != null )
                return false;
        }
        else if( !date.equals( other.date ) )
            return false;

        if( type == null )
        {
            return other.type == null;
        }
        else if( !type.equals( other.type ) )
            return false;


        if( amount == null )
        {
            return other.amount == null;
        }
        else if( !amount.equals( other.amount ) )
            return false;

        if( desc == null )
        {
            return other.desc == null;
        }
        else if( !desc.equals( other.desc ) )
            return false;

        if (fileName == null )
        {
            return other.fileName == null;
        }
        else return fileName.equals(other.fileName);

    }
}
