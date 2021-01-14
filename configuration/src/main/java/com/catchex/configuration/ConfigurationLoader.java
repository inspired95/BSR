package com.catchex.configuration;

import com.catchex.models.CategoriesConfiguration;
import com.catchex.models.Configuration;

import java.util.Optional;

import static com.catchex.util.Log.LOGGER;


public class ConfigurationLoader
{

    public static void loadCategoriesConfiguration()
    {
        Optional<CategoriesConfiguration> defaultConfiguration =
            ConfigurationUtil.getDefaultConfiguration();
        defaultConfiguration.ifPresentOrElse( categoriesConfiguration -> {
            Configuration.getInstance().setCategoriesConfiguration( categoriesConfiguration );
            LOGGER.info( "Default configuration loaded" );
        }, () -> {
            LOGGER.info( "Cannot load default configuration" );
        } );
    }

}
