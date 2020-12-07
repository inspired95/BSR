package com.catchex.report.web;

import com.catchex.models.Operation;
import com.catchex.models.OperationType;
import com.catchex.report.statictics.OperationsStatistics;
import j2html.tags.ContainerTag;

import java.util.*;
import java.util.function.Predicate;

import static com.catchex.models.OperationType.*;
import static j2html.TagCreator.*;


public class OperationTableGenerator
{
    private OperationsStatistics operationsStatistics;


    public OperationTableGenerator( OperationsStatistics operationsStatistics )
    {
        this.operationsStatistics = operationsStatistics;
    }


    public ContainerTag generateSourcesTable()
    {
        return div( generateHeader( "Sources: " ), generateSources() );
    }


    public ContainerTag generateNotResolvedOperationsTable( Comparator<Operation> comparator )
    {
        return div( generateHeader( "Not resolved operations list" ),
            generateOperations( this::isNotResolved, comparator ) );
    }


    public ContainerTag generateExpensesOperationsTable( Comparator<Operation> comparator )
    {
        return div(
            generateHeader( "Expenses list" ), generateOperations( this::isExpense, comparator ) );
    }


    public ContainerTag generateIncomesOperationsTable( Comparator<Operation> comparator )
    {
        return div(
            generateHeader( "Incomes list" ), generateOperations( this::isIncome, comparator ) );
    }


    public ContainerTag generateNotResolvedStatisticsTable()
    {
        return div( generateHeader( "Not resolved operations summary" ),
            generateStatisticTable( operationsStatistics.getNotResolvedOperations() ),
            generateOperationsSum( operationsStatistics.getNotResolvedSum(), ".notResolvedSum" ) );
    }


    public ContainerTag generateExpensesStatisticsTable()
    {
        return div( generateHeader( "Expenses summary" ),
            generateStatisticTable( operationsStatistics.getExpenses() ),
            generateOperationsSum( operationsStatistics.getExpensesSum(), ".expensesSum" ) );
    }


    public ContainerTag generateIncomesStatisticsTable()
    {
        return div( generateHeader( "Incomes summary" ),
            generateStatisticTable( operationsStatistics.getIncomes() ),
            generateOperationsSum( operationsStatistics.getIncomeSum(), ".incomesSum" ) );
    }


    private ContainerTag generateHeader( String text )
    {
        return h1( text );
    }


    private ContainerTag generateOperationsSum( Double value, String cssClass )
    {
        return h2( attrs( cssClass ), "Sum: " + String.format( "%.2f", value ) );
    }


    private ContainerTag generateStatisticTable( Map<String, Double> map )
    {
        Set<String> categories = map.keySet();
        Collection<Double> categoriesSums = map.values();

        ContainerTag header = tr();
        for( String category : categories )
        {
            header.with( td().with( span( category ) ) );
        }

        ContainerTag sums = tr();
        for( Double categorySum : categoriesSums )
        {
            sums.with( td().with( span( String.format( "%.2f", categorySum ) ) ) );
        }
        return table().with( thead( header ) ).with( sums );
    }


    private ContainerTag generateOperations(
        Predicate<Operation> predicate, Comparator<Operation> comparator )
    {
        ContainerTag table = table().with( generateOperationsTableHeader() );
        sortOperationByComparator( comparator );
        for( Operation operation : operationsStatistics.getOperations() )
        {
            if( predicate.test( operation ) )
                table.with( generateContent( operation ) );
        }
        return table;
    }


    private ContainerTag generateSources()
    {
        List<String> sources = new ArrayList<>( getSources() );
        ContainerTag sourceContainer = ul();
        sources.sort( Comparator.comparing( String::toString ) );
        for( String source : sources )
        {
            sourceContainer.with( li( source ) );
        }
        return sourceContainer;
    }


    private boolean isIncome( Operation operation )
    {
        return operation.getType().equals( OperationType.INCOME_TRANSFER ) ||
            operation.getType().equals( OperationType.REFUND );
    }


    private boolean isExpense( Operation operation )
    {
        return operation.getType().equals( OUTGOING_TRANSFER ) ||
            operation.getType().equals( DEBIT_CARD_PAYMENT ) ||
            operation.getType().equals( MOBILE_CODE_PAYMENT ) ||
            operation.getType().equals( CASH_WITHDRAWAL ) ||
            operation.getType().equals( COMMISSION );
    }


    private boolean isNotResolved( Operation operation )
    {
        return operation.getType().equals( OperationType.NOT_RESOLVED );
    }


    private ContainerTag generateContent( Operation operation )
    {
        return tr().with( td().with( span( operation.getRawOperation().getID() ) ),
            td().with( span( operation.getRawOperation().getDate().toString() ) ),
            td().with( span( operation.getType().name() ) ),
            td().with( span( operation.getCategory().getCategoryName() ) ),
            td().with( span( operation.getRawOperation().getAmount().toString() ) ),
            td().with( span( operation.getRawOperation().getDesc() ) ) );

    }


    private ContainerTag generateOperationsTableHeader()
    {
        return thead( tr().with( td().with( span( "ID" ) ), td().with( span( "Date" ) ),
            td().with( span( "Operation Type" ) ), td().with( span( "Category" ) ),
            td().with( span( "Amount" ) ), td().with( span( "Description" ) ) ) );
    }


    private void sortOperationByComparator( Comparator<Operation> comparator )
    {
        operationsStatistics.getOperations().sort( comparator );
    }


    private Set<String> getSources()
    {
        Set<String> sources = new HashSet<>();
        for( Operation operation : operationsStatistics.getOperations() )
        {
            sources.add( operation.getRawOperation().getFileName() );
        }
        return sources;
    }
}
