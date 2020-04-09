package com.catchex.report.statictics;

import com.catchex.models.Operation;
import com.catchex.models.OperationType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class OperationsStatistics
{
    private List<Operation> operations;

    private Map<String, Double> incomes;
    private Map<String, Double> expenses;
    private Map<String, Double> notResolvedOperations;
    private Double incomeSum = 0.0;
    private Double expensesSum = 0.0;
    private Double notResolvedSum = 0.0;


    public OperationsStatistics( List<Operation> operations )
    {
        this.operations = operations;
        this.incomes = new HashMap<>();
        this.expenses = new HashMap<>();
        this.notResolvedOperations = new HashMap<>();
        calculate();
    }


    public void calculate()
    {
        for( Operation operation : operations )
        {
            if( isNotResolved( operation ) )
            {
                calculateNotResolvedOperations( operation );
            }
            else if( isIncome( operation ) )
            {
                calculateIncome( operation );
            }
            else
            {
                calculateExpenses( operation );
            }
        }
    }

    private void calculateExpenses( Operation operation )
    {
        Double currentSum = expenses.get( operation.getCategory().getCategoryName() );
        if( currentSum == null )
        {
            currentSum = operation.getRawOperation().getAmount();
        }
        else
        {
            currentSum += operation.getRawOperation().getAmount();
        }
        expensesSum += operation.getRawOperation().getAmount();
        expenses.put( operation.getCategory().getCategoryName(), currentSum );
    }


    private void calculateIncome( Operation operation )
    {
        Double currentSum = incomes.get( operation.getCategory().getCategoryName() );
        if( currentSum == null )
        {
            currentSum = operation.getRawOperation().getAmount();

        }
        else
        {
            currentSum += operation.getRawOperation().getAmount();
        }
        incomeSum += operation.getRawOperation().getAmount();
        incomes.put( operation.getCategory().getCategoryName(), currentSum );
    }


    private void calculateNotResolvedOperations( Operation operation )
    {
        Double currentSum =
            notResolvedOperations.get( operation.getCategory().getCategoryName() );
        if( currentSum == null )
        {
            currentSum = operation.getRawOperation().getAmount();
        }
        else
        {
            currentSum += operation.getRawOperation().getAmount();
        }
        notResolvedSum += operation.getRawOperation().getAmount();
        notResolvedOperations.put( operation.getCategory().getCategoryName(), currentSum );
    }


    private boolean isIncome( Operation operation )
    {
        return operation.getType().equals( OperationType.INCOME_TRANSFER ) ||
            operation.getType().equals( OperationType.REFUND );
    }


    private boolean isNotResolved( Operation operation )
    {
        return operation.getType().equals( OperationType.NOT_RESOLVED );
    }


    public Map<String, Double> getIncomes()
    {
        return incomes;
    }


    public Map<String, Double> getExpenses()
    {
        return expenses;
    }


    public Map<String, Double> getNotResolvedOperations()
    {
        return notResolvedOperations;
    }


    public double getIncomeSum()
    {
        return incomeSum;
    }


    public double getExpensesSum()
    {
        return expensesSum;
    }


    public double getNotResolvedSum()
    {
        return notResolvedSum;
    }
}
