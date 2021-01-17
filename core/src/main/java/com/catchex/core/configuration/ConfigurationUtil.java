package com.catchex.core.configuration;

import com.catchex.io.reader.ConfigurationReader;
import com.catchex.io.writer.ConfigurationWriter;
import com.catchex.models.CategoriesConfiguration;
import com.catchex.models.Configuration;
import com.catchex.util.Constants;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;


public class ConfigurationUtil
{
    private static final Path defaultConfigurationPath =
        Paths.get( Constants.CONFIGURATION_PATH, Constants.DEFAULT_CONFIGURATION_FILE_NAME );


    public static Optional<CategoriesConfiguration> getConfiguration( Path path )
    {
        return ConfigurationReader.readConfiguration( path );
    }


    public static Optional<CategoriesConfiguration> getDefaultConfiguration()
    {
        return getConfiguration( defaultConfigurationPath );
    }


    public static void setConfigurationAsDefault(
        CategoriesConfiguration categoriesConfiguration )
    {
        Configuration.getInstance().setCategoriesConfiguration( categoriesConfiguration );
        saveAsDefaultConfiguration( categoriesConfiguration );
    }


    public static void saveConfiguration(
        CategoriesConfiguration categoriesConfiguration, Path path )
    {
        ConfigurationWriter.getInstance().writeToFile( categoriesConfiguration, path );
    }


    public static void saveAsDefaultConfiguration(
        CategoriesConfiguration categoriesConfiguration )
    {
        saveConfiguration( categoriesConfiguration, defaultConfigurationPath );
    }
}
