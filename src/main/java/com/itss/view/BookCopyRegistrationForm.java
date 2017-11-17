package com.itss.view;

import com.itss.basic.BasicController;
import com.itss.basic.BasicView;
import com.itss.controller.BookCopyRegistrationController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.Vector;

public class BookCopyRegistrationForm extends JDialog implements BasicView {
    private JPanel contentPane;
    private JButton btnSubmit;
    private JButton btnCancel;
    private JPanel dataField;
    private JTextField inputBookID;
    private JTextField inputNumCopy;
    private JTextField inputAvgPrice;
    private JTextField inputAuthor;
    private JButton btnConfirm;
    private JTable dataTable;
    private JComboBox combType;
    private BookCopyRegistrationController bcrc;
    private DefaultTableModel dtm;

    public BookCopyRegistrationForm() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(btnSubmit);
        bcrc = new BookCopyRegistrationController();
        String[] listTypes = new String[]{"Reference", "Borrowable"};
        combType.addItem(listTypes[0]);
        combType.addItem(listTypes[1]);
        Vector<String> colNames = new Vector<>();
        colNames.add(""); colNames.add(""); colNames.add(""); colNames.add(""); colNames.add("");
        dtm = new DefaultTableModel(colNames, 0);
        dataTable.setModel(dtm);
        dataTable.setVisible(false);
        btnConfirm.setVisible(false);
        btnCancel.setVisible(false);
        btnSubmit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                submit();
            }
        });

        btnCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                close();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                close();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                close();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    @Override
    public BookCopyRegistrationController getController() {
        return bcrc;
    }

    @Override
    public void setController(BasicController bc) {
        this.bcrc = (BookCopyRegistrationController) bc;
    }

    @Override
    public void updateModelFromView() {
        bcrc.updateData();
        close();
    }

    @Override
    public void updateViewFromController() {
        Vector<Object> data = bcrc.getModel();
        if ( data.size() > 0 ) {
            for (Component c : dataField.getComponents()) {
                c.setVisible(false);
            }
            for (Object s : data) {
                String[] tmp = (String[])s;
                dtm.addRow(new Object[]{tmp[0], tmp[1]});
            }
            dtm.fireTableDataChanged();
            dataTable.setVisible(true);
            btnSubmit.setVisible(false);
            btnCancel.setVisible(true);
            btnConfirm.setVisible(true);
        }
        else {
            error();
        }
    }

    @Override
    public void submit() {
        String bookID = inputBookID.getText();
        int numOfCopy = Integer.parseInt(inputNumCopy.getText());
        String type = (String) combType.getSelectedItem();
        double avgPrice = Double.parseDouble(inputAvgPrice.getText());
        String author = inputAuthor.getText();

        bcrc.genCopyCode(bookID, type, avgPrice, author);
        updateViewFromController();
    }

    @Override
    public void close() {
        dispose();
    }

    @Override
    public void error() {

    }
}
