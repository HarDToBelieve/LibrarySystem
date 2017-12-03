package com.itss.Boundary;

import com.itss.Controller.BookBorrowController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.Vector;

public class BorrowBookView extends JDialog {
    private JPanel contentPane;
    private JButton btnBack;
    private JButton btnBorrow;
    private JTextField inputFind;
    private JButton btnFind;
    private JTable dataTable;

    BookBorrowController bbc;
    DefaultTableModel dtm, ftm;

    public BorrowBookView() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(btnBack);

        bbc = new BookBorrowController();
        Vector<String> colNames = new Vector<>();
        colNames.add(""); colNames.add("BookID"); colNames.add("CopyID"); colNames.add("Title"); colNames.add("Author"); colNames.add("Price"); colNames.add("Status");
        dtm = new DefaultTableModel(colNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 0;
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                Class clazz;
                switch (columnIndex) {
                    case 0:
                        clazz = Boolean.class;
                        break;
                    default:
                        clazz = String.class;
                        break;
                }
                return clazz;
            }

        };


    }
}
