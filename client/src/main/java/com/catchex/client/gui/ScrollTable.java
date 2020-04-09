package com.catchex.client.gui;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;


public class ScrollTable
    extends ScrollPane
{
    private JTable table;
    private DefaultTableModel model;


    public ScrollTable( String tooltip, int width, int height, String... columns )
    {
        getModel( columns );
        getTable( tooltip );

        setBounds( 0, 0, width, height );
        add( table );
    }


    public void getTable( String tooltip )
    {
        table = new JTable( model );
        table.setToolTipText( tooltip );

        DefaultTableCellRenderer tableCellRendererCenter = getDefaultTableCellRendererCenter();
        for( int i = 0; i < table.getColumnCount(); i++ )
        {
            TableColumn columnSource = table.getColumnModel().getColumn( i );
            columnSource.setCellRenderer( tableCellRendererCenter );
        }
    }


    public void getModel( String[] columns )
    {
        model = new DefaultTableModel()
        {
            @Override
            public boolean isCellEditable( int row, int column )
            {
                return false;
            }
        };

        for( String column : columns )
        {
            model.addColumn( column );
        }
    }


    public DefaultTableModel getModel()
    {
        return model;
    }


    private DefaultTableCellRenderer getDefaultTableCellRendererCenter()
    {
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( SwingConstants.CENTER );
        return centerRenderer;
    }
}
