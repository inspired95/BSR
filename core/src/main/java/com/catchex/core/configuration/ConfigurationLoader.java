package com.catchex.core.configuration;

import com.catchex.logging.Log;
import com.catchex.models.CategoriesConfiguration;
import com.catchex.models.Configuration;

import java.util.Optional;


public class ConfigurationLoader
{

    public static void loadCategoriesConfiguration()
    {
        Optional<CategoriesConfiguration> defaultConfiguration =
            ConfigurationUtil.getDefaultConfiguration();
        defaultConfiguration.ifPresentOrElse( categoriesConfiguration -> {
            Configuration.getInstance().setCategoriesConfiguration( categoriesConfiguration );
            Log.LOGGER.info( "Default configuration loaded" );
        }, () -> {
            Log.LOGGER.info( "Cannot load default configuration" );
        } );
    }

}
