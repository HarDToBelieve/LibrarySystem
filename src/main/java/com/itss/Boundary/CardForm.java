package com.itss.Boundary;

import com.itss.basic.BasicForm;

/**
 * Created by BuiAnhVu on 11/20/2017.
 */
public class CardForm implements BasicForm{
    String activateCode;
    String isActivated;
    String expiredDate;
    String cardNumber;

    public String getUserID() {
        return userID;
    }

    String isStudent;
    String userID;
    public CardForm(String activateCode, String expiredDate, String cardNumber, String isActivated, String isStudent, String userID){
        this.activateCode = activateCode;
        this.expiredDate = expiredDate;
        this.cardNumber = cardNumber;
        this.isActivated = isActivated;
        this.isStudent = isStudent;
        this.userID = userID;
    }
    public String getActivateCode() {
        return activateCode;
    }

    public void setActivateCode(String activateCode) {
        this.activateCode = activateCode;
    }

    public String isActivated() {
        return isActivated;
    }

    public void setActivated(String activated) {
        isActivated = activated;
    }

    public String getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(String expiredDate) {
        this.expiredDate = expiredDate;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String isStudent() {
        return isStudent;
    }

    public void setStudent(String student) {
        isStudent = student;
    }

}
