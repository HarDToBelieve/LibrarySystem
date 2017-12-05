package com.itss.Boundary;

import com.itss.Boundary.Forms.CardForm;
import com.itss.Controller.IssueCardController;
import com.itss.basic.BasicController;
import com.itss.basic.BasicView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.text.ParseException;
import java.util.Vector;

public class IssueCardView extends JDialog implements BasicView {
    private JPanel contentPane;
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

        guestField.setVisible(true);
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

//        comboType.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                if ( comboType.getSelectedItem().equals("Guest is HUST student") ) {
//                    studentField.setVisible(true);
//                    guestField.setVisible(false);
//                    dataField.setVisible(false);
//                }
//                else if ( comboType.getSelectedItem().equals("Guest is not HUST student") ) {
//                    studentField.setVisible(false);
//                    guestField.setVisible(true);
//                    dataField.setVisible(false);
//                }
//                else {
//                    studentField.setVisible(false);
//                    guestField.setVisible(false);
//                    dataField.setVisible(false);
//                }
//            }
//        });

        btnDone.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateModel();
                if ( !icc.isAddCardSuccess() )
                    JOptionPane.showMessageDialog(null, "Something's wrong");
                dataField.setVisible(false);
                guestField.setVisible(true);
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
        CardForm cf = new CardForm(inputGuest.getText());
        icc.setCardform(cf);
        if (icc.validateObject()) {
            if ( icc.getTypeOfGuest().equals("guest") ) {
                JOptionPane.showMessageDialog(null, "Guest who is not a HUST student needs to pay a deposit of 15000.");
            }
            try {
                if (icc.isValidToGetANewCard()) {
                    icc.genACard();
                    updateViewFromController();
                }
                else  {
                    JOptionPane.showMessageDialog(null, "You are not allowed to get a new card");
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        else {
            JOptionPane.showMessageDialog(null, "Cannot find such userID");
        }
    }

    @Override
    public void close() {
        onCancel();
    }

    @Override
    public void error() {
        JOptionPane.showMessageDialog(this, "Something's wrong");
    }

    @Override
    public void refresh() {

    }

    public JPanel getMainPanel() {
        return contentPane;
    }
}
