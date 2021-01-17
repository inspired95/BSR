package com.catchex.repositorycreator.client.view;

import com.catchex.repositorycreator.client.control.RepositoryCreatorDialogController;
import com.catchex.repositorycreator.client.view.columns.*;
import com.catchex.repositorycreator.client.view.model.AbstractTreeItem;
import com.catchex.repositorycreator.client.view.model.RootTreeItem;
import com.catchex.util.Constants;
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
