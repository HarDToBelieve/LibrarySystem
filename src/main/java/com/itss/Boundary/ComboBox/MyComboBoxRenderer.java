package com.itss.Boundary.ComboBox;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

/**
 * Created by HarDToBelieve on 11/25/2017.
 */

public class MyComboBoxRenderer extends JComboBox implements TableCellRenderer {

    public MyComboBoxRenderer(String[] items) {
        super(items);
    }

    /**
     * Render a table with a ComboBox
     * @param table current table
     * @param value value or current cell
     * @param isSelected check whether the cell is selected or not
     * @param hasFocus check whether the cell is focused or not
     * @param row row index
     * @param column column index
     */
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                   boolean hasFocus, int row, int column) {
        if (isSelected) {
            setForeground(table.getSelectionForeground());
            super.setBackground(table.getSelectionBackground());
        } else {
            setForeground(table.getForeground());
            setBackground(table.getBackground());
        }
        setSelectedItem(value);
        return this;
    }
}
