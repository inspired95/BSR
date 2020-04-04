package webreport.html;

import j2html.tags.ContainerTag;
import model.Operation;
import operationtype.OperationType;

import java.util.*;
import java.util.function.Predicate;

import static j2html.TagCreator.*;
import static operationtype.OperationType.*;


public class OperationTableGenerator
{
    private List<Operation> operations;
    private List<String> sources;
    private OperationsStatistics operationsStatistics;


    public OperationTableGenerator(
        List<Operation> operations, List<String> sources,
        OperationsStatistics operationsStatistics )
    {
        this.operations = operations;
        this.sources = sources;
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
        return div( generateHeader( "Expenses list" ), generateOperations( this::isExpense, comparator ) );
    }


    public ContainerTag generateIncomesOperationsTable( Comparator<Operation> comparator )
    {
        return div( generateHeader( "Incomes list" ), generateOperations( this::isIncome, comparator ) );
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
        return h2( attrs( cssClass ), "Sum: " + String.format( "%.2f", value) );
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


    private ContainerTag generateOperations( Predicate<Operation> predicate,
                                             Comparator<Operation> comparator )
    {
        ContainerTag table = table().with( generateOperationsTableHeader() );
        sortOperationByComparator(comparator);
        for( Operation operation : operations )
        {
            if( predicate.test( operation ) )
                table.with( generateContent( operation ) );
        }
        return table;
    }


    private ContainerTag generateSources()
    {
        ContainerTag sourceContainer = ul();
        Collections.sort( sources, Comparator.comparing( String::toString ) );
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
            td().with( span( operation.getRawOperation().getAmount().toString() ) ) );

    }


    private ContainerTag generateOperationsTableHeader()
    {
        return thead(tr().with( td().with( span( "ID" ) ), td().with( span( "Date" ) ),
            td().with( span( "Operation Type" ) ), td().with( span( "Category" ) ),
            td().with( span( "Amount" ) ) ));
    }

    private void sortOperationByComparator( Comparator<Operation> comparator ){
        Collections.sort( operations, comparator );
    }
}
