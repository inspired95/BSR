package com.catchex.reportcreator.statictics;

import com.catchex.models.CurrentOperation;
import com.catchex.reportcreator.ReportUtil;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


public class OperationsStatistics
{
    private List<CurrentOperation> currentOperations;

    private ReportUtil reportUtil;

    private Map<String, Double> incomes;
    private Map<String, Double> expenses;
    private Map<String, Double> notResolvedOperations;
    private Double incomeSum = 0.0;
    private Double expensesSum = 0.0;
    private Double notResolvedSum = 0.0;
    private TreeMap<String, Map<String, Double>> expensesCategoriesPerMonth;


    public OperationsStatistics( List<CurrentOperation> currentOperations, ReportUtil reportUtil )
    {
        this.currentOperations = currentOperations;
        this.reportUtil = reportUtil;
        this.incomes = new HashMap<>();
        this.expenses = new HashMap<>();
        this.notResolvedOperations = new HashMap<>();
        this.expensesCategoriesPerMonth = new TreeMap<>();
        calculate();
    }


    public void calculate()
    {
        for( CurrentOperation operation : currentOperations )
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

        calculateExpensesCategoriesPerMonth();
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


    public List<CurrentOperation> getCurrentOperations()
    {
        return currentOperations;
    }


    public Map<String, Map<String, Double>> getExpensesCategoriesPerMonth()
    {
        return expensesCategoriesPerMonth;
    }


    private void calculateExpenses( CurrentOperation currentOperation )
    {
        Double currentSum = expenses.get( currentOperation.getCategory().getCategoryName() );
        if( currentSum == null )
        {
            currentSum = currentOperation.getOperation().getRawOperation().getAmount();
        }
        else
        {
            currentSum += currentOperation.getOperation().getRawOperation().getAmount();
        }
        expensesSum += currentOperation.getOperation().getRawOperation().getAmount();
        expenses.put( currentOperation.getCategory().getCategoryName(), currentSum );
    }


    private void calculateIncome( CurrentOperation currentOperation )
    {
        Double currentSum = incomes.get( currentOperation.getCategory().getCategoryName() );
        if( currentSum == null )
        {
            currentSum = currentOperation.getOperation().getRawOperation().getAmount();

        }
        else
        {
            currentSum += currentOperation.getOperation().getRawOperation().getAmount();
        }
        incomeSum += currentOperation.getOperation().getRawOperation().getAmount();
        incomes.put( currentOperation.getCategory().getCategoryName(), currentSum );
    }


    private void calculateNotResolvedOperations( CurrentOperation currentOperation )
    {
        Double currentSum =
            notResolvedOperations.get( currentOperation.getCategory().getCategoryName() );
        if( currentSum == null )
        {
            currentSum = currentOperation.getOperation().getRawOperation().getAmount();
        }
        else
        {
            currentSum += currentOperation.getOperation().getRawOperation().getAmount();
        }
        notResolvedSum += currentOperation.getOperation().getRawOperation().getAmount();
        notResolvedOperations.put( currentOperation.getCategory().getCategoryName(), currentSum );
    }


    private void calculateExpensesCategoriesPerMonth()
    {
        for( CurrentOperation currentOperation : currentOperations )
        {
            if( reportUtil.isExpense( currentOperation ) )
            {
                LocalDate month = currentOperation.getOperation().getRawOperation().getDate();
                String formattedDate = month.format( DateTimeFormatter.ofPattern( "yyyy/MM" ) );
                String category = currentOperation.getCategory().getCategoryName();
                Double amount = currentOperation.getOperation().getRawOperation().getAmount();

                Map<String, Double> monthCategoriesExpenses =
                    expensesCategoriesPerMonth.get( formattedDate );
                if( monthCategoriesExpenses == null )
                {
                    Map<String, Double> categoriesExpenses = new HashMap<>();
                    categoriesExpenses.put( category, amount );
                    expensesCategoriesPerMonth.put( formattedDate, categoriesExpenses );
                }
                else
                {
                    Double categoryExpenses = monthCategoriesExpenses.get( category );
                    if( categoryExpenses == null )
                    {
                        monthCategoriesExpenses.put( category, amount );
                    }
                    else
                    {
                        Double newExpensesValue = categoryExpenses + amount;
                        monthCategoriesExpenses.replace( category, newExpensesValue );
                    }
                }
            }
        }
    }


    private boolean isIncome( CurrentOperation currentOperation )
    {
        return reportUtil.isIncome( currentOperation );
    }


    private boolean isNotResolved( CurrentOperation currentOperation )
    {
        return reportUtil.isNotResolved( currentOperation );
    }
}
