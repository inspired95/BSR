package gui;

import model.Operation;
import utils.Util;
import webreport.html.OperationTableGenerator;
import webreport.html.OperationsStatistics;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Set;


public class GenerateReportActionListener
    implements ActionListener
{
    private Set<Operation> allOperations;
    private Set<String> sources;


    public GenerateReportActionListener( Set<Operation> allOperations, Set<String> sources )
    {
        this.allOperations = allOperations;
        this.sources = sources;
    }


    @Override
    public void actionPerformed( ActionEvent e )
    {
        String report = new OperationTableGenerator( new ArrayList<>( allOperations ),
            new ArrayList<>( sources ),
            new OperationsStatistics( new ArrayList<>( allOperations ) ) ).generate();

        Util.writeHtmlReport( report );
    }
}
