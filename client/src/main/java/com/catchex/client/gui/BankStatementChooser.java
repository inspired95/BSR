package com.catchex.client.gui;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;

import static com.catchex.util.Constants.BANK_STMT_ALLOWED_EXTENSIONS;
import static com.catchex.util.Constants.SELECT_BANK_STATEMENT_TXT;


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
        setMultiSelectionEnabled( true );
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
