package app;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.logging.Logger;

import static java.util.logging.Logger.GLOBAL_LOGGER_NAME;
import static java.util.logging.Logger.getLogger;
import static utils.Constants.CATEGORIES_CONFIGURATION_FILE_NAME;
import static utils.Constants.CONFIGURATION_PATH;


public class DefaultConfigurationCreator
{
    private final static Logger LOGGER = getLogger( GLOBAL_LOGGER_NAME );


    public static void createDefaultCategoriesConfiguration()
    {
        LOGGER.info( "Attempt to create default categories configuration file" );
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
                "Error during an attempt to create default categories configuration " + "file" );
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
