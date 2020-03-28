package gui;

import model.Operation;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
    private JTable table;

    private Set<Operation> allOperations;


    public MainFrame()
    {
        allOperations = new HashSet<>();
        setup();
        drawTable();
        drawBankSelectorComboBox();
        drawBankStmtChooserButton();
        pack();
        LOGGER.info( "app.Main frame loaded" );
    }


    private void drawTable()
    {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn( "Type" );
        model.addColumn( "Category" );
        model.addColumn( "Amount" );
        table = new JTable( model );

        table.setBounds( 30, 40, 200, 300 );
        JScrollPane sp = new JScrollPane( table );
        add( sp );
    }


    private void setup()
    {
        setTitle( APP_TITLE );
        setVisible( true );
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        setLayout( new FlowLayout() );
    }


    private void drawBankStmtChooserButton()
    {
        openBankStmtChooserBtn = new JButton( SELECT_BANK_STATEMENT_TXT );
        openBankStmtChooserBtn.addActionListener(
            new BankStmtChooserBtnListener( (String)selectBankComboBox.getSelectedItem(), this ) );
        add( openBankStmtChooserBtn );
    }


    private void drawBankSelectorComboBox()
    {
        selectBankLbl = new JLabel( SELECT_BANK_TXT );
        add( selectBankLbl );
        selectBankComboBox = new JComboBox( new String[] { PKO } );
        add( selectBankComboBox );
    }


    public JTable getTable()
    {
        return table;
    }


    public void updateResults( List<Operation> operations )
    {
        DefaultTableModel tableModel = (DefaultTableModel)table.getModel();
        List<Operation> toAppendInTable = new ArrayList<>();
        for( Operation operation : operations )
        {
            boolean added = allOperations.add( operation );
            if( added ) toAppendInTable.add( operation );
        }

        for( Operation operation : toAppendInTable )
        {
            tableModel.addRow(
                new Object[] { operation.getType(), operation.getCategory().getCategoryName(),
                               operation.getAmount() } );
        }
        pack();
    }

}

