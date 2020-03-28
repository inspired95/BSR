package gui;

import categories.OperationCategoryResolverImpl;
import model.Operation;
import model.RawOperation;
import operationtype.OperationTypeResolver;
import operationtype.OperationTypeResolverFactory;
import pdfconverters.BankStmtConverter;
import pdfconverters.BankStmtConverterFactory;
import transformators.OperationTransformer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import static app.Configuration.getCategoriesConfiguration;
import static java.util.logging.Logger.GLOBAL_LOGGER_NAME;
import static java.util.logging.Logger.getLogger;
import static reader.PDFReader.read;


public class BankStmtChooserBtnListener
    implements ActionListener
{
    private final static Logger LOGGER = getLogger( GLOBAL_LOGGER_NAME );

    private String chosenBank;
    private MainFrame root;

    private List<Operation> operations;


    public BankStmtChooserBtnListener( String chosenBank, JFrame root )
    {
        this.chosenBank = chosenBank;
        this.root = (MainFrame)root;
    }


    @Override
    public void actionPerformed( ActionEvent actionEvent )
    {
        drawGlassPane();
        BankStatementChooser bankStatementChooser = new BankStatementChooser();
        int status = bankStatementChooser.showOpenDialog( null );

        if( status == JFileChooser.APPROVE_OPTION )
        {
            File selectedFile = bankStatementChooser.getSelectedFile();

            LOGGER.info( "Bank of which statement to be converted: " + chosenBank );
            LOGGER.info( "File to be convert: " + selectedFile );
            LOGGER.info( "Bank statement reading started" );

            Optional<String> bankStmtPdf = read( selectedFile.getAbsolutePath() );

            LOGGER.info( "Bank statement reading finished" );

            if( bankStmtPdf.isPresent() )
            {
                Optional<BankStmtConverter> bankStmtConverter =
                    new BankStmtConverterFactory().match( chosenBank );
                if( bankStmtConverter.isPresent() )
                {
                    List<RawOperation> rawOperations =
                        bankStmtConverter.get().convert( bankStmtPdf.get() );

                    Optional<OperationTypeResolver> operationTypeResolver =
                        new OperationTypeResolverFactory().match( chosenBank );

                    if( !rawOperations.isEmpty() && operationTypeResolver.isPresent() )
                    {
                        OperationTransformer transformer = new OperationTransformer(
                            operationTypeResolver.get(), new OperationCategoryResolverImpl(
                            getCategoriesConfiguration().getCategories() ) );
                        this.operations = transformer.transform( rawOperations );
                    }
                }
            }
            root.updateResults( operations );
        }
        updateGlassPaneVisibility( false );
    }


    private void drawGlassPane()
    {
        root.setGlassPane( new JComponent()
        {
            @Override
            protected void paintComponent( Graphics g )
            {
                g.setColor( new Color( 191, 191, 191, 150 ) );
                g.fillRect( 0, 0, getWidth(), getHeight() );
                super.paintComponent( g );
            }
        } );
        root.getGlassPane().setVisible( true );
    }


    private void updateGlassPaneVisibility( boolean glassPaneVisibility )
    {
        new Thread( () -> SwingUtilities
            .invokeLater( () -> root.getGlassPane().setVisible( glassPaneVisibility ) ) ).start();
    }
}
