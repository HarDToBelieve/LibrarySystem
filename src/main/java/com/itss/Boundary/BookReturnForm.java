package com.itss.Boundary;

import com.itss.Controller.BookReturnController;
import com.itss.basic.BasicController;
import com.itss.basic.BasicForm;
import com.itss.basic.BasicView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.util.Vector;

public class BookReturnForm extends JDialog implements BasicView {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JComboBox choose_box;
    private JTextField textInput;
    private JButton searchButton;
    private JLabel notice;
    private JPanel JPanel1;
    private JPanel JPanel2;
    private JPanel JPanel3;
    private JTable result_table;
    private BookReturnController bookReturnController;
    private BookLentForm bookLentForm;
    private DefaultTableModel dtm;
    String choices[] = {"Select","Search By Copy Number", "Search By Card Number"};

    public BookReturnForm() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });
        notice.setVisible(false);
        choose_box.addItem(choices[0]);
        choose_box.addItem(choices[1]);
        choose_box.addItem(choices[2]);
        Vector<String> colNames = new Vector<>();
        colNames.add(""); colNames.add(""); colNames.add(""); colNames.add(""); colNames.add("");
        dtm = new DefaultTableModel(colNames, 0);
        result_table.setModel(dtm);
        result_table.setVisible(true);

        choose_box.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                search();
            }
        });
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                begin_search();
            }
        });

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
    }

    private void onOK() {
        // add your code here
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {
        BookReturnForm dialog = new BookReturnForm();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
    private void search(){
        if(choose_box.getSelectedIndex() == 1){
            search_by_card_number();
        }
        else if(choose_box.getSelectedIndex() == 2){
            search_by_copy_number();
        }
    }
    private void begin_search(){
        if(textInput.getText().length() == 0){
            notice.setText("Please, fill the blank");
            notice.setVisible(true);
        }
    }
    private void search_by_card_number(){
        System.out.println("1");

    }
    private void search_by_copy_number(){
        System.out.println("2");
    }

    @Override
    public BasicController getController() {
        return null;
    }

    @Override
    public void setController(BasicController bc) {

    }

    @Override
    public void updateModel() {

    }

    @Override
    public void updateViewFromController() {

    }

    @Override
    public void submit() {

    }

    @Override
    public void close() {

    }

    @Override
    public void error() {

    }
}
