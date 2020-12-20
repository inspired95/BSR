package com.catchex.configuration;

import com.catchex.models.CategoriesConfiguration;
import com.catchex.models.CategoriesConfigurationV2;
import com.catchex.models.ConfigurationV2;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Optional;

import static com.catchex.configuration.DefaultConfigurationCreator.createDefaultCategoriesConfiguration;
import static com.catchex.io.reader.FileReader.readCategoriesConfigJson;
import static com.catchex.io.reader.JsonParser.parseJsonToCategoryConfiguration;
import static com.catchex.util.Constants.CATEGORIES_CONFIGURATION_FILE_NAME;
import static com.catchex.util.Constants.CONFIGURATION_PATH;
import static com.catchex.util.Log.LOGGER;


public class ConfigurationLoader
{
    private static boolean categoriesConfigurationLoadedSuccessfully = false;


    public static void loadConfiguration( Boolean isFirstConfigurationLoadingAttempt )
    {
        LOGGER.info( "Configuration loading" );

        loadCategoriesConfiguration( isFirstConfigurationLoadingAttempt );
    }


    private static void loadCategoriesConfigurationV2()
    {
        Optional<CategoriesConfigurationV2> defaultConfiguration =
            ConfigurationUtil.getDefaultConfiguration();
        defaultConfiguration.ifPresentOrElse( categoriesConfiguration -> {
            ConfigurationV2.getInstance().setCategoriesConfiguration( categoriesConfiguration );
        }, () -> {
            LOGGER.info( "Cannot load default configuration" );
        } );
    }


    private static void loadCategoriesConfiguration( Boolean isFirstConfigurationLoadingAttempt )
    {
        LOGGER.info( "Categories configuration loading" );
        if( checkIfCategoriesConfigurationExists() )
        {
            Optional<String> categoriesConfig = readCategoriesConfigurationJson();
            if( categoriesConfig.isPresent() )
            {
                Optional<CategoriesConfiguration> categoriesConfiguration =
                    parseCategoriesConfigurationJson( categoriesConfig.get() );
                categoriesConfiguration
                    .ifPresent( ConfigurationLoader::setCategoriesConfiguration );
            }
            LOGGER.info( "Configuration loading has been finished" );
        }
        else if( isFirstConfigurationLoadingAttempt )
        {
            LOGGER.info( "Categories configuration does not exist" );
            int input = JOptionPane.showConfirmDialog(
                null, "Categories configuration does not exist! \nThe default configuration" +
                    " will be created...", "Are you agree?", JOptionPane.YES_NO_OPTION );
            if( input == 0 )
            {
                createDefaultCategoriesConfiguration();
                loadCategoriesConfiguration( false );
            }
        }
        else
        {
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
        return parseJsonToCategoryConfiguration( categoriesConfig );
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
            LOGGER.warning( "Cannot read com.catchex.bankstmt.categories configuration" );
        }
        return categoriesConfig;
    }


    public static boolean isConfigurationLoadedSuccessfully()
    {
        return categoriesConfigurationLoadedSuccessfully;
    }


    private static boolean checkIfCategoriesConfigurationExists()
    {
        File categoriesConfigurationFile = new File(
            Paths.get( CONFIGURATION_PATH, CATEGORIES_CONFIGURATION_FILE_NAME ).toString() );
        return categoriesConfigurationFile.exists();
    }
}
