package com.itss.Boundary;

import com.itss.Controller.BookDeleteController;
import com.itss.basic.BasicController;
import com.itss.basic.BasicView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.text.ParseException;
import java.util.Vector;

public class BookDeleteForm extends JDialog implements BasicView {
    private JPanel contentPane;
    private JTextField inputFind;
    private JButton btnFind;
    private JTable dataTable;
    private JButton btnBack;
    private JButton btnDelete;
    private JLabel lablQuestion;
    private JLabel lablStatus;
    private JButton btnYes;
    private JButton btnNo;
    private JPanel stage1;
    private JPanel stage2;
    private JPanel stage3;
    private JButton btnBack2;
    private JButton buttonOK;

    BookDeleteController bdc;
    DefaultTableModel dtm, ftm;

    public BookDeleteForm() {
        setContentPane(contentPane);
        setModal(true);

        bdc = new BookDeleteController();
        Vector<String> colNames = new Vector<>();
        colNames.add(""); colNames.add("CopyID"); colNames.add("Type"); colNames.add("Price"); colNames.add("BookID"); colNames.add("Title"); colNames.add("Status");
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
        colNames2.add("CopyID"); colNames2.add("Type"); colNames2.add("Price"); colNames2.add("BookID"); colNames2.add("Title"); colNames2.add("Status");
        ftm = new DefaultTableModel(colNames2, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        dataTable.setModel(dtm);
        lablQuestion.setVisible(false);
        lablStatus.setVisible(true);
        stage1.setVisible(false);
        stage2.setVisible(false);
        stage3.setVisible(false);

        btnFind.addActionListener(e -> {
            updateViewFromController();
        });
        btnBack.addActionListener(e -> {
            initState();
        });
        btnDelete.addActionListener(e -> {
            pickItems();
        });
        btnYes.addActionListener(e -> {
            updateModel();
            lablStatus.setText("Successfully");
            lablStatus.setVisible(true);
            stage1.setVisible(false);
            stage2.setVisible(false);
            stage3.setVisible(true);
        });
        btnNo.addActionListener(e -> initState());
        btnBack2.addActionListener(e -> {
            initState();
        });
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
            bdc.setPick_from_view(tmp);

            try {
                bdc.getPickedDeleteBook();
                if ( ftm.getRowCount() > 0 ) {
                    for (int i = ftm.getRowCount() - 1; i>=0; i--)
                        ftm.removeRow(i);
                }
                for (int i=0; i<tmp.size(); ++i)
                    ftm.addRow(tmp.get(i));
                ftm.fireTableDataChanged();
                dataTable.setModel(ftm);
                stage1.setVisible(false);
                stage2.setVisible(true);

            } catch (ParseException e) {
                error();
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
        inputFind.setText("");
    }

    private void findCopy() {
        bdc.getCopyByCopyID(inputFind.getText());
        Vector<Object> data = bdc.getModel();
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
            stage1.setVisible(true);
        }
        else {
            JOptionPane.showMessageDialog(this, "Cannot find such copy id");
        }
    }

    @Override
    public BasicController getController() {
        return bdc;
    }

    @Override
    public void setController(BasicController bc) {
        bdc = (BookDeleteController) bc;
    }

    @Override
    public void updateModel() {
        bdc.updateData();
        if ( !bdc.isPickedRowsIsDeletable() ) {
            JOptionPane.showMessageDialog(this, "There're books is BORROWED");
        }
        else {
            JOptionPane.showMessageDialog(this, "Successfully");
        }
        initState();
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
