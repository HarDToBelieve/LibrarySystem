package com.itss.Boundary;

import com.itss.Boundary.ComboBox.MyComboBoxEditor;
import com.itss.Boundary.ComboBox.MyComboBoxRenderer;
import com.itss.Boundary.Forms.BookForm;
import com.itss.Controller.BookCopyRegistrationController;
import com.itss.Controller.ObserverController;
import com.itss.basic.BasicController;
import com.itss.basic.BasicView;
import com.itss.Controller.BookRegistrationController;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Vector;

public class BookRegistrationForm extends JDialog implements BasicView {
    private JPanel contentPane;
    private JButton btnSubmit;
    private JTextField inputTitle;
    private JTextField inputPubl;
    private JTextField inputAuthor;
    private JTable dataTable;
    private JButton btnConfirm;
    private JButton btnCancel;
    private JTextField inputISBN;
    private JComboBox comboType;

    BookCopyRegistrationController bcrc;
    private BookRegistrationController brc;
    private DefaultTableModel dtm;
    private DefaultTableModel dtm2;

    /**
     * Initialize all components and listeners
     */
    public BookRegistrationForm() {

        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(btnSubmit);
        brc = new BookRegistrationController();
        bcrc = new BookCopyRegistrationController();

        String[] listTypes = new String[] {
                "GW - General Works",
                "PP - Philosophy, Psychology, Religion",
                "WH - World History",
                "GA - Geography, Anthropology, Recreation",
                "SS - Social Sciences",
                "PS - Political Science",
                "LA - LAW",
                "ED - Education",
                "MU - Music",
                "LL - Language and Literature",
                "SC - Science",
                "ME - Medicine",
                "AG - Agriculture",
                "TE - Technology",
                "MS - Military Science",
                "NS - Naval Science",
                "BL - Bibliography, Library Science"
        };
        for (String s : listTypes) {
            comboType.addItem(s);
        }


        Vector<String> colNames = new Vector<>(); colNames.add("Information"); colNames.add("");
        dtm = new DefaultTableModel(colNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        Vector<String> colNames2 = new Vector<>(); colNames2.add("Title"); colNames2.add("Author"); colNames2.add("Publisher"); colNames2.add("ISBN"); colNames2.add("BookID");
        dtm2 = new DefaultTableModel(colNames2, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        Vector<ArrayList<String>> cur_db = brc.getBook("");
        for (ArrayList<String> tmp : cur_db) {
            dtm2.addRow(new String[]{tmp.get(0), tmp.get(1), tmp.get(2), tmp.get(3), tmp.get(4)});
        }
        dataTable.setModel(dtm2);

        btnConfirm.setVisible(false); btnCancel.setVisible(false);

        btnSubmit.addActionListener(e -> submit());
        btnConfirm.addActionListener(e -> {
            try {
                updateModel();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });
        btnCancel.addActionListener(e -> {
            initState();
        });

        inputTitle.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                search();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                search();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                search();
            }

            public void search() {
                if ( dtm2.getRowCount() > 0 ) {
                    for (int i = dtm2.getRowCount() - 1; i >= 0; i--) {
                        dtm2.removeRow(i);
                    }
                }
                Vector<ArrayList<String>> cur_db = brc.getBook(inputTitle.getText());
                for (ArrayList<String> tmp : cur_db) {
                    dtm2.addRow(new String[]{tmp.get(0), tmp.get(1), tmp.get(2), tmp.get(3), tmp.get(4)});
                }
                dtm2.fireTableDataChanged();
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

    /**
     * Finish the work by calling method updateData of controller
     */
    @Override
    public void updateModel() {
        brc.updateData();
        int opt =JOptionPane.showConfirmDialog(this,"Do you want to add a copy?");
        if ( opt == JOptionPane.YES_OPTION) {
            BookCopyRegistrationForm tmp_bcrf = new BookCopyRegistrationForm();
            tmp_bcrf.pack();
            tmp_bcrf.setVisible(true);
        }
        initState();
        ObserverController.notify(1);
//        close();
    }

    /**
     * Return to beginning state
     */
    public void initState() {
        brc.setDb();
        if ( dtm2.getRowCount() > 0 ) {
            for (int i = dtm2.getRowCount() - 1; i >= 0; i--) {
                dtm2.removeRow(i);
            }
        }
        Vector<ArrayList<String>> cur_db1 = brc.getBook("");
        for (ArrayList<String> tmp : cur_db1) {
            dtm2.addRow(new String[]{tmp.get(0), tmp.get(1), tmp.get(2), tmp.get(3), tmp.get(4)});
        }

        dtm2.fireTableDataChanged(); dataTable.setModel(dtm2);
        inputTitle.setText(""); inputAuthor.setText(""); inputPubl.setText(""); inputISBN.setText("");
        inputAuthor.setEditable(true); inputISBN.setEditable(true); inputPubl.setEditable(true); inputTitle.setEditable(true);
        btnConfirm.setVisible(false); btnCancel.setVisible(false); btnSubmit.setVisible(true);
    }

    /**
     * Get the correct information from controller to update the view
     */
    @Override
    public void updateViewFromController() {
        Vector<Object> data = brc.getModel();
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
            dataTable.setModel(dtm);
//            dataTable.setVisible(true);
            btnSubmit.setVisible(false);
            btnCancel.setVisible(true);
            btnConfirm.setVisible(true);

            inputAuthor.setEditable(false);
            inputISBN.setEditable(false);
            inputPubl.setEditable(false);
            inputTitle.setEditable(false);
        }
        else {
            error();
        }
    }

    /**
     * Submit all information from the form to controller
     */
    @Override
    public void submit() {
        String title = inputTitle.getText();
        String author = inputAuthor.getText();
        String publisher = inputPubl.getText();
        String isbn = inputISBN.getText();
        String type = (String) comboType.getSelectedItem();

        BookForm bf = new BookForm(title, author, publisher, isbn);
        brc.setForm(bf);
        if ( brc.validateObject() ) {
            brc.genCode(type.substring(0, 2));
            updateViewFromController();
        }
        else {
            error();
            initState();
        }
    }

    @Override
    public void close() {
        dispose();
    }

    @Override
    public void error() {
        JOptionPane.showMessageDialog(this, "Something's wrong");
    }

    @Override
    public void refresh() {
        initState();
    }

    @Override
    public JPanel getMainPanel() {
        return contentPane;
    }
}
