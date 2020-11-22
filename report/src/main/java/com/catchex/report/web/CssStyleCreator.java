package com.catchex.report.web;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import static com.catchex.util.Constants.CONFIGURATION_PATH;
import static com.catchex.util.Constants.CSS_STYLE_FILE_NAME;
import static com.catchex.util.Log.LOGGER;


public class CssStyleCreator
{

    public static void createCssStyle()
    {
        try (InputStream cssStyleStream = CssStyleCreator.class
            .getResourceAsStream( "/" + CSS_STYLE_FILE_NAME ))
        {
            File cssStyleFile = new File( CONFIGURATION_PATH, CSS_STYLE_FILE_NAME );

            if( !cssStyleFile.exists() )
            {
                LOGGER.info( "Css style file doesn't exist - creating" );
                createDirectoryIfNeeded();

                Files.copy( cssStyleStream, cssStyleFile.toPath(),
                    StandardCopyOption.REPLACE_EXISTING );
                LOGGER.info( "Css style file created" );
            }
            else
            {
                LOGGER.info( "Css style file exists" );
            }
        }
        catch( IOException e )
        {
            LOGGER.warning( "Error during an attempt to css style file" );
        }
    }


    private static void createDirectoryIfNeeded()
    {
        File bsrConfigurationDirectory = new File( CONFIGURATION_PATH );
        if( !bsrConfigurationDirectory.exists() )
        {
            bsrConfigurationDirectory.mkdir();
        }
    }
}
