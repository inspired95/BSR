package com.catchex.repositorycreator.client.control.event;

import com.catchex.reportcreator.ReportGeneratorController;
import com.catchex.repositorycreator.client.control.RepositoryCreatorDialogController;
import com.catchex.repositorycreator.client.model.repository.CurrentRepositoryHolder;
import dialogs.EventHandler;


public class GenerateReportBtnEventHandler
    extends EventHandler
{
    private RepositoryCreatorDialogController controller;


    public GenerateReportBtnEventHandler(
        RepositoryCreatorDialogController controller )
    {
        super( "GenerateReport" );
        this.controller = controller;
    }


    @Override
    public void handle( Object event )
    {
        super.handle( event );
        new ReportGeneratorController( CurrentRepositoryHolder.getInstance().get().getOperations() )
            .generate();
    }


    /*private String getSortedByColumnName()
    {
        Optional<String> sortedByColumn =
            view.getTreeTableView().getSortOrder().stream().map( TreeTableColumn::getText )
                .findFirst();
        if( sortedByColumn.isPresent() )
        {
            return sortedByColumn.get();
        }
        else
        {
            return "";
        }
    }*/

}