package app;

import model.CategoriesConfiguration;
import reader.JsonParser;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

import static java.util.logging.Logger.GLOBAL_LOGGER_NAME;
import static java.util.logging.Logger.getLogger;
import static reader.FileReader.readCategoriesConfigJson;


public class ConfigurationLoader
{
    private final static Logger LOGGER = getLogger( GLOBAL_LOGGER_NAME );

    private static boolean categoriesConfigurationLoadedSuccessfully = false;


    public static void loadConfiguration()
    {
        LOGGER.info( "Configuration loading" );

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
        LOGGER.info( "Categories configuration loading" );
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
}
