package gui;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.io.File;

import static utils.Constants.*;


public class BankStatementChooser
    extends JFileChooser
{
    public BankStatementChooser()
    {
        super( FileSystemView.getFileSystemView().getHomeDirectory() );
        setup();
    }


    private void setup()
    {
        setDialogTitle( SELECT_BANK_STATEMENT_TXT );
        setMultiSelectionEnabled( false );
        setFileSelectionMode( JFileChooser.FILES_ONLY );
        setExtensionFilter();
    }


    private void setExtensionFilter()
    {
        ExtensionFilter extensionFilter = new ExtensionFilter( BANK_STMT_ALLOWED_EXTENSIONS );
        addChoosableFileFilter( extensionFilter );
        setFileFilter( extensionFilter );
    }
}
