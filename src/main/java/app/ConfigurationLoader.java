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

    private static boolean configurationLoadedSuccessfully = false;

    public static void loadConfiguration(){
        LOGGER.info( "Configuration loading" );

        Optional<String> categoriesConfig = readCategoriesFConfigurationJson();
        Optional<CategoriesConfiguration> categoriesConfiguration =
            parseCategoriesConfigurationJson( categoriesConfig );
        setCategoriesConfiguration( categoriesConfiguration );

        LOGGER.info( "Configuration loading has been finished" );
    }


    private static void setCategoriesConfiguration(
        Optional<CategoriesConfiguration> categoriesConfiguration )
    {
        if( categoriesConfiguration.isPresent() ){
            Configuration.setCategoriesConfiguration( categoriesConfiguration.get() );
            configurationLoadedSuccessfully = true;
            LOGGER.info( "Categories configuration loading has been finished" );
        }
    }


    private static Optional<CategoriesConfiguration> parseCategoriesConfigurationJson(
        Optional<String> categoriesConfig )
    {
        Optional<CategoriesConfiguration> categoriesConfiguration = Optional.empty();
        if( categoriesConfig.isPresent() ){
            categoriesConfiguration =
                JsonParser.parseJsonToCategoryConfiguration( categoriesConfig );
        }
        return categoriesConfiguration;
    }


    private static Optional<String> readCategoriesFConfigurationJson()
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
        return configurationLoadedSuccessfully;
    }
}
