package com.catchex.io.reader;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.Optional;


public class PDFReader
{
    private static final Logger logger = LoggerFactory.getLogger( PDFReader.class );


    private PDFReader()
    {
    }


    public static Optional<String> read( String path )
    {
        logger.error( "Thread {}", Thread.currentThread().getName() );
        String pdfContent = null;

        PDFTextStripper stripper = null;
        try
        {
            stripper = new PDFTextStripper();
        }
        catch( IOException e )
        {
            logger.error( ExceptionUtils.getStackTrace( e ) );
        }
        if( stripper != null )
        {
            try
            {
                PDDocument document = PDDocument.load( new File( path ) );
                if( !document.isEncrypted() )
                {
                    pdfContent = stripper.getText( document );
                    document.close();
                }
                else
                {
                    logger.warn( "File {} is encrypted and cannot be read", path );
                }
            }
            catch( IOException e )
            {
                logger.warn( ExceptionUtils.getStackTrace( e ) );
            }
        }
        return Optional.ofNullable( pdfContent );
    }
}
