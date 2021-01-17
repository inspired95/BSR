package com.catchex.io.writer;

import com.catchex.models.CategoriesConfiguration;

import java.io.*;
import java.nio.file.Path;


public class ConfigurationWriter
    implements IWriter<CategoriesConfiguration>
{
    private static ConfigurationWriter INSTANCE;


    private ConfigurationWriter()
    {
    }


    public static ConfigurationWriter getInstance()
    {
        if( INSTANCE == null )
        {
            INSTANCE = new ConfigurationWriter();
        }
        return INSTANCE;
    }


    public static boolean write( CategoriesConfiguration configuration, Path path )
    {
        try
        {
            FileOutputStream f = new FileOutputStream( new File( path.toString() ) );
            ObjectOutputStream o = new ObjectOutputStream( f );

            o.writeObject( configuration );

            o.close();
            f.close();

        }
        catch( FileNotFoundException e )
        {
            System.out.println( "File not found" );
        }
        catch( IOException e )
        {
            e.printStackTrace();
            System.out.println( "Error initializing stream" );
        }
        return true;
    }


    @Override
    public boolean writeToFile( CategoriesConfiguration content, Path path )
    {
        try (FileOutputStream f = new FileOutputStream( new File( path.toString() ) );
            ObjectOutputStream o = new ObjectOutputStream( f );)
        {
            o.writeObject( content );
            return true;
        }
        catch( IOException e )
        {
            e.printStackTrace();
            return false;
        }
    }
}
