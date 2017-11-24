package com.itss.Controller;

import com.itss.Boundary.CardForm;

import static org.junit.Assert.*;

/**
 * Created by BuiAnhVu on 11/21/2017.
 */
public class IssueCardControllerTest {
    @org.junit.Test
    public void validateData() throws Exception {
        IssueCardController issueCardController = new IssueCardController();
        CardForm cardForm = new CardForm("122239492233", "22072018", "hust2012acftt07", "No","No","20145292");
        issueCardController.setForm(cardForm);
        assertEquals(true, issueCardController.validateData());
    }

}