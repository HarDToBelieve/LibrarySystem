package com.itss.Controller;

import com.itss.Boundary.Forms.CardForm;

import java.text.ParseException;

/**
 * Created by BuiAnhVu on 12/5/2017.
 */
public class test1 {
    public static void main(String args[]){
        CardForm cardForm = new CardForm("20145292");
        IssueCardController issueCardController = new IssueCardController();
        issueCardController.setCardform(cardForm);
        System.out.println(issueCardController.getTypeOfGuest());
        try {
            System.out.println(issueCardController.checkValidToGetANewCard());
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

}
