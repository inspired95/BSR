package com.catchex.reportcreator;

import com.catchex.io.writer.ReportWriter;
import com.catchex.models.CurrentOperation;
import com.catchex.reportcreator.statictics.OperationsStatistics;
import com.catchex.reportcreator.web.CssStyleCreator;
import com.catchex.reportcreator.web.OperationsTableComparatorFactory;
import com.catchex.reportcreator.web.ReportGeneratorEngine;
import com.catchex.util.Constants;
import guihelpers.Alerts;
import javafx.scene.control.Alert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;
import java.util.Set;

import static com.catchex.util.Constants.*;


public class ReportGeneratorController
{
    private static final Logger logger = LoggerFactory.getLogger( ReportGeneratorController.class );

    private Set<CurrentOperation> currentOperations;


    public ReportGeneratorController( Set<CurrentOperation> currentOperations )
    {
        this.currentOperations = currentOperations;
    }


    public void generate()
    {
        ReportUtil reportUtil = new ReportUtil();
        OperationsStatistics operationsStatistics =
            new OperationsStatistics( new ArrayList<>( currentOperations ), reportUtil );

        ReportGeneratorEngine reportGenerator =
            new ReportGeneratorEngine( operationsStatistics, reportUtil );

        Comparator<CurrentOperation> currentOperationComparator =
            OperationsTableComparatorFactory.get( Objects.requireNonNull( Constants.AMOUNT ) );

        String report = reportGenerator.generateHtml( currentOperationComparator );

        Path path = Paths.get( CONFIGURATION_PATH, REPORT + LocalDate.now() + HTML_EXTENSION );

        CssStyleCreator.createCssStyle();

        if( ReportWriter.getInstance().writeToFile( report, path ) )
        {
            Alerts
                .showAlert( Alert.AlertType.INFORMATION, "Report generator - saving", "Successful",
                    "Report has been saved in:\n" + path );
            logger.info( "Report has been saved successfully in: {}", path );
        }
        else
        {
            Alerts.showAlert( Alert.AlertType.ERROR, "Report generator - saving",
                "Report cannot " + "be saved", "Check logs" );
            logger.warn( "Report has not been saved successfully" );
        }

    }
}
