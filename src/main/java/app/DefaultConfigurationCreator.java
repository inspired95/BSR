package app;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
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
        try (InputStream resourceAsStream = DefaultConfigurationCreator.class
            .getResourceAsStream( "/defaultCategoryConfiguration.json" )) {

            File file = new File( Paths.get( CONFIGURATION_PATH,
                CATEGORIES_CONFIGURATION_FILE_NAME ).toString());

            FileUtils.copyInputStreamToFile(resourceAsStream, file);
        }
        catch( IOException e )
        {
            LOGGER.warning( "Error during an attempt to create default categories configuration " +
                "file" );
        }
    }
}
