package gui;

import model.Operation;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.logging.Logger;

import static java.util.logging.Logger.GLOBAL_LOGGER_NAME;
import static java.util.logging.Logger.getLogger;
import static utils.Constants.*;


public class MainFrame
    extends JFrame
{
    private final static Logger LOGGER = getLogger( GLOBAL_LOGGER_NAME );

    private JButton openBankStmtChooserBtn;
    private JLabel selectBankLbl;
    private JComboBox selectBankComboBox;
    private JTable operationsTable;
    private JTable sourcesTable;

    private Set<Operation> allOperations;
    private Set<String> sources;


    public MainFrame()
    {
        allOperations = new HashSet<>();
        sources = new HashSet<>();
        setup();
        drawOperationsTable();
        drawBankSelectorComboBox();
        drawBankStmtChooserButton();
        drawGenerateReportButton();
        drawSources();
        pack();
        LOGGER.info( "app.Main frame loaded" );
    }


    private void setup()
    {
        setTitle( APP_TITLE );
        setVisible( true );
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        setLayout( new FlowLayout() );
    }


    private void drawOperationsTable()
    {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn( "Date" );
        model.addColumn( "Type" );
        model.addColumn( "Category" );
        model.addColumn( "Amount" );
        operationsTable = new JTable( model );
        operationsTable.setAutoCreateRowSorter( true );
        operationsTable.setBounds( 30, 40, 200, 300 );
        operationsTable.setToolTipText( "Operations" );
        JScrollPane sp = new JScrollPane( operationsTable );
        add( sp );
    }


    private void drawBankStmtChooserButton()
    {
        openBankStmtChooserBtn = new JButton( SELECT_BANK_STATEMENT_TXT );
        openBankStmtChooserBtn.addActionListener(
            new BankStmtChooserBtnListener( (String)selectBankComboBox.getSelectedItem(), this ) );
        add( openBankStmtChooserBtn );
    }


    private void drawGenerateReportButton()
    {
        openBankStmtChooserBtn = new JButton( "Generate report" );
        openBankStmtChooserBtn
            .addActionListener( new GenerateReportActionListener( allOperations, sources ) );
        add( openBankStmtChooserBtn );
    }


    private void drawBankSelectorComboBox()
    {
        selectBankLbl = new JLabel( SELECT_BANK_TXT );
        add( selectBankLbl );
        selectBankComboBox = new JComboBox( new String[] { PKO } );
        add( selectBankComboBox );
    }


    public void drawSources()
    {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn( "Source" );
        sourcesTable = new JTable( model );
        sourcesTable.setAutoCreateRowSorter( true );
        sourcesTable.setBounds( 30, 40, 200, 100 );
        sourcesTable.setToolTipText( "Sources" );
        JScrollPane sp = new JScrollPane( sourcesTable );
        add( sp );
    }


    public void updateResults( List<Operation> operations )
    {
        DefaultTableModel tableModel = (DefaultTableModel)operationsTable.getModel();
        List<Operation> toAppendInTable = new ArrayList<>();
        for( Operation operation : operations )
        {
            boolean added = allOperations.add( operation );
            if( added )
                toAppendInTable.add( operation );
        }

        for( Operation operation : toAppendInTable )
        {
            tableModel.addRow( new Object[] { operation.getDate(), operation.getType(),
                                              operation.getCategory().getCategoryName(),
                                              operation.getAmount() } );
        }
        pack();
    }


    public void updateSources( String selectedFile )
    {
        sources.add( selectedFile );

        DefaultTableModel tableModel = (DefaultTableModel)sourcesTable.getModel();
        tableModel.setRowCount( 0 );
        for( String source : sources )
        {
            tableModel.addRow( new Object[] { source } );
        }
    }

}

