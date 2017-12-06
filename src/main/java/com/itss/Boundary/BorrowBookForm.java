package com.itss.Boundary;

import com.itss.Controller.BookBorrowController;
import com.itss.Entity.User;
import com.itss.basic.BasicController;
import com.itss.basic.BasicView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.Vector;

public class BorrowBookForm extends JDialog implements BasicView {
    private JPanel contentPane;
    private JButton btnBack;
    private JButton btnBorrow;
    private JTextField inputFind;
    private JButton btnFind;
    private JTable dataTable;
    private JLabel lablStatus;
    private JPanel stage1;
    private JButton btnYes;
    private JButton btnNo;
    private JPanel stage2;
    private JPanel stage3;
    private JButton btnBack2;
    private JComboBox comboType;

    BookBorrowController bbc;
    DefaultTableModel dtm, ftm;

    public BorrowBookForm(String username, String cardno) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(btnBack);
        String[] listTypes = new String[]{"Search by title", "Search by copy id"};
        comboType.addItem(listTypes[0]);
        comboType.addItem(listTypes[1]);


        bbc = new BookBorrowController(cardno);
        Vector<String> colNames = new Vector<>();
        colNames.add(""); colNames.add("BookID"); colNames.add("CopyID"); colNames.add("Title"); colNames.add("Type"); colNames.add("Price"); colNames.add("Status");
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

        Vector<String> colNames2 = new Vector<>();
        colNames2.add("BookID"); colNames2.add("CopyID"); colNames2.add("Title"); colNames2.add("Type"); colNames2.add("Price"); colNames2.add("Status");
        ftm = new DefaultTableModel(colNames2, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        dataTable.setModel(dtm);
        lablStatus.setVisible(true);
        stage1.setVisible(false);
        stage2.setVisible(false);


        btnFind.addActionListener(e -> {
            updateViewFromController();
        });
        btnBack.addActionListener(e -> {
            initState();
        });
        btnBorrow.addActionListener(e -> {
            pickItems();
        });
        btnYes.addActionListener(e -> {
            updateModel();
            lablStatus.setText("User: " + username + " Card Number: " + cardno + " ---- Successfully");
            lablStatus.setVisible(true);

        });

        btnNo.addActionListener(e -> initState());
        btnBack2.addActionListener(e -> {
            initState();
        });
    }

    private void findCopy() {
        if (comboType.getSelectedIndex() == 0) {
            bbc.getCopiesByTitle(inputFind.getText());
        } else {
            bbc.getCopiesByBookId(inputFind.getText());
        }
        Vector<Object> data = bbc.getModel();
        if ( data.size() > 0 ) {
            if ( dtm.getRowCount() > 0 ) {
                for (int i = dtm.getRowCount() - 1; i >= 0; i--) {
                    dtm.removeRow(i);
                }
            }
            for (Object s : data) {
                String[] tmp = (String[])s;
                dtm.addRow(new Object[]{false, tmp[0], tmp[1], tmp[2], tmp[3], tmp[4], tmp[5]});
            }
            dtm.fireTableDataChanged();
            dataTable.setModel(dtm);
            stage1.setVisible(true);
        }
        else {
            if ( dtm.getRowCount() > 0 ) {
                for (int i = dtm.getRowCount() - 1; i >= 0; i--) {
                    dtm.removeRow(i);
                }
            }
        }
    }

    private void initState() {
        if ( dtm.getRowCount() > 0 ) {
            for (int i = dtm.getRowCount() - 1; i >= 0; i--) {
                dtm.removeRow(i);
            }
        }
        dtm.fireTableDataChanged();
        dataTable.setModel(dtm);
        stage1.setVisible(false);
        stage2.setVisible(false);
        stage3.setVisible(false);
        lablStatus.setVisible(false);
        inputFind.setText("");
    }

    private void pickItems() {
        if ( dtm.getRowCount() > 0 ) {
            Vector<String[]> tmp = new Vector<>();
            for (int i = dtm.getRowCount() - 1; i >= 0; i--) {
                if ((Boolean) dtm.getValueAt(i, 0) == true) {
                    tmp.add(new String[]{dtm.getValueAt(i, 1).toString(), dtm.getValueAt(i, 2).toString(),
                            dtm.getValueAt(i, 3).toString(), dtm.getValueAt(i, 4).toString(), dtm.getValueAt(i, 5).toString(), dtm.getValueAt(i, 6).toString()});
                }
            }
            bbc.setPick_from_view(tmp);

            try {
                bbc.getPickedBorrowBook();
                if ( bbc.validateObject() ) {
                    if (ftm.getRowCount() > 0) {
                        for (int i = ftm.getRowCount() - 1; i >= 0; i--)
                            ftm.removeRow(i);
                    }
                    for (int i = 0; i < tmp.size(); ++i)
                        ftm.addRow(tmp.get(i));
                    ftm.fireTableDataChanged();
                    dataTable.setModel(ftm);
                    stage1.setVisible(false);
                    stage2.setVisible(true);
                }
                else {
                    JOptionPane.showMessageDialog(this, "Cannot choose those copies");
                }
            } catch (ParseException e) {
                error();
            }
        }
    }

    @Override
    public BasicController getController() {
        return bbc;
    }

    @Override
    public void setController(BasicController bc) {
        bbc = (BookBorrowController) bc;
    }

    @Override
    public void updateModel() {
        try {
            bbc.updateData();
            stage3.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateViewFromController() {
        findCopy();
    }

    @Override
    public void submit() {

    }

    @Override
    public void close() {

    }

    @Override
    public void error() {
        lablStatus.setText("Error!");
        lablStatus.setVisible(true);
    }

    @Override
    public void refresh() {

    }

    @Override
    public JPanel getMainPanel() {
        return contentPane;
    }
}
