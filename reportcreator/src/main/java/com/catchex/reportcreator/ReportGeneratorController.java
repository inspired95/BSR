package com.catchex.reportcreator;

import GuiHelpers.Alerts;
import com.catchex.io.writer.ReportWriter;
import com.catchex.models.Operation;
import com.catchex.reportcreator.statictics.OperationsStatistics;
import com.catchex.reportcreator.web.CssStyleCreator;
import com.catchex.reportcreator.web.OperationsTableComparatorFactory;
import com.catchex.reportcreator.web.ReportGeneratorEngine;
import com.catchex.util.Constants;
import javafx.scene.control.Alert;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;
import java.util.Set;

import static com.catchex.logging.Log.LOGGER;
import static com.catchex.util.Constants.*;


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

        Comparator<Operation> operationComparator =
            OperationsTableComparatorFactory.get( Objects.requireNonNull( Constants.AMOUNT ) );

        String report = reportGenerator.generateHtml( operationComparator );

        Path path = Paths.get( CONFIGURATION_PATH, REPORT + LocalDate.now() + HTML_EXTENSION );

        CssStyleCreator.createCssStyle();

        if( ReportWriter.getInstance().writeToFile( report, path ) )
        {
            Alerts
                .showAlert( Alert.AlertType.INFORMATION, "Report generator - saving", "Successful",
                    "Report has been saved in:\n" + path );
            LOGGER.info( "Report has been saved successfully in:" + path );
        }
        else
        {
            Alerts.showAlert( Alert.AlertType.ERROR, "Report generator - saving",
                "Report cannot " + "be saved", "Check logs" );
        }

    }
}
