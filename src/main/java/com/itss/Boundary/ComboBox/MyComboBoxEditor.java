package com.itss.Boundary.ComboBox;

import javax.swing.*;

/**
 * Created by HarDToBelieve on 11/25/2017.
 */
public class MyComboBoxEditor extends DefaultCellEditor {
    public MyComboBoxEditor(String[] items) {
        super(new JComboBox(items));
    }
}