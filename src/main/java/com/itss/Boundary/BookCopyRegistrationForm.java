package com.itss.Boundary;

import com.itss.Boundary.ComboBox.MyComboBoxEditor;
import com.itss.Boundary.ComboBox.MyComboBoxRenderer;
import com.itss.basic.BasicController;
import com.itss.basic.BasicView;
import com.itss.Controller.BookCopyRegistrationController;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Vector;

public class BookCopyRegistrationForm extends JDialog implements BasicView {
    private JPanel contentPane;
    private JButton btnSubmit;
    private JButton btnCancel;
    private JTextField inputBookID;
    private JTextField inputNumCopy;
    private JTextField inputAvgPrice;
    private JButton btnConfirm;
    private JTable dataTable;
    private JComboBox combType;
    private BookCopyRegistrationController bcrc;
    private DefaultTableModel dtm;
    private DefaultTableModel dtm2;

    /**
     * Initialize all components and listeners
     */
    public BookCopyRegistrationForm() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(btnSubmit);
        bcrc = new BookCopyRegistrationController();
        String[] listTypes = new String[]{"Reference", "Borrowable"};
        combType.addItem(listTypes[0]);
        combType.addItem(listTypes[1]);

        Vector<String> colNames = new Vector<>();
        colNames.add("CopyID"); colNames.add("Type"); colNames.add("Price"); colNames.add("BookID");
        dtm = new DefaultTableModel(colNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return !(column == 0 || column == 3);
            }
        };

        Vector<String> colNames2 = new Vector<>();
        colNames2.add("CopyID"); colNames2.add("Type"); colNames2.add("Price"); colNames2.add("BookID"); colNames2.add("Title"); colNames2.add("Status");
        dtm2 = new DefaultTableModel(colNames2, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        Vector<ArrayList<String>> cur_db = bcrc.getCopy("");
        for (ArrayList<String> tmp : cur_db) {
            dtm2.addRow(new String[]{tmp.get(0), tmp.get(1), tmp.get(2), tmp.get(3), tmp.get(4), tmp.get(5)});
        }

        dataTable.setModel(dtm2);

        btnConfirm.setVisible(false);
        btnCancel.setVisible(false);
        btnSubmit.addActionListener(e -> submit());

        btnConfirm.addActionListener(e -> {
            for (int count = 0; count < dtm.getRowCount(); count++){
//                    System.out.println(dtm.getValueAt(count, 1).toString());
                bcrc.modifyData(dtm.getValueAt(count, 1).toString(), dtm.getValueAt(count, 2).toString(), count);
//                    dtm.getDataVector().elementAt(count);
            }
            updateModel();
            bcrc.setDb();
            initState();
        });
        btnCancel.addActionListener(e -> {
            initState();
            close();
        });

        inputBookID.getDocument().addDocumentListener(new DocumentListener() {
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
                Vector<ArrayList<String>> cur_db = bcrc.getCopy(inputBookID.getText());
                for (ArrayList<String> tmp : cur_db) {
                    dtm2.addRow(new String[]{tmp.get(0), tmp.get(1), tmp.get(2), tmp.get(3), tmp.get(4), tmp.get(5)});
                }
                dtm2.fireTableDataChanged();
            }
        });

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                close();
            }
        });
        contentPane.registerKeyboardAction(e -> close(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    /**
     * Return to beginning state
     */
    private void initState() {
        if ( dtm2.getRowCount() > 0 ) {
            for (int i = dtm2.getRowCount() - 1; i >= 0; i--) {
                dtm2.removeRow(i);
            }
        }
        Vector<ArrayList<String>> cur_db = bcrc.getCopy("");
        for (ArrayList<String> tmp : cur_db) {
            dtm2.addRow(new String[]{tmp.get(0), tmp.get(1), tmp.get(2), tmp.get(3), tmp.get(4), tmp.get(5)});
        }

        dtm2.fireTableDataChanged();
        dataTable.setModel(dtm2);

        inputBookID.setText("");
        inputNumCopy.setText("");
        inputAvgPrice.setText("");

        inputBookID.setEditable(true);
        inputAvgPrice.setEditable(true);
        inputNumCopy.setEditable(true);
        combType.setEditable(true);

        btnConfirm.setVisible(false);
        btnCancel.setVisible(false);
        btnSubmit.setVisible(true);
    }

    @Override
    public BookCopyRegistrationController getController() {
        return bcrc;
    }

    @Override
    public void setController(BasicController bc) {
        this.bcrc = (BookCopyRegistrationController) bc;
    }

    /**
     * Finish the work by calling method updateData of controller
     */
    @Override
    public void updateModel() {
        bcrc.updateData();
        close();
    }

    /**
     * Get the correct information from controller to update the view
     */
    @Override
    public void updateViewFromController() {
        Vector<Object> data = bcrc.getModel();
        if ( data.size() > 0 ) {
            if ( dtm.getRowCount() > 0 ) {
                for (int i = dtm.getRowCount() - 1; i >= 0; i--) {
                    dtm.removeRow(i);
                }
            }

            for (Object s : data) {
                String[] tmp = (String[])s;
                dtm.addRow(new Object[]{tmp[0], tmp[1], tmp[2], tmp[3]});
            }
            dtm.fireTableDataChanged();
            dataTable.setModel(dtm);

            String[] listTypes = new String[]{"Reference", "Borrowable"};
            TableColumn col = dataTable.getColumnModel().getColumn(1);
            col.setCellEditor(new MyComboBoxEditor(listTypes));
            col.setCellRenderer(new MyComboBoxRenderer(listTypes));

            btnSubmit.setVisible(false);
            btnCancel.setVisible(true);
            btnConfirm.setVisible(true);

            inputBookID.setEditable(false);
            inputAvgPrice.setEditable(false);
            inputNumCopy.setEditable(false);
            combType.setEditable(false);
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
        String bookID = inputBookID.getText();
        String numOfCopy = inputNumCopy.getText();
        String type = (String) combType.getSelectedItem();
        String avgPrice = inputAvgPrice.getText();

        bcrc.setForm(bookID, type, avgPrice, numOfCopy);
        if ( bcrc.validateObject() ) {
            bcrc.genCopyCode();
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


