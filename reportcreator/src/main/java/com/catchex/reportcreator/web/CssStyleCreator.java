package com.catchex.reportcreator.web;

import com.catchex.logging.Log;
import com.catchex.util.Constants;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;


public class CssStyleCreator
{

    public static void createCssStyle()
    {
        try (InputStream cssStyleStream = CssStyleCreator.class
            .getResourceAsStream( "/" + Constants.CSS_STYLE_FILE_NAME ))
        {
            File cssStyleFile =
                new File( Constants.CONFIGURATION_PATH, Constants.CSS_STYLE_FILE_NAME );

            if( !cssStyleFile.exists() )
            {
                Log.LOGGER.info( "Css style file doesn't exist - creating" );
                createDirectoryIfNeeded();

                Files.copy( cssStyleStream, cssStyleFile.toPath(),
                    StandardCopyOption.REPLACE_EXISTING );
                Log.LOGGER.info( "Css style file created" );
            }
            else
            {
                Log.LOGGER.info( "Css style file exists" );
            }
        }
        catch( IOException e )
        {
            Log.LOGGER.warning( "Error during an attempt to css style file" );
        }
    }


    private static void createDirectoryIfNeeded()
    {
        File bsrConfigurationDirectory = new File( Constants.CONFIGURATION_PATH );
        if( !bsrConfigurationDirectory.exists() )
        {
            bsrConfigurationDirectory.mkdir();
        }
    }
}
