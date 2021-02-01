package com.catchex.io.writer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;


public class ReportWriter
    implements IWriter<String>
{

    private static ReportWriter instance;


    private ReportWriter()
    {
    }


    public static ReportWriter getInstance()
    {
        if( instance == null )
        {
            instance = new ReportWriter();
        }
        return instance;
    }


    @Override
    public boolean writeToFile( String content, Path path )
    {
        try
        {
            Files.write( path, content.getBytes() );
            return true;
        }
        catch( IOException e )
        {
            e.printStackTrace();
            return false;
        }
    }
}
