package com.itss.Boundary;

import com.itss.basic.BasicController;
import com.itss.basic.BasicView;
import com.itss.Controller.BookCopyRegistrationController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
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
        dtm = new DefaultTableModel(colNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return !(column == 0 || column == 3);
            }
        };

        dataTable.setModel(dtm);
        TableColumn col = dataTable.getColumnModel().getColumn(1);
        col.setCellEditor(new MyComboBoxEditor(listTypes));
        col.setCellRenderer(new MyComboBoxRenderer(listTypes));

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
        btnConfirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int count = 0; count < dtm.getRowCount(); count++){
                    bcrc.modifyData(dtm.getValueAt(count, 1).toString(), dtm.getValueAt(count, 2).toString(), count);
//                    dtm.getDataVector().elementAt(count);
                }
                updateModel();
            }
        });
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
    public void updateModel() {
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
                dtm.addRow(new Object[]{tmp[0], tmp[1], tmp[2], tmp[3]});
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
        String numOfCopy = inputNumCopy.getText();
        String type = (String) combType.getSelectedItem();
        String avgPrice = inputAvgPrice.getText();

        bcrc.setForm(bookID, type, avgPrice, numOfCopy);
        if ( bcrc.getBookStatus() ) {
            bcrc.genCopyCode();
            updateViewFromController();
        }
    }

    @Override
    public void close() {
        dispose();
    }

    @Override
    public void error() {

    }
}

class MyComboBoxRenderer extends JComboBox implements TableCellRenderer {
    public MyComboBoxRenderer(String[] items) {
        super(items);
    }

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                   boolean hasFocus, int row, int column) {
        if (isSelected) {
            setForeground(table.getSelectionForeground());
            super.setBackground(table.getSelectionBackground());
        } else {
            setForeground(table.getForeground());
            setBackground(table.getBackground());
        }
        setSelectedItem(value);
        return this;
    }
}

class MyComboBoxEditor extends DefaultCellEditor {
    public MyComboBoxEditor(String[] items) {
        super(new JComboBox(items));
    }
}
