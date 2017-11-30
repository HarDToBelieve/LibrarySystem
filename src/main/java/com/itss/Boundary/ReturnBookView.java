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
        colNames.add(""); colNames.add(""); colNames.add(""); colNames.add(""); colNames.add("");
        dtm = new DefaultTableModel(colNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 0;
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                Class clazz = String.class;
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

        dataTable.setModel(dtm);

        Vector<String> colResult = new Vector<>();
        colResult.add(""); colResult.add(""); colResult.add(""); colResult.add("");
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

        btnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                preSubmit();
                preUpdateView();
            }
        });
        btnProcess.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                preSubmit2();
            }
        });
        btnFinish.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                submit();
                updateModel();
            }
        });
    }

    private void preSubmit2() {
        if ( dtm.getRowCount() > 0 ) {
            Vector<String[]> tmp = new Vector<>();
            for (int i = dtm.getRowCount() - 1; i>=0; i--) {
                if ( (Boolean)dtm.getValueAt(i, 0) == true ) {
                    tmp.add(new String[]{dtm.getValueAt(i, 1).toString(), dtm.getValueAt(i, 2).toString(),
                                        dtm.getValueAt(i, 3).toString(), dtm.getValueAt(i, 4).toString()});
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
                if ( !inputFee.getText().isEmpty() ) {
                    fee = Double.parseDouble(inputFee.getText());
                }

                ftm.fireTableDataChanged();
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
        rbc.updateData();
    }

    @Override
    public void updateViewFromController() {
        labelCost.setText("Total: " + String.valueOf(fee + rbc.getTotal_compensation()));
    }

    public void preSubmit() {
        if ( comboType.getSelectedIndex() == 0 ) {
            rbc.getLentBooksByCardNumber(inputSearch.getText());
        }
        else {
            rbc.getLentBooksByCopyID(inputSearch.getText());
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
                dtm.addRow(new Object[]{false, tmp[0], tmp[1], tmp[2], tmp[3]});
            }
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

    }

    @Override
    public JPanel getMainPanel() {
        return contentPane;
    }
}
