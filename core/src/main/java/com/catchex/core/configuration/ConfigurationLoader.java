package com.catchex.core.configuration;

import com.catchex.models.CategoriesConfiguration;
import com.catchex.models.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;


public class ConfigurationLoader
{
    private static final Logger logger = LoggerFactory.getLogger( ConfigurationLoader.class );


    public static void loadCategoriesConfiguration()
    {
        Optional<CategoriesConfiguration> defaultConfiguration =
            ConfigurationUtil.getDefaultConfiguration();
        defaultConfiguration.ifPresentOrElse( categoriesConfiguration -> {
            Configuration.getInstance().setCategoriesConfiguration( categoriesConfiguration );
            logger.info( "Default configuration loaded" );
        }, () -> {
            logger.info( "Cannot load default configuration" );
        } );
    }

}
