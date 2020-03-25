package executors;

import exceptions.BankStmtConverterNotFoundException;
import gui.BankStatementChooser;
import pdfconverters.BankStmtConverter;
import pdfconverters.BankStmtConverterFactory;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.StringJoiner;
import java.util.logging.Logger;

import static reader.PDFReader.read;


public class BankStmtChooserBtnListener
    implements ActionListener
{
    private final static Logger LOGGER =
        Logger.getLogger( BankStmtChooserBtnListener.class.getName() );

    private String chosenBank;


    public BankStmtChooserBtnListener( String chosenBank )
    {
        this.chosenBank = chosenBank;
    }


    @Override
    public void actionPerformed( ActionEvent actionEvent )
    {
        BankStatementChooser bankStatementChooser = new BankStatementChooser();
        int status = bankStatementChooser.showOpenDialog( null );
        if( status == JFileChooser.APPROVE_OPTION )
        {
            File selectedFile = bankStatementChooser.getSelectedFile();
            LOGGER.info( "Bank of which statement to be converted: " + chosenBank );
            LOGGER.info( "File to be convert: " + selectedFile );
            String bankStmtPdf = "";
            LOGGER.info( "Bank statement reading started" );
            try
            {
                bankStmtPdf = read( selectedFile.getAbsolutePath() );
            }
            catch( IOException e )
            {
                LOGGER.warning( "Bank statement could't be read" );
                e.printStackTrace();
            }
            LOGGER.info( "Bank statement reading finished" );
            if( !bankStmtPdf.isEmpty() )
            {
                BankStmtConverter bankStmtConverter = null;
                try
                {
                    bankStmtConverter =
                        new BankStmtConverterFactory().match( chosenBank );
                }catch( BankStmtConverterNotFoundException e ){
                    String errorMsg =
                        new StringJoiner( " " ).add( "Cannot match bank statement converter for" )
                            .add( chosenBank ).toString();
                    LOGGER.warning( errorMsg );
                    JOptionPane.showMessageDialog(null, errorMsg, "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                bankStmtConverter.convert( bankStmtPdf );
            }
        }
    }
}
