package gui;

import executors.BankStmtChooserBtnListener;

import javax.swing.*;
import java.awt.*;
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


    public MainFrame()
    {
        setup();
        drawBankSelectorComboBox();
        drawBankStmtChooserButton();
        LOGGER.info( "app.Main frame loaded" );
    }


    private void setup()
    {
        setTitle( APP_TITLE );
        setVisible( true );
        setSize( 400, 200 );
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


}

