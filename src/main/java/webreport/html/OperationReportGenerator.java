package webreport.html;

import j2html.tags.ContainerTag;
import model.Operation;

import java.util.List;

import static j2html.TagCreator.*;
import static utils.Constants.APP_TITLE;


public class OperationReportGenerator
{
    private OperationTableGenerator tableGenerator;


    public OperationReportGenerator(
        List<Operation> operations, List<String> sources,
        OperationsStatistics operationsStatistics )
    {
        this.tableGenerator =
            new OperationTableGenerator( operations, sources, operationsStatistics );
    }


    public String generateHtml()
    {
        return html(
            generateHead(),
            body(
                    tableGenerator.generateIncomesStatisticsTable(),
                    tableGenerator.generateExpensesStatisticsTable(),
                    tableGenerator.generateNotResolvedStatisticsTable(),

                    tableGenerator.generateIncomesOperationsTable(),
                    tableGenerator.generateExpensesOperationsTable(),
                    tableGenerator.generateNotResolvedOperationsTable(),

                    tableGenerator.generateSourcesTable() ) )
            .render();
    }


    private ContainerTag generateHead()
    {
        return head( title( APP_TITLE ), link().withRel( "stylesheet" ).withHref( "style.css" ) );
    }

}
