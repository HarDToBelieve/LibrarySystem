package com.itss.view;

import com.itss.basic.BasicController;
import com.itss.basic.BasicView;
import com.itss.controller.BookRegistrationController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.Vector;

public class BookRegistrationForm extends JDialog implements BasicView {
    private JPanel contentPane;
    private JButton btnSubmit;
    private JTextField inputTitle;
    private JTextField inputPubl;
    private JTextField inputPrice;
    private JComboBox combType;
    private JPanel dataField;
    private JTextField inputAuthor;
    private JTable dataTable;
    private JButton btnConfirm;
    private BookRegistrationController brc;
    private DefaultTableModel dtm;

    public BookRegistrationForm() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(btnSubmit);
        brc = new BookRegistrationController();
        String[] listTypes = new String[]{"Reference", "Borrowable"};
        combType.addItem(listTypes[0]);
        combType.addItem(listTypes[1]);
        Vector<String> colNames = new Vector<>(); colNames.add(""); colNames.add("");
        dtm = new DefaultTableModel(colNames, 0);
        dataTable.setModel(dtm);
        dataTable.setVisible(false);
        btnConfirm.setVisible(false);
        btnSubmit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                submit();
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
        btnConfirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    updateModelFromView();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });
    }

    @Override
    public BookRegistrationController getController() {
        return brc;
    }

    @Override
    public void setController(BasicController bc) {
        this.brc = (BookRegistrationController) bc;
    }

    @Override
    public void updateModelFromView() throws Exception {
        brc.updateData();
        close();
    }

    @Override
    public void updateViewFromController() {
        Vector<Object> data = brc.getModel();
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
            btnConfirm.setVisible(true);
        }
        else {
            error();
        }
    }

    @Override
    public void submit() {
        String title = inputTitle.getText();
        String author = inputAuthor.getText();
        String publisher = inputPubl.getText();
        String type = (String) combType.getSelectedItem();
        Double price = Double.parseDouble(inputPrice.getText());

        brc.genCode(title, author, publisher, type, price);
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
