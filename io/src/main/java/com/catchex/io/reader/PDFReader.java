package com.catchex.io.reader;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import static com.catchex.util.Log.LOGGER;


public class PDFReader
{

    public static Optional<String> read( String path )
    {
        try
        {
            PDDocument document = PDDocument.load( new File( path ) );
            if( !document.isEncrypted() )
            {
                PDFTextStripperByArea stripper = new PDFTextStripperByArea();
                stripper.setSortByPosition( true );

                PDFTextStripper tStripper = new PDFTextStripper();
                String stripperText = tStripper.getText( document );
                document.close();
                return Optional.of( stripperText );
            }
        }
        catch( IOException e )
        {
            LOGGER.warning( "PDF cannot be read. Reason: " + e.getCause() );
        }
        return Optional.empty();
    }
}
