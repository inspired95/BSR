package app;

import gui.MainFrame;

import javax.swing.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import static app.ConfigurationLoader.isConfigurationLoadedSuccessfully;
import static app.ConfigurationLoader.loadConfiguration;
import static java.util.logging.Logger.GLOBAL_LOGGER_NAME;
import static java.util.logging.Logger.getLogger;


public class Main
{
    private final static Logger LOGGER = getLogger( GLOBAL_LOGGER_NAME );


    public static void main( String[] args )
    {
        startApplication();
    }


    private static void startApplication()
    {
        LOGGER.setLevel( Level.ALL );
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
        JOptionPane.showMessageDialog( null, errMsg, "Error", JOptionPane.ERROR_MESSAGE );
    }
}
