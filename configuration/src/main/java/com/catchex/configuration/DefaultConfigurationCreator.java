package com.catchex.configuration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import static com.catchex.util.Constants.CATEGORIES_CONFIGURATION_FILE_NAME;
import static com.catchex.util.Constants.CONFIGURATION_PATH;
import static com.catchex.util.Log.LOGGER;


public class DefaultConfigurationCreator
{

    public static void createDefaultCategoriesConfiguration()
    {
        LOGGER
            .info( "Attempt to create default com.catchex.bankstmt.categories configuration file" );
        try (InputStream defaultConfigurationStream = DefaultConfigurationCreator.class
            .getResourceAsStream( "/" + CATEGORIES_CONFIGURATION_FILE_NAME ))
        {
            createDirectoryIfNeeded();

            File categoriesConfigurationFile =
                new File( CONFIGURATION_PATH, CATEGORIES_CONFIGURATION_FILE_NAME );

            Files.copy( defaultConfigurationStream, categoriesConfigurationFile.toPath(),
                StandardCopyOption.REPLACE_EXISTING );
        }
        catch( IOException e )
        {
            LOGGER.warning(
                "Error during an attempt to create default com.catchex.bankstmt.categories configuration " +
                    "file" );
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
