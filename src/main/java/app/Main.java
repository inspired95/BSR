package app;

import gui.MainFrame;

import javax.swing.*;
import java.util.logging.Level;

import static app.ConfigurationLoader.isConfigurationLoadedSuccessfully;
import static app.ConfigurationLoader.loadConfiguration;
import static app.Log.LOGGER;
import static utils.Util.showError;


public class Main
{
    public static void main( String[] args )
    {
        startApplication();
    }


    private static void startApplication()
    {
        Log.initLogging();
        LOGGER.info( "BSR staring" );
        loadConfiguration( true );
        if( isConfigurationLoadedSuccessfully() )
        {
            SwingUtilities.invokeLater( () -> new MainFrame() );
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
