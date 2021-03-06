package com.catchex.core.configuration;

import com.catchex.io.reader.ConfigurationReader;
import com.catchex.io.writer.ObjectToFileWriter;
import com.catchex.models.CategoriesConfiguration;
import com.catchex.models.Configuration;
import com.catchex.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;


public class ConfigurationUtil
{
    private static final Logger logger = LoggerFactory.getLogger( ConfigurationUtil.class );

    private static final Path defaultConfigurationPath =
        Paths.get( Constants.CONFIGURATION_PATH, Constants.DEFAULT_CONFIGURATION_FILE_NAME );


    public static Optional<CategoriesConfiguration> getConfiguration( Path path )
    {
        logger.debug( "Reading configuration from file {}", path );
        return ConfigurationReader.readConfiguration( path );
    }


    public static Optional<CategoriesConfiguration> getDefaultConfiguration()
    {
        logger.debug( "Getting current configuration" );
        return getConfiguration( defaultConfigurationPath );
    }


    public static void setConfigurationAsDefault(
        CategoriesConfiguration categoriesConfiguration )
    {
        logger.debug( "Updating default configuration" );
        Configuration.getInstance().setCategoriesConfiguration( categoriesConfiguration );
        saveAsDefaultConfiguration( categoriesConfiguration );
    }


    public static void saveConfiguration(
        CategoriesConfiguration categoriesConfiguration, Path path )
    {
        logger.debug( "Saving configuration to file {}", path );
        ObjectToFileWriter.getInstance().writeToFile( categoriesConfiguration, path );
    }


    public static void saveAsDefaultConfiguration(
        CategoriesConfiguration categoriesConfiguration )
    {
        saveConfiguration( categoriesConfiguration, defaultConfigurationPath );
    }
}
