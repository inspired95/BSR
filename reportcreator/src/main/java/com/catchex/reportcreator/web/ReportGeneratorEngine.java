package com.catchex.reportcreator.web;

import com.catchex.models.CurrentOperation;
import com.catchex.reportcreator.ReportUtil;
import com.catchex.reportcreator.statictics.OperationsStatistics;
import j2html.tags.ContainerTag;

import java.util.Comparator;

import static com.catchex.util.Constants.APP_TITLE;
import static j2html.TagCreator.*;


public class ReportGeneratorEngine
{
    private OperationTableGenerator tableGenerator;
    private OperationChartGenerator chartGenerator;


    public ReportGeneratorEngine( OperationsStatistics operationsStatistics, ReportUtil reportUtil )
    {
        this.tableGenerator = new OperationTableGenerator( operationsStatistics, reportUtil );
        this.chartGenerator = new OperationChartGenerator( operationsStatistics );
    }


    public String generateHtml( Comparator<CurrentOperation> operationTableComparator )
    {
        return html( generateHead(), body(
            tableGenerator.generateIncomesStatisticsTable(),
            tableGenerator.generateExpensesStatisticsTable()
                .with( chartGenerator.generateCanvas( "expensesSummary", "smallChart" ) ).with(
                chartGenerator.generateCanvas( "CategoriesExpensesPerMonthChart", "chart" ) ),
            tableGenerator.generateNotResolvedStatisticsTable(),
            chartGenerator.generateExpensesSummaryChartScript( "expensesSummary" ), chartGenerator
                .generateCategoriesExpensesPerMonthChartScript( "CategoriesExpensesPerMonthChart" ),
            tableGenerator.generateIncomesOperationsTable( operationTableComparator ),
            tableGenerator.generateExpensesOperationsTable( operationTableComparator ),
            tableGenerator.generateNotResolvedOperationsTable( operationTableComparator ),

            tableGenerator.generateSourcesTable() ) ).render();
    }


    private ContainerTag generateHead()
    {
        return head( title( APP_TITLE ), script()
                .withSrc( "https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.5.0/Chart.min.js" ),
            link().withRel( "stylesheet" ).withHref( "style.css" ) );
    }

}
