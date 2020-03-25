import gui.MainFrame;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.util.logging.Logger.GLOBAL_LOGGER_NAME;
import static java.util.logging.Logger.getLogger;


public class Main
{

    public static void main( String[] args )
    {
        startApplication();
    }


    private final static Logger LOGGER = getLogger( GLOBAL_LOGGER_NAME );


    private static void startApplication()
    {
        LOGGER.setLevel( Level.ALL );
        LOGGER.info( "BSR staring" );
        SwingUtilities.invokeLater( () -> new MainFrame() );
    }
}
