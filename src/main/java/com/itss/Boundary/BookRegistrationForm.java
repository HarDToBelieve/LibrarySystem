package com.itss.Boundary;

import com.itss.Boundary.ComboBox.MyComboBoxEditor;
import com.itss.Boundary.ComboBox.MyComboBoxRenderer;
import com.itss.Boundary.Forms.BookForm;
import com.itss.Controller.BookCopyRegistrationController;
import com.itss.basic.BasicController;
import com.itss.basic.BasicView;
import com.itss.Controller.BookRegistrationController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.*;
import java.util.Vector;

public class BookRegistrationForm extends JDialog implements BasicView {
    private JPanel contentPane;
    private JButton btnSubmit;
    private JTextField inputTitle;
    private JTextField inputPubl;
    private JPanel dataField;
    private JTextField inputAuthor;
    private JTable dataTable;
    private JButton btnConfirm;
    private JButton btnCancel;
    private JTextField inputISBN;

    BookCopyRegistrationController bcrc;
    private BookRegistrationController brc;
    private DefaultTableModel dtm;

    public BookRegistrationForm() {

        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(btnSubmit);
        brc = new BookRegistrationController();
        bcrc = new BookCopyRegistrationController();

        Vector<String> colNames = new Vector<>(); colNames.add(""); colNames.add("");
        dtm = new DefaultTableModel(colNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        dataTable.setModel(dtm);

        dataTable.setVisible(false);
        btnConfirm.setVisible(false);
        btnCancel.setVisible(false);
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
                    updateModel();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                close();
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
    public void updateModel() {
        brc.updateData();
        int opt =JOptionPane.showConfirmDialog(this,"Do you want to add a copy?");
        if ( opt == JOptionPane.YES_OPTION) {
            String type = JOptionPane.showInputDialog(this,"Enter type");
            String price = JOptionPane.showInputDialog(this,"Enter price");
            brc.addSample(Double.parseDouble(price), type);
        }
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
            btnCancel.setVisible(true);
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
        String isbn = inputISBN.getText();

        BookForm bf = new BookForm(title, author, publisher, isbn);
        brc.setForm(bf);
        if ( brc.validateObject() ) {
            brc.genCode();
            updateViewFromController();
        }
        else {
            error();
        }
    }

    @Override
    public void close() {
        dispose();
    }

    @Override
    public void error() {

    }

    @Override
    public JPanel getMainPanel() {
        return contentPane;
    }
}
