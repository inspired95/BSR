package com.catchex.client.app;

import com.catchex.client.gui.MainFrame;

import javax.swing.*;

import static com.catchex.configuration.ConfigurationLoader.isConfigurationLoadedSuccessfully;
import static com.catchex.configuration.ConfigurationLoader.loadConfiguration;
import static com.catchex.util.Log.*;
import static com.catchex.util.Util.showError;


public class Main
{
    public static void main( String[] args )
    {
        startApplication();
    }


    private static void startApplication()
    {
        initLogging();
        LOGGER.info( "BSR staring" );
        loadConfiguration( true );
        if( isConfigurationLoadedSuccessfully() )
        {
            SwingUtilities.invokeLater( MainFrame::new );
        }
        else
        {
            reportLoadingConfigErr( getLoadingConfigErrMsg() );
            System.exit( -1 );
        }
    }


    private static String getLoadingConfigErrMsg()
    {
        return "Configuration has not been loaded successfully";
    }


    private static void reportLoadingConfigErr( String errMsg )
    {
        LOGGER.warning( errMsg );
        showError( errMsg );
    }
}
