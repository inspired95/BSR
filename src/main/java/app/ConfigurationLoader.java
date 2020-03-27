package app;

import model.CategoriesConfiguration;
import reader.FileReader;
import reader.JsonParser;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

import static java.util.logging.Logger.GLOBAL_LOGGER_NAME;
import static java.util.logging.Logger.getLogger;


public class ConfigurationLoader
{
    private final static Logger LOGGER = getLogger( GLOBAL_LOGGER_NAME );

    private static boolean configurationLoadedSuccessfully = false;

    public static void loadConfiguration(){
        LOGGER.info( "Configuration loading" );

        Optional<String> categoriesConfig = Optional.empty();
        LOGGER.info( "Categories configuration loading" );
        try
        {
            categoriesConfig = Optional.ofNullable( FileReader.readCategoriesConfigJson() );
        }
        catch( IOException e )
        {
            LOGGER.warning( "Cannot read categories configuration" );
        }


        if( categoriesConfig.isPresent() ){
            Optional<CategoriesConfiguration> categoriesConfiguration =
                JsonParser.parseJsonToCategoryConfiguration( categoriesConfig );
            if( categoriesConfiguration.isPresent() ){
                Configuration.setCategoriesConfiguration( categoriesConfiguration.get() );
                configurationLoadedSuccessfully = true;
                LOGGER.info( "Categories configuration loading has been finished" );
            }
        }

        LOGGER.info( "Configuration loading has been finished" );
    }


    public static boolean isConfigurationLoadedSuccessfully()
    {
        return configurationLoadedSuccessfully;
    }
}
