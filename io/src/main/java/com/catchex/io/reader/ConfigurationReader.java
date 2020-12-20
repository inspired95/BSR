package com.catchex.io.reader;

import com.catchex.models.CategoriesConfigurationV2;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.file.Path;
import java.util.Optional;

import static com.catchex.util.Log.LOGGER;


public class ConfigurationReader
{
    public static Optional<CategoriesConfigurationV2> readConfiguration( Path path )
    {
        try
        {
            FileInputStream fi = new FileInputStream( new File( path.toString() ) );
            ObjectInputStream oi = new ObjectInputStream( fi );

            CategoriesConfigurationV2 categoriesConfiguration =
                (CategoriesConfigurationV2)oi.readObject();

            oi.close();
            fi.close();
            return Optional.of( categoriesConfiguration );
        }
        catch( IOException | ClassNotFoundException e )
        {
            LOGGER.warning( e.getMessage() );
        }
        return Optional.empty();
    }
}
