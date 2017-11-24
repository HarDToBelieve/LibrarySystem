package com.itss.Boundary;

import com.itss.basic.BasicForm;

/**
 * Created by BuiAnhVu on 11/21/2017.
 */
public class BookLentForm implements BasicForm{
    String userID;
    String copyID;
    String card_number;
    String date;
    String isReturned;

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getCopyID() {
        return copyID;
    }

    public void setCopyID(String copyID) {
        this.copyID = copyID;
    }

    public String getCard_number() {
        return card_number;
    }

    public void setCard_number(String card_number) {
        this.card_number = card_number;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
