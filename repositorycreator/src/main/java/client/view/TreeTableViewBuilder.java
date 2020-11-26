package client.view;

import client.Constants;
import client.control.RepositoryCreatorDialogController;
import client.view.columns.*;
import client.view.model.AbstractTreeItem;
import client.view.model.RootTreeItem;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableView;


public class TreeTableViewBuilder
{
    private RepositoryCreatorDialogController controller;
    private TreeTableView<AbstractTreeItem> treeTableView;


    public TreeTableViewBuilder( RepositoryCreatorDialogController controller )
    {
        this.controller = controller;
    }


    public TreeTableView<AbstractTreeItem> build()
    {
        TreeItem<AbstractTreeItem> treeRootItem = new TreeItem<>( new RootTreeItem() );
        treeRootItem.setExpanded( true );
        treeTableView = new TreeTableView<>();
        treeTableView.setRoot( treeRootItem );
        buildTreeTableViewColumns();
        treeTableView.setPrefHeight( 600 );

        return treeTableView;
    }


    private void buildTreeTableViewColumns()
    {
        treeTableView.setRowFactory( ttv -> new OperationRowFactory() );
        treeTableView.setEditable( true );
        treeTableView.setTableMenuButtonVisible( true );

        treeTableView.getColumns().setAll(
            new IntervalColumn( Constants.intervalTreeItemFormatter, controller ),
            new DateColumn( controller ), new IdColumn( controller ), new TypeColumn( controller ),
            new CategoryColumn( controller ), new AmountColumn( controller ),
            new DescriptionColumn( controller ), new BankNameColumn( controller ),
            new FileNameColumn( controller ) );

    }
}
