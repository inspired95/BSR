package com.catchex.repositorycreator.client.control.event;

import com.catchex.models.Repository;
import com.catchex.reportcreator.ReportGeneratorController;
import com.catchex.repositorycreator.client.view.RepositoryCreatorDialogView;
import javafx.scene.control.TreeTableColumn;

import java.util.Optional;

import static com.catchex.logging.Log.LOGGER;


public class GenerateReportBtnEventHandler
    implements EventHandler
{
    private Repository repository;
    private RepositoryCreatorDialogView view;


    public GenerateReportBtnEventHandler(
        Repository repository, RepositoryCreatorDialogView view )
    {
        this.repository = repository;
        this.view = view;
    }


    @Override
    public void handle( Object event )
    {
        LOGGER.info( "Report saving started" );
        //System.out.println( getSortedByColumnName() );
        new ReportGeneratorController( repository.getOperations() ).generate();
    }


    private String getSortedByColumnName()
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
    }

}
