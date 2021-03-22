package com.catchex.io.writer;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Path;


public class ObjectToFileWriter
    implements IWriter<Object>
{
    private static final Logger logger = LoggerFactory.getLogger( ObjectToFileWriter.class );

    private static ObjectToFileWriter instance;


    private ObjectToFileWriter()
    {
    }


    public static ObjectToFileWriter getInstance()
    {
        if( instance == null )
        {
            instance = new ObjectToFileWriter();
        }
        return instance;
    }


    @Override
    public boolean writeToFile( Object content, Path path )
    {
        try (FileOutputStream f = new FileOutputStream( path.toString() );
            ObjectOutputStream o = new ObjectOutputStream( f );)
        {
            o.writeObject( content );
            return true;
        }
        catch( IOException e )
        {
            logger
                .error( "Error during application staring {}", ExceptionUtils.getStackTrace( e ) );
            return false;
        }
    }
}
