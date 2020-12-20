package com.catchex.configuration;

import com.catchex.models.CategoriesConfiguration;
import com.catchex.models.Configuration;

import java.util.Optional;

import static com.catchex.util.Log.LOGGER;


public class ConfigurationLoader
{

    public static void loadCategoriesConfigurationV2()
    {
        Optional<CategoriesConfiguration> defaultConfiguration =
            ConfigurationUtil.getDefaultConfiguration();
        defaultConfiguration.ifPresentOrElse( categoriesConfiguration -> Configuration.getInstance()
            .setCategoriesConfiguration( categoriesConfiguration ), () -> {
                LOGGER.info( "Cannot load default configuration" );
            } );
    }

}
