package com.itss.Boundary;

import com.itss.Boundary.Forms.CardForm;
import com.itss.Controller.IssueCardController;
import com.itss.basic.BasicController;
import com.itss.basic.BasicView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.Vector;

public class IssueCardView extends JDialog implements BasicView {
    private JPanel contentPane;
    private JTextField inputStdCard;
    private JButton btnNext;
    private JButton btnConfirm;
    private JComboBox comboType;
    private JPanel guestField;
    private JPanel studentField;
    private JPanel dataField;
    private JButton btnDone;
    private JTable dataTable;
    private JTextField inputGuest;

    IssueCardController icc;
    private DefaultTableModel dtm;

    public IssueCardView() {
        setContentPane(contentPane);
        setModal(true);
        icc = new IssueCardController();
        String[] listTypes = new String[]{"Select", "Guest is HUST student", "Guest is not HUST student"};
        comboType.addItem(listTypes[0]);
        comboType.addItem(listTypes[1]);
        comboType.addItem(listTypes[2]);
        guestField.setVisible(false);
        studentField.setVisible(false);
        dataField.setVisible(false);

        Vector<String> colNames = new Vector<>(); colNames.add(""); colNames.add("");
        dtm = new DefaultTableModel(colNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        dataTable.setModel(dtm);
        dataTable.setVisible(false);

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        comboType.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if ( comboType.getSelectedItem().equals("Guest is HUST student") ) {
                    studentField.setVisible(true);
                    guestField.setVisible(false);
                    dataField.setVisible(false);
                }
                else if ( comboType.getSelectedItem().equals("Guest is not HUST student") ) {
                    studentField.setVisible(false);
                    guestField.setVisible(true);
                    dataField.setVisible(false);
                }
                else {
                    studentField.setVisible(false);
                    guestField.setVisible(false);
                    dataField.setVisible(false);
                }
            }
        });
        btnNext.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                submit();
            }
        });
        btnDone.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateModel();
                if ( !icc.isAddCardSuccess() )
                    JOptionPane.showMessageDialog(null, "Something's wrong");
                dataField.setVisible(false);
                studentField.setVisible(false);
                guestField.setVisible(false);
            }
        });


        btnConfirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                submit();
            }
        });
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    @Override
    public BasicController getController() {
        return icc;
    }

    @Override
    public void setController(BasicController bc) {
        icc = (IssueCardController) bc;
    }

    @Override
    public void updateModel() {
        icc.updateData();
    }

    @Override
    public void updateViewFromController() {
        Vector<Object> data = icc.getModel();
        if ( data.size() > 0 ) {
            if ( dtm.getRowCount() > 0 ) {
                for (int i = dtm.getRowCount() - 1; i >= 0; i--) {
                    dtm.removeRow(i);
                }
            }
            for (Object s : data) {
                String[] tmp = (String[])s;
                dtm.addRow(new Object[]{tmp[0], tmp[1]});
            }
            dtm.fireTableDataChanged();
            dataField.setVisible(true);
            studentField.setVisible(false);
            guestField.setVisible(false);
            dataTable.setVisible(true);
//            btnNext.setVisible(false);
//            btnConfirm.setVisible(false);
            btnDone.setVisible(true);
        }
        else {
            error();
        }
    }

    @Override
    public void submit() {
//        String cond = comboType.getSelectedIndex() == 1 ? "YES" : "NO";
//        String id = comboType.getSelectedIndex() == 1 ? inputStdCard.getText() : inputGuest.getText();
//        CardForm cf = new CardForm(inputStdCard.getText(), cond);
//        icc.setCardform(cf);
//        if (icc.validateObject()) {
//            icc.genACard();
//            updateViewFromController();
//        }
    }

    @Override
    public void close() {
        onCancel();
    }

    @Override
    public void error() {

    }

    public JPanel getMainPanel() {
        return contentPane;
    }
}
