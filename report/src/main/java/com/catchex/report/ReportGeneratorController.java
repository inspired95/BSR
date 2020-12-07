package com.catchex.report;

import com.catchex.models.Operation;
import com.catchex.report.statictics.OperationsStatistics;
import com.catchex.report.web.CssStyleCreator;
import com.catchex.report.web.ReportGeneratorEngine;
import com.catchex.util.Constants;
import com.catchex.util.OperationsTableComparatorFactory;
import com.catchex.util.Util;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;
import java.util.Set;

import static com.catchex.util.Constants.*;
import static com.catchex.util.Log.LOGGER;
import static com.catchex.util.Util.showError;
import static com.catchex.util.Util.showInformation;


public class ReportGeneratorController
{
    private Set<Operation> operations;


    public ReportGeneratorController( Set<Operation> operations )
    {
        this.operations = operations;
    }


    public void generate()
    {
        OperationsStatistics operationsStatistics =
            new OperationsStatistics( new ArrayList<>( operations ) );

        ReportGeneratorEngine reportGenerator = new ReportGeneratorEngine( operationsStatistics );

        Comparator<Operation> operationComparator = OperationsTableComparatorFactory
            .get( (String)Objects.requireNonNull( Constants.AMOUNT ) );

        String report = reportGenerator.generateHtml( operationComparator );

        Path path = Paths.get( CONFIGURATION_PATH, REPORT + LocalDate.now() + HTML_EXTENSION );

        CssStyleCreator.createCssStyle();

        try
        {
            Util.writeToFile( report, path );
            showInformation( "Report has been saved in:\n" + path );
            LOGGER.info( "Report has been saved in:" + path );
        }
        catch( IOException ex )
        {
            LOGGER.warning( "Cannot write HTML report" );
            showError( "Cannot write HTML report" );
        }

    }
}
