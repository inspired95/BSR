package executors;

import exceptions.BankStmtConverterNotFoundException;
import gui.BankStatementChooser;
import model.RawOperation;
import pdfconverters.BankStmtConverter;
import pdfconverters.BankStmtConverterFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.StringJoiner;
import java.util.logging.Logger;

import static reader.PDFReader.read;


public class BankStmtChooserBtnListener
    implements ActionListener
{
    private final static Logger LOGGER =
        Logger.getLogger( BankStmtChooserBtnListener.class.getName() );

    private String chosenBank;
    private JFrame root;

    public BankStmtChooserBtnListener( String chosenBank, JFrame root )
    {
        this.chosenBank = chosenBank;
        this.root = root;
    }


    @Override
    public void actionPerformed( ActionEvent actionEvent )
    {
        drawGlassPane();
        BankStatementChooser bankStatementChooser = new BankStatementChooser();
        int status = bankStatementChooser.showOpenDialog( null );

        if( status == JFileChooser.APPROVE_OPTION ){
            File selectedFile = bankStatementChooser.getSelectedFile();
            LOGGER.info( "Bank of which statement to be converted: " + chosenBank );
            LOGGER.info( "File to be convert: " + selectedFile );
            String bankStmtPdf = "";
            LOGGER.info( "Bank statement reading started" );
            try
            {
                bankStmtPdf= read( selectedFile.getAbsolutePath() );
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

                List<RawOperation> convert = bankStmtConverter.convert( bankStmtPdf );
            }
        }
        updateGlassPaneVisibility( false );
    }

    private void drawGlassPane(){
        root.setGlassPane( new JComponent()
        {
            @Override
            protected void paintComponent( Graphics g )
            {
                g.setColor( new Color( 191,191 ,191 ,150 ) );
                g.fillRect( 0,0,getWidth(), getHeight() );
                super.paintComponent( g );
            }
        } );
        root.getGlassPane().setVisible( true );
    }

    private void updateGlassPaneVisibility( boolean glassPaneVisibility ){
        new Thread( () -> {
            SwingUtilities.invokeLater(
                () -> root.getGlassPane().setVisible( glassPaneVisibility ) );
        } ).start();
    }


}
