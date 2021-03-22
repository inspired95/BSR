package com.catchex.repositorycreator.client.control.event;

import com.catchex.repositorycreator.client.control.RepositoryCreatorDialogController;
import com.catchex.util.Constants;
import dialogs.EventHandler;
import guihelpers.Alerts;
import guihelpers.FileChoosers;
import javafx.stage.Stage;

import java.io.File;
import java.util.*;


public class LoadBankStatementsBtnEventHandler
    extends EventHandler<RepositoryCreatorDialogController>
{
    private SortedSet<String> banks = new TreeSet<>( Arrays.asList( Constants.supportedBanks ) );


    public LoadBankStatementsBtnEventHandler( RepositoryCreatorDialogController controller )
    {
        super( "LoadBankStatements", controller );
    }


    @Override
    public void handle()
    {
        super.handle();
        Optional<String> selectedBankName = getSelectedBank();

        selectedBankName.ifPresentOrElse( bankName -> {
            List<File> selectedBankStatementsFiles = getBankStatementsFileToLoad();
            if( selectedBankStatementsFiles != null )
            {
                BankStatementReadingTask bankStatementReadingTask =
                    new BankStatementReadingTask( bankName, selectedBankStatementsFiles );
                new Thread( bankStatementReadingTask ).start();
            }
            else
            {
                actionCancelled();
            }

        }, this::actionCancelled );
    }


    private Optional<String> getSelectedBank()
    {
        return Alerts.showChoiceFromListDialog( "Repository creator", Constants.SELECT_BANK_TXT,
            new ArrayList<>( banks ) );
    }


    private List<File> getBankStatementsFileToLoad()
    {
        return Alerts.showOpenMultipleDialog(
            (Stage)getDialogController().getView().getScene().getWindow(),
            FileChoosers.getBankStatementsFileChooser( "Select bank statements" ) );
    }

}
