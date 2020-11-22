package com.catchex.models;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;


public class RawOperation
    implements Serializable
{
    public static final RawOperation DUMMY_RAW_OPERATION =
        new RawOperation( LocalDate.MIN, "", "", Double.NaN, "", "", "" );

    private LocalDate date;
    private String ID;
    private String type;
    private Double amount;
    private String desc;
    private String fileName;
    private String bank;


    public RawOperation()
    {

    }


    public LocalDate getDate()
    {
        return date;
    }


    public String getID()
    {
        return ID;
    }//new SimpleStringProperty(ID)


    public String getType()
    {
        return type;
    }


    public Double getAmount()
    {
        return amount;
    }


    public String getFileName()
    {
        return fileName;
    }


    public String getBank()
    {
        return bank;
    }


    public String getDesc()
    {
        return desc;
    }


    public void setDesc( String desc )
    {
        this.desc = desc;
    }


    public void setAmount( Double amount )
    {
        this.amount = amount;
    }


    protected RawOperation(
        LocalDate date, String ID, String type, Double amount, String desc, String fileName,
        String bank )
    {
        this.date = date;
        this.ID = ID;
        this.type = type;
        this.amount = amount;
        this.desc = desc;
        this.fileName = fileName;
        this.bank = bank;
    }


    public static final class Builder
    {
        private LocalDate date;
        private String ID;
        private String type;
        private Double amount;
        private String desc;
        private String fileName;
        private String bank;


        public Builder( String ID )
        {
            this.ID = ID;
        }


        public Builder setDate( LocalDate date )
        {
            this.date = date;
            return this;
        }


        public Builder setFileName( String fileName )
        {
            this.fileName = fileName;
            return this;
        }


        public Builder setType( String type )
        {
            this.type = type;
            return this;
        }


        public Builder setAmount( Double amount )
        {
            this.amount = amount;
            return this;
        }


        public Builder setDesc( String desc )
        {
            this.desc = desc;
            return this;
        }


        public Builder setBank( String bank )
        {
            this.bank = bank;
            return this;
        }


        public boolean isValid()
        {
            return validID() && validOperationType() && validAmount() && validDescription() &&
                validFileName();
        }


        private boolean validDescription()
        {
            return !desc.isEmpty();
        }


        private boolean validAmount()
        {
            return !amount.equals( Double.NaN );
        }


        private boolean validOperationType()
        {
            return !type.isEmpty();
        }


        private boolean validID()
        {
            return ID.length() == 17;
        }


        private boolean validFileName()
        {
            return !fileName.isEmpty();
        }


        public RawOperation build()
        {
            return new RawOperation( date, ID, type, amount, desc, fileName, bank );
        }
    }


    public boolean isValid()
    {
        return validID() && validOperationType() && validAmount() && validDescription() &&
            validFileName();
    }


    private boolean validDescription()
    {
        return !desc.isEmpty();
    }


    private boolean validAmount()
    {
        return !amount.equals( Double.NaN );
    }


    private boolean validOperationType()
    {
        return !type.isEmpty();
    }


    private boolean validID()
    {
        return ID.length() == 17;
    }


    private boolean validFileName()
    {
        return !fileName.isEmpty();
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

        if( fileName == null )
        {
            return other.fileName == null;
        }
        else
            return fileName.equals( other.fileName );

    }
}
