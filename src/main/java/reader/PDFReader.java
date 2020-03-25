package reader;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;

import java.io.File;
import java.io.IOException;


public class PDFReader
{
    public static String read( String path ) throws IOException
    {
        try (PDDocument document = PDDocument.load( new File( path ) ))
        {

            document.getClass();

            if( !document.isEncrypted() )
            {

                PDFTextStripperByArea stripper = new PDFTextStripperByArea();
                stripper.setSortByPosition( true );

                PDFTextStripper tStripper = new PDFTextStripper();

                return tStripper.getText( document );
            }
        }
        throw new IOException();
    }
}
