package com.catchex.configuration;

import com.catchex.models.CategoriesConfigurationV2;
import com.catchex.models.ConfigurationV2;

import java.util.Optional;

import static com.catchex.util.Log.LOGGER;


public class ConfigurationLoader
{

    public static void loadCategoriesConfigurationV2()
    {
        Optional<CategoriesConfigurationV2> defaultConfiguration =
            ConfigurationUtil.getDefaultConfiguration();
        defaultConfiguration.ifPresentOrElse(
            categoriesConfiguration -> ConfigurationV2.getInstance()
                .setCategoriesConfiguration( categoriesConfiguration ), () -> {
                LOGGER.info( "Cannot load default configuration" );
            } );
    }

}
