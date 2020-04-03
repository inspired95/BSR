package model;

import operationtype.OperationType;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.Objects;


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

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;

        Operation other = (Operation) obj;

        if (ID == null) {
            if (other.ID != null)
                return false;
        } else if (!ID.equals(other.ID))
            return false;

        if (date == null) {
            if (other.date != null)
                return false;
        } else if (!date.equals(other.date))
            return false;

        if (type == null) {
            if (other.type != null)
                return false;
        } else if (!type.equals(other.type))
            return false;

        if (category == null) {
            if (other.category != null)
                return false;
        } else if (!category.equals(other.category))
            return false;

        if (amount == null) {
            if (other.amount != null)
                return false;
        } else if (!amount.equals(other.amount))
            return false;

        return true;
    }


    @Override
    public int hashCode()
    {
        return Objects.hash( ID, date, type, amount, category );
    }


    public static class OperationAmountComparator implements Comparator<Operation>{

        @Override
        public int compare( Operation o1, Operation o2 )
        {
            return o1.getAmount().compareTo( o2.getAmount() );
        }
    }

    public static class OperationTypeComparator implements Comparator<Operation>{

        @Override
        public int compare( Operation o1, Operation o2 )
        {
            return o1.getType().compareTo( o2.getType() );
        }
    }

    public static class OperationCategoryComparator implements Comparator<Operation>{

        @Override
        public int compare( Operation o1, Operation o2 )
        {
            return o1.getCategory().getCategoryName().compareTo( o2.getCategory().getCategoryName() );
        }
    }


    public static class OperationDateComparator implements Comparator<Operation>{

        @Override
        public int compare( Operation o1, Operation o2 )
        {
            return o1.getDate().compareTo( o2.getDate() );
        }
    }
}
