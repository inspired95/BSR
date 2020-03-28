package app;

import model.CategoriesConfiguration;
import reader.JsonParser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.logging.Logger;

import static app.DefaultConfigurationCreator.*;
import static java.util.logging.Logger.GLOBAL_LOGGER_NAME;
import static java.util.logging.Logger.getLogger;
import static reader.FileReader.readCategoriesConfigJson;
import static utils.Constants.CATEGORIES_CONFIGURATION_FILE_NAME;
import static utils.Constants.CONFIGURATION_PATH;


public class ConfigurationLoader
{
    private final static Logger LOGGER = getLogger( GLOBAL_LOGGER_NAME );

    private static boolean categoriesConfigurationLoadedSuccessfully = false;


    public static void loadConfiguration( Boolean isFirstConfigurationLoadingAttempt )
    {
        LOGGER.info( "Configuration loading" );

        loadCategoriesConfiguration( isFirstConfigurationLoadingAttempt );
    }


    private static void loadCategoriesConfiguration( Boolean isFirstConfigurationLoadingAttempt )
    {
        LOGGER.info( "Categories configuration loading" );
        if( checkIfCategoriesConfigurationExists() ){
            Optional<String> categoriesConfig = readCategoriesConfigurationJson();
            if( categoriesConfig.isPresent() )
            {
                Optional<CategoriesConfiguration> categoriesConfiguration =
                    parseCategoriesConfigurationJson( categoriesConfig.get() );
                if( categoriesConfiguration.isPresent() )
                {
                    setCategoriesConfiguration( categoriesConfiguration.get() );
                }
            }
            LOGGER.info( "Configuration loading has been finished" );
        }else if( isFirstConfigurationLoadingAttempt ){
            LOGGER.info( "Categories configuration does not exist" );
            createDefaultCategoriesConfiguration();
            loadCategoriesConfiguration( false );
        }else {
            LOGGER.warning( "Categories configuration cannot be loaded" );
        }
    }


    private static void setCategoriesConfiguration(
        CategoriesConfiguration categoriesConfiguration )
    {
        Configuration.setCategoriesConfiguration( categoriesConfiguration );
        categoriesConfigurationLoadedSuccessfully = true;
        LOGGER.info( "Categories configuration loading has been finished" );
    }


    private static Optional<CategoriesConfiguration> parseCategoriesConfigurationJson(
        String categoriesConfig )
    {
        return JsonParser.parseJsonToCategoryConfiguration( categoriesConfig );
    }


    private static Optional<String> readCategoriesConfigurationJson()
    {
        Optional<String> categoriesConfig = Optional.empty();
        try
        {
            categoriesConfig = Optional.of( readCategoriesConfigJson() );
        }
        catch( IOException e )
        {
            LOGGER.warning( "Cannot read categories configuration" );
        }
        return categoriesConfig;
    }


    public static boolean isConfigurationLoadedSuccessfully()
    {
        return categoriesConfigurationLoadedSuccessfully;
    }

    private static boolean checkIfCategoriesConfigurationExists(){
        File categoriesConfigurationFile = new File( Paths.get( CONFIGURATION_PATH,
            CATEGORIES_CONFIGURATION_FILE_NAME ).toString());
        return categoriesConfigurationFile.exists();
    }
}
