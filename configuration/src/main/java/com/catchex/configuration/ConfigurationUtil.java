package com.catchex.configuration;

import com.catchex.io.reader.ConfigurationReader;
import com.catchex.io.writer.ConfigurationWriter;
import com.catchex.models.CategoriesConfigurationV2;
import com.catchex.models.ConfigurationV2;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import static com.catchex.util.Constants.CONFIGURATION_PATH;
import static com.catchex.util.Constants.DEFAULT_CONFIGURATION_FILE_NAME;


public class ConfigurationUtil
{
    private static final Path defaultConfigurationPath =
        Paths.get( CONFIGURATION_PATH, DEFAULT_CONFIGURATION_FILE_NAME );


    public static Optional<CategoriesConfigurationV2> getConfiguration( Path path )
    {
        return ConfigurationReader.readConfiguration( path );
    }


    public static Optional<CategoriesConfigurationV2> getDefaultConfiguration()
    {
        return getConfiguration( defaultConfigurationPath );
    }


    public static void setConfigurationAsDefault(
        CategoriesConfigurationV2 categoriesConfiguration )
    {
        ConfigurationV2.getInstance().setCategoriesConfiguration( categoriesConfiguration );
        saveAsDefaultConfiguration( categoriesConfiguration );
    }


    public static void saveConfiguration(
        CategoriesConfigurationV2 categoriesConfiguration, Path path )
    {
        ConfigurationWriter.write( categoriesConfiguration, path );
    }


    public static void saveAsDefaultConfiguration(
        CategoriesConfigurationV2 categoriesConfiguration )
    {
        saveConfiguration( categoriesConfiguration, defaultConfigurationPath );
    }
}
