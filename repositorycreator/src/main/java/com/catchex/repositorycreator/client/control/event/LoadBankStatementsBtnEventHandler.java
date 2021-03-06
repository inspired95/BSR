package com.catchex.repositorycreator.client.control.event;

import GuiHelpers.Alerts;
import com.catchex.models.CurrentOperation;
import com.catchex.models.Operation;
import com.catchex.repositorycreator.client.control.CurrentOperationsUtil;
import com.catchex.repositorycreator.client.control.OperationsFromBankStatementsFilesProvider;
import com.catchex.repositorycreator.client.control.RepositoryCreatorDialogController;
import com.catchex.repositorycreator.client.model.CurrentRepositoryUtil;
import com.catchex.util.Constants;
import dialogs.EventHandler;
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
                Set<Operation> operations = new OperationsFromBankStatementsFilesProvider(
                    bankName,
                    selectedBankStatementsFiles ).get();

                Set<CurrentOperation> currentRepository =
                    new CurrentOperationsUtil().mapToCurrentOperations( operations );
                new CurrentRepositoryUtil()
                    .addCurrentOperations( Optional.of( currentRepository ) );
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
            "Select bank statements" );
    }
}
