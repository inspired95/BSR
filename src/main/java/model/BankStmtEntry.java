package model;

import operationtype.OperationType;

import java.time.LocalDate;


public class BankStmtEntry
{
    LocalDate date;
    String ID;
    OperationType type;
    Double amount;
    String desc;


    public LocalDate getDate()
    {
        return date;
    }


    public String getID()
    {
        return ID;
    }


    public OperationType getType()
    {
        return type;
    }


    public Double getAmount()
    {
        return amount;
    }


    public String getDesc()
    {
        return desc;
    }


    protected BankStmtEntry(
        LocalDate date, String ID, OperationType type, Double amount, String desc )
    {
        this.date = date;
        this.ID = ID;
        this.type = type;
        this.amount = amount;
        this.desc = desc;
    }


    public static final class Builder
    {
        LocalDate date;
        String ID;
        OperationType type;
        Double amount;
        String desc;


        public Builder( String ID )
        {
            this.ID = ID;
        }


        public Builder setDate( LocalDate date )
        {
            this.date = date;
            return this;
        }


        public Builder setType( OperationType type )
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


        public LocalDate getDate()
        {
            return date;
        }


        public String getID()
        {
            return ID;
        }


        public OperationType getType()
        {
            return type;
        }


        public Double getAmount()
        {
            return amount;
        }


        public String getDesc()
        {
            return desc;
        }


        public boolean isValid()
        {
            return validID() && validOperationType() && validAmount() && validDescription();
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
            return !type.equals( OperationType.NOT_RESOLVED );
        }


        private boolean validID()
        {
            return !ID.isEmpty() && ID.length() == 17;
        }


        public BankStmtEntry build()
        {
            return new BankStmtEntry( date, ID, type, amount, desc );
        }
    }


    @Override
    public String toString()
    {
        return "BankStmtEntry{" + "\n\tdate=" + date + "\n\tID='" + ID + '\'' + "\n\ttype='" +
            type + '\'' + "\n\tamount=" + amount + "\n\tdesc='" + desc + '\'' + "\n}";
    }
}
