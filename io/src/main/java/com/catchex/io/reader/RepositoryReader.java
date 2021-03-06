package com.catchex.io.reader;

import com.catchex.models.Repository;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Optional;


public class RepositoryReader
{
    private static final Logger logger = LoggerFactory.getLogger( RepositoryReader.class );


    private RepositoryReader()
    {

    }


    public static Optional<Repository> loadRepository( File file )
    {
        Repository loadedRepository = null;
        if( file != null )
        {
            try (ObjectInputStream oi = new ObjectInputStream( new FileInputStream( file ) ))
            {
                loadedRepository = (Repository)oi.readObject();
            }
            catch( IOException | ClassNotFoundException e )
            {
                logger.error( ExceptionUtils.getStackTrace( e ) );
            }
        }
        return Optional.ofNullable( loadedRepository );
    }
}
