package com.itss.Boundary;

import com.itss.Controller.ReturnBookController;
import com.itss.basic.BasicController;
import com.itss.basic.BasicView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.Vector;

public class ReturnBookView extends JDialog implements BasicView {
    private JPanel contentPane;
    private JComboBox comboType;
    private JTextField inputSearch;
    private JButton btnSearch;
    private JTable dataTable;
    private JButton btnProcess;
    private JPanel searchField;
    private JPanel resultField;
    private JPanel finishField;
    private JTable finishTable;
    private JTextField inputFee;
    private JButton btnFinish;
    private JLabel labelCost;
    private JButton btnCancel;
    private JButton btnBack;

    ReturnBookController rbc;
    DefaultTableModel dtm, ftm;
    double fee;

    public ReturnBookView() {
        setContentPane(contentPane);
        setModal(true);

        fee = 0;
        rbc = new ReturnBookController();
        String[] listTypes = new String[]{"Search by card number", "Search by copy id"};
        comboType.addItem(listTypes[0]);
        comboType.addItem(listTypes[1]);

        Vector<String> colNames = new Vector<>();
        colNames.add(""); colNames.add("Username"); colNames.add("Title"); colNames.add("CopyID");
        colNames.add("UserID"); colNames.add("Date"); colNames.add("CardNumber"); colNames.add("Fee");
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

        Vector<String> colResult = new Vector<>();
        colResult.add("Username"); colResult.add("Title");colResult.add("CopyID");
        colResult.add("UserID"); colResult.add("Date"); colResult.add("CardNumber"); colResult.add("Fee");
        ftm = new DefaultTableModel(colResult, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        finishTable.setModel(ftm);

        searchField.setVisible(true);
        resultField.setVisible(false);
        finishField.setVisible(false);

        btnSearch.addActionListener(e -> {
            preSubmit();
            preUpdateView();
        });
        btnProcess.addActionListener(e -> preSubmit2());
        btnFinish.addActionListener(e -> {
            submit();
            updateModel();

        });
        btnCancel.addActionListener(e -> {
            dataTable.setModel(dtm);
            resultField.setVisible(true);
            finishField.setVisible(false);
        });
        btnBack.addActionListener(e -> {
            searchField.setVisible(true);
            resultField.setVisible(false);
            finishField.setVisible(false);
        });
    }

    private void preSubmit2() {
        if ( dtm.getRowCount() > 0 ) {
            Vector<String[]> tmp = new Vector<>();
            for (int i = dtm.getRowCount() - 1; i>=0; i--) {
                if ( (Boolean)dtm.getValueAt(i, 0) == true ) {
                    tmp.add(new String[]{dtm.getValueAt(i, 1).toString(), dtm.getValueAt(i, 2).toString(),
                                        dtm.getValueAt(i, 3).toString(), dtm.getValueAt(i, 4).toString(), dtm.getValueAt(i, 5).toString(), dtm.getValueAt(i, 6).toString()});
                }
            }

            rbc.setPick_from_view(tmp);
            try {
                rbc.getPickedLentBook();
                if ( ftm.getRowCount() > 0 ) {
                    for (int i = ftm.getRowCount() - 1; i>=0; i--)
                        ftm.removeRow(i);
                }
                for (int i=0; i<tmp.size(); ++i)
                    ftm.addRow(tmp.get(i));

                if ( ftm.getRowCount() > 0 ) {
                    for (int i = ftm.getRowCount() - 1; i>=0; i--)
                        ftm.removeRow(i);
                }
                for (int i=0; i<tmp.size(); ++i)
                    ftm.addRow(tmp.get(i));
                dataTable.setModel(ftm);
                resultField.setVisible(false);
                finishField.setVisible(true);

            } catch (ParseException e) {
                error();
            }
        }
    }

    @Override
    public BasicController getController() {
        return rbc;
    }

    @Override
    public void setController(BasicController bc) {
        rbc = (ReturnBookController) bc;
    }

    @Override
    public void updateModel() {
        try {
            rbc.updateData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateViewFromController() {
        if ( !inputFee.getText().isEmpty() ) {
            fee = Double.parseDouble(inputFee.getText());
        }
        labelCost.setText("Total: " + String.valueOf(fee + rbc.getTotal_compensation()));
    }

    public void preSubmit() {
        try {
            if (comboType.getSelectedIndex() == 0) {
                rbc.getLentBooksByCardNumber(inputSearch.getText());
            } else {
                rbc.getLentBooksByCopyID(inputSearch.getText());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void preUpdateView() {
        Vector<Object> data = rbc.getModel();
        if ( data.size() > 0 ) {
            if ( dtm.getRowCount() > 0 ) {
                for (int i = dtm.getRowCount() - 1; i >= 0; i--) {
                    dtm.removeRow(i);
                }
            }
            for (Object s : data) {
                String[] tmp = (String[])s;
                dtm.addRow(new Object[]{false, tmp[0], tmp[1], tmp[2], tmp[3], tmp[4], tmp[5], tmp[6]});
            }
            dataTable.setModel(dtm);
            dtm.fireTableDataChanged();
            searchField.setVisible(false);
            resultField.setVisible(true);
        }
        else {
            error();
        }
    }

    @Override
    public void submit() {
        updateViewFromController();
    }

    @Override
    public void close() {

    }

    @Override
    public void error() {
        JOptionPane.showMessageDialog(this, "Something's wrong");
    }

    @Override
    public void refresh() {

    }

    @Override
    public JPanel getMainPanel() {
        return contentPane;
    }
}
