package com.catchex.reportcreator.web;

import com.catchex.models.CurrentOperation;
import com.catchex.reportcreator.ReportUtil;
import com.catchex.reportcreator.statictics.OperationsStatistics;
import j2html.tags.ContainerTag;

import java.util.*;
import java.util.function.Predicate;

import static j2html.TagCreator.*;


public class OperationTableGenerator
{
    private OperationsStatistics operationsStatistics;
    private ReportUtil reportUtil;


    public OperationTableGenerator(
        OperationsStatistics operationsStatistics, ReportUtil reportUtil )
    {
        this.operationsStatistics = operationsStatistics;
        this.reportUtil = reportUtil;
    }


    public ContainerTag generateSourcesTable()
    {
        return div( generateHeader( "Sources: " ), generateSources() );
    }


    public ContainerTag generateNotResolvedOperationsTable(
        Comparator<CurrentOperation> comparator )
    {
        return div( generateHeader( "Not resolved operations list" ),
            generateOperations( this::isNotResolved, comparator ) );
    }


    public ContainerTag generateExpensesOperationsTable( Comparator<CurrentOperation> comparator )
    {
        return div(
            generateHeader( "Expenses list" ), generateOperations( this::isExpense, comparator ) );
    }


    public ContainerTag generateIncomesOperationsTable( Comparator<CurrentOperation> comparator )
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
        Predicate<CurrentOperation> predicate, Comparator<CurrentOperation> comparator )
    {
        ContainerTag table = table().with( generateOperationsTableHeader() );
        sortOperationByComparator( comparator );
        for( CurrentOperation currentOperation : operationsStatistics.getCurrentOperations() )
        {
            if( predicate.test( currentOperation ) )
                table.with( generateContent( currentOperation ) );
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


    private boolean isIncome( CurrentOperation currentOperation )
    {
        return reportUtil.isIncome( currentOperation );
    }


    private boolean isExpense( CurrentOperation currentOperation )
    {
        return reportUtil.isExpense( currentOperation );

    }


    private boolean isNotResolved( CurrentOperation currentOperation )
    {
        return reportUtil.isNotResolved( currentOperation );
    }


    private ContainerTag generateContent( CurrentOperation currentOperation )
    {
        return tr()
            .with( td().with( span( currentOperation.getOperation().getRawOperation().getID() ) ),
                td().with( span(
                    currentOperation.getOperation().getRawOperation().getDate().toString() ) ),
                td().with( span( currentOperation.getOperation().getType().name() ) ),
                td().with( span( currentOperation.getCategory().getCategoryName() ) ), td().with(
                    span( currentOperation.getOperation().getRawOperation().getAmount()
                        .toString() ) ),
                td().with( span( currentOperation.getOperation().getRawOperation().getDesc() ) ) );

    }


    private ContainerTag generateOperationsTableHeader()
    {
        return thead( tr().with( td().with( span( "ID" ) ), td().with( span( "Date" ) ),
            td().with( span( "Operation Type" ) ), td().with( span( "Category" ) ),
            td().with( span( "Amount" ) ), td().with( span( "Description" ) ) ) );
    }


    private void sortOperationByComparator( Comparator<CurrentOperation> comparator )
    {
        operationsStatistics.getCurrentOperations().sort( comparator );
    }


    private Set<String> getSources()
    {
        Set<String> sources = new HashSet<>();
        for( CurrentOperation currentOperation : operationsStatistics.getCurrentOperations() )
        {
            sources.add( currentOperation.getOperation().getRawOperation().getFileName() );
        }
        return sources;
    }
}
