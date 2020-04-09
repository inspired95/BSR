package com.catchex.client.gui;

import javax.swing.filechooser.FileFilter;
import java.io.File;
import java.util.List;

import static com.catchex.util.Constants.BANK_STMT_ALLOWED_EXTENSIONS;
import static com.catchex.util.Constants.DOT;


public class ExtensionFilter
    extends FileFilter
{
    private List<String> extensions;


    public ExtensionFilter( List<String> extensions )
    {
        this.extensions = extensions;
    }


    public boolean accept( File file )
    {
        if( file.isDirectory() )
        {
            return true;
        }

        String path = file.getAbsolutePath();
        for( String extension : extensions )
        {
            if( path.endsWith( extension ) &&
                path.charAt( path.length() - extension.length() ) == DOT )
            {
                return true;
            }
        }
        return false;
    }


    @Override
    public String getDescription()
    {
        return BANK_STMT_ALLOWED_EXTENSIONS.toString();
    }
}