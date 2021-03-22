package guihelpers;

import javafx.stage.FileChooser;

import java.io.File;

import static com.catchex.util.Constants.*;


public class FileChoosers
{
    public static FileChooser getRepositoryFileChooser( String title )
    {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory( new File( CONFIGURATION_PATH ) );
        fileChooser.setTitle( title );
        fileChooser.getExtensionFilters().add( new FileChooser.ExtensionFilter( "BSR repository",
            BSR_REPOSITORY_EXTENSION_FILE_CHOOSER ) );
        return fileChooser;
    }


    public static FileChooser getConfigurationFileChooser( String title )
    {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory( new File( CONFIGURATION_PATH ) );
        fileChooser.setTitle( title );
        fileChooser.getExtensionFilters().add(
            new FileChooser.ExtensionFilter( "BSR configuration" + "configuration",
                BSR_CONFIGURATION_EXTENSION_FILE_CHOOSER ) );
        return fileChooser;
    }


    public static FileChooser getBankStatementsFileChooser( String title )
    {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle( title );

        FileChooser.ExtensionFilter extFilter =
            new FileChooser.ExtensionFilter( PDF_EXTENSION_LABEL, BANK_STATEMENT_EXTENSIONS );
        fileChooser.getExtensionFilters().add( extFilter );
        return fileChooser;
    }
}
