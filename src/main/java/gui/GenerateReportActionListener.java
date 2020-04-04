package gui;

import model.Operation;
import utils.OperationsTableComparatorFactory;
import utils.Util;
import webreport.html.CssStyleCreator;
import webreport.html.OperationReportGenerator;
import webreport.html.OperationsStatistics;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

import static java.util.logging.Logger.GLOBAL_LOGGER_NAME;
import static java.util.logging.Logger.getLogger;
import static utils.Constants.*;
import static utils.Util.showError;
import static utils.Util.showInformation;


public class GenerateReportActionListener
    implements ActionListener
{
    private final static Logger LOGGER = getLogger( GLOBAL_LOGGER_NAME );

    private List<Operation> allOperations;
    private List<String> sources;
    private JComboBox<String> operationsTableSortingBy;


    public GenerateReportActionListener(
        List<Operation> allOperations, List<String> sources,
        JComboBox<String> operationsTableSortingBy )
    {
        this.allOperations = allOperations;
        this.sources = sources;
        this.operationsTableSortingBy = operationsTableSortingBy;
    }


    @Override
    public void actionPerformed( ActionEvent e )
    {
        LOGGER.info( "Report saving started" );
        if( allOperations.isEmpty() || sources.isEmpty() )
        {
            LOGGER.warning( "There is no data to save" );
            showError( "There is no data to save" );
            return;
        }

        OperationsStatistics operationsStatistics =
            new OperationsStatistics( new ArrayList<>( allOperations ) );

        OperationReportGenerator reportGenerator =
            new OperationReportGenerator( new ArrayList<>( allOperations ),
                new ArrayList<>( sources ), operationsStatistics );

        Comparator<Operation> operationComparator = OperationsTableComparatorFactory
            .get( (String)Objects.requireNonNull( operationsTableSortingBy.getSelectedItem() ) );

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
