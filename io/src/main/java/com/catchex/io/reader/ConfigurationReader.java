package com.catchex.io.reader;

import com.catchex.models.CategoriesConfiguration;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.file.Path;
import java.util.Optional;


public class ConfigurationReader
{
    private static final Logger logger = LoggerFactory.getLogger( ConfigurationReader.class );


    public static Optional<CategoriesConfiguration> readConfiguration( Path path )
    {
        try (ObjectInputStream oi = new ObjectInputStream( new FileInputStream( path.toString() ) ))
        {
            CategoriesConfiguration categoriesConfiguration =
                (CategoriesConfiguration)oi.readObject();

            return Optional.of( categoriesConfiguration );
        }
        catch( IOException | ClassNotFoundException e )
        {
            logger.error( ExceptionUtils.getStackTrace( e ) );
        }
        return Optional.empty();
    }
}
