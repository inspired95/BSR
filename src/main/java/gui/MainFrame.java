package gui;

import model.Operation;
import utils.OperationsTableComparatorFactory;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
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

        JPanel left = new JPanel();
        left.setLayout( new BoxLayout( left, BoxLayout.Y_AXIS ) );

        JPanel right = new JPanel();
        right.setLayout( new BoxLayout( right, BoxLayout.Y_AXIS ) );

        drawBankSelectorComboBox( left );
        drawBankStmtChooserButton( left );
        drawOperationsTable( left );

        drawReportComparatorComboBox( right );
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
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn( DATE );
        model.addColumn( TYPE );
        model.addColumn( CATEGORY );
        model.addColumn( AMOUNT );
        operationsTable = new JTable( model );
        operationsTable.setAutoCreateRowSorter( true );
        operationsTable.setToolTipText( OPERATIONS );

        DefaultTableCellRenderer tableCellRendererCenter = getDefaultTableCellRendererCenter();
        for( int i = 0; i < operationsTable.getColumnCount(); i++ )
        {
            TableColumn columnSource = operationsTable.getColumnModel().getColumn( i );
            columnSource.setCellRenderer( tableCellRendererCenter );
        }

        JScrollPane sp = new JScrollPane( operationsTable );
        panel.add( sp );
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
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn( SOURCE );
        sourcesTable = new JTable( model );
        sourcesTable.setAutoCreateRowSorter( true );
        sourcesTable.setBounds( 30, 40, 200, 100 );
        sourcesTable.setToolTipText( SOURCES );

        TableColumn columnSource = sourcesTable.getColumnModel().getColumn( 0 );
        columnSource.setCellRenderer( getDefaultTableCellRendererCenter() );

        JScrollPane sp = new JScrollPane( sourcesTable );
        panel.add( sp );
    }


    public DefaultTableCellRenderer getDefaultTableCellRendererCenter()
    {
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( SwingConstants.CENTER );
        return centerRenderer;
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
            tableModel.addRow( new Object[] { operation.getRawOperation().getDate(),
                                              operation.getType(),
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

