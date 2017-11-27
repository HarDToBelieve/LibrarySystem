package com.itss.Entity;

import com.itss.basic.BasicModel;


public class BookLentHistory implements BasicModel {
    private String user_id;
    private String copyID;
    private String date;
    private String card_number;

    public String getUser_id() {
        return user_id;
    }

    public String getCopyID() {
        return copyID;
    }

    public String getDate() {
        return date;
    }

    public String getCard_number() {
        return card_number;
    }

    @Override
    public boolean checkConnection() {
        return true;
    }

    @Override
    public void getByID(String ID) {

    }

    @Override
    public void add() {

    }

    @Override
    public boolean validObject() {
        return false;
    }
    public void getByCardNumber(){

    }
}
