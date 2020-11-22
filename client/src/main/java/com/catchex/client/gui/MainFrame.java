package com.catchex.client.gui;

import com.catchex.models.Operation;
import com.catchex.util.OperationsTableComparatorFactory;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import static com.catchex.util.Constants.*;
import static com.catchex.util.Log.LOGGER;


public class MainFrame
    extends JFrame
{
    private JButton openBankStmtChooserBtn;
    private JComboBox<String> selectBankComboBox;
    private JComboBox<String> selectReportComparatorComboBox;
    private ScrollTable operationsScrollTable;
    private ScrollTable sourcesScrollTable;

    private List<Operation> allOperations = new ArrayList<>();
    private List<String> sources = new ArrayList<>();


    public MainFrame()
    {
        setup();

        JPanel left = new JPanel();
        left.setLayout( new BoxLayout( left, BoxLayout.Y_AXIS ) );

        JPanel right = new JPanel();
        right.setLayout( new BoxLayout( right, BoxLayout.Y_AXIS ) );

        drawBankSelectorComboBox( left );
        drawBankStmtChooserButton( left );
        drawOperationsTable( left );
        drawReportComparatorComboBox( left );

        drawSources( right );
        drawGenerateReportButton( right );

        add( left );
        add( right );
        pack();
        LOGGER.info( "The main frame loaded" );
    }


    private void setup()
    {
        setTitle( APP_TITLE );
        setVisible( true );
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        setLayout( new FlowLayout() );
    }


    private void drawOperationsTable( JPanel panel )
    {
        operationsScrollTable =
            new ScrollTable( OPERATIONS, 800, 300, DATE, TYPE, CATEGORY, AMOUNT );
        panel.add( operationsScrollTable );
    }


    private void drawBankStmtChooserButton( JPanel panel )
    {
        openBankStmtChooserBtn = new JButton( SELECT_BANK_STATEMENT_TXT );
        openBankStmtChooserBtn.addActionListener(
            new BankStmtChooserBtnListener( (String)selectBankComboBox.getSelectedItem(), this ) );
        openBankStmtChooserBtn.setAlignmentX( Component.CENTER_ALIGNMENT );
        panel.add( openBankStmtChooserBtn );
    }


    private void drawGenerateReportButton( JPanel panel )
    {
        openBankStmtChooserBtn = new JButton( GENERATE_REPORT );
        openBankStmtChooserBtn.addActionListener(
            new GenerateReportActionListener( allOperations, sources,
                selectReportComparatorComboBox ) );
        panel.add( openBankStmtChooserBtn );
    }


    private void drawBankSelectorComboBox( JPanel panel )
    {
        JLabel selectBankLbl = new JLabel( SELECT_BANK_TXT );
        selectBankLbl.setAlignmentX( Component.CENTER_ALIGNMENT );
        panel.add( selectBankLbl );
        selectBankComboBox = new JComboBox<>( new String[] { PKO } );
        panel.add( selectBankComboBox );
    }


    private void drawReportComparatorComboBox( JPanel panel )
    {
        JLabel selectReportComparatorLbl = new JLabel( OPERATIONS_LIST_SORTING_BY );
        selectReportComparatorLbl.setAlignmentX( Component.CENTER_ALIGNMENT );
        panel.add( selectReportComparatorLbl );
        selectReportComparatorComboBox =
            new JComboBox<>( new String[] { DATE, AMOUNT, TYPE, CATEGORY } );
        selectReportComparatorComboBox.addActionListener( e -> {
            sortOperations();
            updateOperationsTable();
        } );
        panel.add( selectReportComparatorComboBox );
    }


    public void drawSources( JPanel panel )
    {
        sourcesScrollTable = new ScrollTable( SOURCES, 100, 100, SOURCE );
        panel.add( sourcesScrollTable );
    }


    public void updateOperationsList( List<Operation> operations )
    {
        allOperations.addAll( operations );
        sortOperations();
        updateOperationsTable();
    }


    private void sortOperations()
    {
        String selectedComparator =
            Objects.requireNonNull( selectReportComparatorComboBox.getSelectedItem() ).toString();
        Comparator<Operation> comparator =
            OperationsTableComparatorFactory.get( selectedComparator );
        allOperations.sort( comparator );
    }


    public void updateOperationsTable()
    {
        DefaultTableModel model = operationsScrollTable.getModel();
        model.setRowCount( 0 );
        for( Operation operation : allOperations )
        {
            model.addRow( new Object[] { operation.getRawOperation().getDate(), operation.getType(),
                                         operation.getCategory().getCategoryName(),
                                         operation.getRawOperation().getAmount() } );
        }
        pack();
    }


    public void updateSources( String selectedFile )
    {
        sources.add( selectedFile );
        sources.sort( Comparator.comparing( String::toString ) );
        updateSourcesTable();
    }


    public void updateSourcesTable()
    {
        DefaultTableModel tableModel = sourcesScrollTable.getModel();
        tableModel.setRowCount( 0 );
        for( String source : sources )
        {
            tableModel.addRow( new Object[] { source } );
        }
    }


    public List<String> getSources()
    {
        return sources;
    }

}

