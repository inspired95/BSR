package client.control.event;

import client.Repository;
import client.view.RepositoryCreatorDialogView;
import com.catchex.report.ReportGeneratorController;
import com.catchex.util.Log;
import javafx.scene.control.TreeTableColumn;

import java.util.Optional;


public class GenerateReportBtnEventHandler
    implements EventHandler
{
    private Repository repository;
    private RepositoryCreatorDialogView view;


    public GenerateReportBtnEventHandler( Repository repository, RepositoryCreatorDialogView view )
    {
        this.repository = repository;
        this.view = view;
    }


    @Override
    public void handle( Object event )
    {
        Log.LOGGER.info( "Report saving started" );
        System.out.println( getSortedByColumnName() );
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
