package com.catchex.io.reader;

import com.catchex.models.CategoriesConfiguration;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.file.Path;
import java.util.Optional;

import static com.catchex.logging.Log.LOGGER;


public class ConfigurationReader
{
    public static Optional<CategoriesConfiguration> readConfiguration( Path path )
    {
        try
        {
            FileInputStream fi = new FileInputStream( new File( path.toString() ) );
            ObjectInputStream oi = new ObjectInputStream( fi );

            CategoriesConfiguration categoriesConfiguration =
                (CategoriesConfiguration)oi.readObject();

            oi.close();
            fi.close();
            return Optional.of( categoriesConfiguration );
        }
        catch( IOException | ClassNotFoundException e )
        {
            e.printStackTrace();
            LOGGER.warning( e.getMessage() );
        }
        return Optional.empty();
    }
}
