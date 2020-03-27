package model;

import operationtype.OperationType;

import java.time.LocalDate;


public class Operation
{
    private String ID;
    private LocalDate date;
    private OperationType type;
    private Double amount;
    private Category category;


    public Operation(
        String ID, LocalDate date, OperationType type, Double amount, Category category )
    {
        this.ID = ID;
        this.date = date;
        this.type = type;
        this.amount = amount;
        this.category = category;
    }


    public String getID()
    {
        return ID;
    }


    public LocalDate getDate()
    {
        return date;
    }


    public OperationType getType()
    {
        return type;
    }


    public Double getAmount()
    {
        return amount;
    }


    public Category getCategory()
    {
        return category;
    }
}
