package webreport.html;

import j2html.tags.ContainerTag;
import model.Operation;
import operationtype.OperationType;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
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


    public String generate()
    {
        StringBuilder page = new StringBuilder();
        page.append( generateHeader( "Incomes summary" ) );
        page.append( generateStatisticTable( operationsStatistics.getIncomes() ) );
        page.append( generateOperationsSum( operationsStatistics.getIncomeSum() ) );
        page.append( generateHeader( "Expenses summary" ) );
        page.append( generateStatisticTable( operationsStatistics.getExpenses() ) );
        page.append( generateOperationsSum( operationsStatistics.getExpensesSum() ) );
        page.append( generateHeader( "Not resolved operations summary" ) );
        page.append( generateStatisticTable( operationsStatistics.getNotResolvedOperations() ) );
        page.append( generateOperationsSum( operationsStatistics.getNotResolvedSum() ) );
        page.append( generateHeader( "Incomes list" ) );
        page.append( generateOperations( this::isIncome ) );
        page.append( generateHeader( "Expenses list" ) );
        page.append( generateOperations( this::isExpense ) );
        page.append( generateHeader( "Not resolved operations list" ) );
        page.append( generateOperations( this::isNotResolved ) );
        page.append( generateHeader( "Sources: " ) );
        page.append( generateSources() );
        return page.toString();
    }


    private String generateHeader( String text )
    {
        return h1( text ).render();
    }


    private String generateOperationsSum( Double value )
    {
        return h2( "Sum: " + value ).render();
    }


    private String generateStatisticTable( Map<String, Double> map )
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
        return table().with( header ).with( sums ).render();
    }


    private String generateOperations( Predicate<Operation> predicate )
    {
        ContainerTag table = table().with( generateOperationsTableHeader() );
        for( Operation operation : operations )
        {
            if( predicate.test( operation ) )
                table.with( generateContent( operation ) );
        }
        return table.render();
    }


    private String generateSources()
    {
        ContainerTag sourceContainer = ul();
        for( String source : sources )
        {
            sourceContainer.with( li( source ) );
        }
        return sourceContainer.render();
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
        return tr().with( td().with( span( operation.getID() ) ),
            td().with( span( operation.getDate().toString() ) ),
            td().with( span( operation.getType().name() ) ),
            td().with( span( operation.getCategory().getCategoryName() ) ),
            td().with( span( operation.getAmount().toString() ) ) );

    }


    private ContainerTag generateOperationsTableHeader()
    {
        return tr().with( td().with( span( "ID" ) ), td().with( span( "Date" ) ),
            td().with( span( "Operation Type" ) ), td().with( span( "Category" ) ),
            td().with( span( "Amount" ) ) );
    }
}
