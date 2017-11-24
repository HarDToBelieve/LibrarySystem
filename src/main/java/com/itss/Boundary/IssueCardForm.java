package com.itss.Boundary;

import com.itss.Controller.IssueCardController;
import com.itss.basic.BasicController;
import com.itss.basic.BasicView;
import com.itss.utilities.RandomString;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.util.UUID;

/**
 * Created by BuiAnhVu on 11/20/2017.
 */
public class IssueCardForm extends JDialog implements BasicView{
    private JPanel contentPanel;
    private JComboBox guest_type_box;
    private JTextField inputStudentNumber;
    private JLabel module_name;
    private JPanel start_panel;
    private JPanel functional_panel;
    private JButton confirmButton;
    private JButton nextButton;
    private JLabel notice;
    private JButton cancelButton;
    private JTextArea final_note;
    private JButton exitButton;

    String choice_box[] = {"Select", "Guest is not HUST student", "Guest is HUST student"};
    String isStudent;
    String userID = "";

    private IssueCardController cardController;
    private CardForm cardForm;

    public IssueCardForm(){
        setContentPane(contentPanel);
        setModal(true);
        cardController = new IssueCardController();
        confirmButton.setVisible(false);
        nextButton.setVisible(false);
        exitButton.setVisible(false);
        notice.setVisible(false);
        inputStudentNumber.setVisible(false);
        final_note.setVisible(false);
        guest_type_box.addItem(choice_box[0]);
        guest_type_box.addItem(choice_box[1]);
        guest_type_box.addItem(choice_box[2]);
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                submit();
                updateModel();
                displayCardInfo();
            }
        });
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                submit();
                updateModel();
                displayCardInfo();
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

       guest_type_box.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               chooseGuestType();
           }
       });
    }
    private void displayCardInfo(){
        updateViewFromController();
    }

    private void chooseGuestType() {
        if(guest_type_box.getSelectedIndex() == 0){
            cancelButton.setVisible(true);
            confirmButton.setVisible(false);
            nextButton.setVisible(false);
            notice.setVisible(false);
            inputStudentNumber.setVisible(false);
        }
        else if(guest_type_box.getSelectedIndex() == 1){
            cancelButton.setVisible(true);
            nextButton.setVisible(false);
            confirmButton.setVisible(true);
            notice.setVisible(true);
            inputStudentNumber.setVisible(false);
            isStudent = "No";
            userID = "";
        }
        else if(guest_type_box.getSelectedIndex() == 2){
            cancelButton.setVisible(true);
            nextButton.setVisible(true);
            confirmButton.setVisible(false);
            notice.setVisible(false);
            inputStudentNumber.setVisible(true);
            isStudent = "Yes";
        }
    }


    @Override
    public BasicController getController() {
        return cardController;
    }

    @Override
    public void setController(BasicController bc) {
        this.cardController = (IssueCardController) bc;
    }

    @Override
    public void updateModel() {
        //add a new card info to server
        cardController.updateData();
    }

    @Override
    public void updateViewFromController() {

        String mess ="Please note down this information \n" +
                "Card number: " + cardForm.cardNumber + "\n" +
                "Expired Date: " + cardForm.expiredDate + "\n" +
                "Activate Code: " + cardForm.activateCode + "\n" ;
        confirmButton.setVisible(false);
        nextButton.setVisible(false);
        cancelButton.setVisible(false);
        guest_type_box.setVisible(false);
        inputStudentNumber.setVisible(false);
        exitButton.setVisible(true);
        final_note.setText(mess);
        final_note.setVisible(true);
    }

    @Override
    public void submit() {
        String cardNumber = cardController.genACardNumber();
        LocalDate now = LocalDate.now();
        String expiredDate = now.plusDays(300).toString();
        String isActivated = "No";
        String isStudent = this.isStudent;
        String activateCode = cardNumber + "libinhust";
        if(!userID.equals(""))userID = inputStudentNumber.getText();
        CardForm cardForm_ = new CardForm(activateCode, expiredDate, cardNumber, isActivated, isStudent, userID);
        cardController.setForm(cardForm_);
        cardForm = cardForm_;
    }

    @Override
    public void close() {
        dispose();
    }

    @Override
    public void error() {

    }

}
