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
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;
import java.util.Optional;

import static app.Configuration.getCategoriesConfiguration;
import static app.Log.LOGGER;
import static reader.PDFReader.read;
import static utils.Util.showInformation;


public class BankStmtChooserBtnListener
    implements ActionListener
{
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
            File[] selectedFiles = bankStatementChooser.getSelectedFiles();

            for( File selectedFile : selectedFiles )
            {
                if( !root.getSources().contains( selectedFile.getName() ) )
                {
                    handleSelectedBankStmtPdf( selectedFile );
                }
                else
                {
                    LOGGER.info(
                        "The selected file " + selectedFile.getName() + " already " + "handled" );
                    showInformation(
                        "The selected file " + selectedFile.getName() + " already handled" );
                }

            }
        }
        disableGlassPaneVisibility();
    }


    private void handleSelectedBankStmtPdf( File selectedFile )
    {
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
                    root.updateSources( selectedFile.getName() );
                }
            }
        }
        root.updateOperationsList( operations );
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


    private void disableGlassPaneVisibility()
    {
        new Thread(
            () -> SwingUtilities.invokeLater( () -> root.getGlassPane().setVisible( false ) ) )
            .start();
    }

}
