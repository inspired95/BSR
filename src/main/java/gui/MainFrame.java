package gui;

import model.Operation;
import utils.OperationsTableComparatorFactory;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

import static java.util.logging.Logger.GLOBAL_LOGGER_NAME;
import static java.util.logging.Logger.getLogger;
import static utils.Constants.*;


public class MainFrame
    extends JFrame
{
    private final static Logger LOGGER = getLogger( GLOBAL_LOGGER_NAME );

    private JButton openBankStmtChooserBtn;
    private JComboBox<String> selectBankComboBox;
    private JComboBox<String> selectReportComparatorComboBox;
    private JTable operationsTable;
    private JTable sourcesTable;

    private List<Operation> allOperations = new ArrayList<>();
    private List<String> sources = new ArrayList<>();


    public MainFrame()
    {
        setup();
        drawOperationsTable();
        drawBankSelectorComboBox();
        drawReportComparatorComboBox();
        drawBankStmtChooserButton();
        drawGenerateReportButton();
        drawSources();
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


    private void drawOperationsTable()
    {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn( DATE );
        model.addColumn( TYPE );
        model.addColumn( CATEGORY );
        model.addColumn( AMOUNT );
        operationsTable = new JTable( model );
        operationsTable.setAutoCreateRowSorter( true );
        operationsTable.setBounds( 30, 40, 200, 300 );
        operationsTable.setToolTipText( OPERATIONS );
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
        openBankStmtChooserBtn = new JButton( GENERATE_REPORT );
        openBankStmtChooserBtn.addActionListener(
            new GenerateReportActionListener( allOperations, sources,
                selectReportComparatorComboBox ) );
        add( openBankStmtChooserBtn );
    }


    private void drawBankSelectorComboBox()
    {
        JLabel selectBankLbl = new JLabel( SELECT_BANK_TXT );
        add( selectBankLbl );
        selectBankComboBox = new JComboBox<>( new String[] { PKO } );
        add( selectBankComboBox );
    }


    private void drawReportComparatorComboBox()
    {
        JLabel selectReportComparatorLbl = new JLabel( OPERATIONS_LIST_SORTING_BY );
        add( selectReportComparatorLbl );
        selectReportComparatorComboBox =
            new JComboBox<>( new String[] { DATE, AMOUNT, TYPE, CATEGORY } );
        selectReportComparatorComboBox.addActionListener( e -> {
            sortOperations();
            updateOperationsTable();
        } );
        add( selectReportComparatorComboBox );
    }


    public void drawSources()
    {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn( SOURCE );
        sourcesTable = new JTable( model );
        sourcesTable.setAutoCreateRowSorter( true );
        sourcesTable.setBounds( 30, 40, 200, 100 );
        sourcesTable.setToolTipText( SOURCES );
        JScrollPane sp = new JScrollPane( sourcesTable );
        add( sp );
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
        DefaultTableModel tableModel = (DefaultTableModel)operationsTable.getModel();
        tableModel.setRowCount( 0 );
        for( Operation operation : allOperations )
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
        sources.sort( Comparator.comparing( String::toString ) );
        updateSourcesTable();
    }


    public void updateSourcesTable()
    {
        DefaultTableModel tableModel = (DefaultTableModel)sourcesTable.getModel();
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

