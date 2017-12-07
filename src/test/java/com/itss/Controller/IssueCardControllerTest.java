package com.itss.Controller;

import com.itss.Boundary.Forms.CardForm;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by BuiAnhVu on 12/7/2017.
 */
public class IssueCardControllerTest {
    @Test
    public void isValidToGetANewCard() throws Exception {
        CardForm cardForm = new CardForm("TEST_01");
        IssueCardController issueCardController = new IssueCardController();
        issueCardController.setCardform(cardForm);
        boolean isValid = issueCardController.isValidToGetANewCard();
        assertEquals(isValid, false);
    }

    @Test
    public void validateObject() throws Exception {
        CardForm cardForm = new CardForm("TEST_01");
        IssueCardController issueCardController = new IssueCardController();
        issueCardController.setCardform(cardForm);
        boolean is_ok  = issueCardController.validateObject();
        assertEquals(is_ok, true);
    }

    @Test
    public void genACard() throws Exception {
        CardForm cardForm = new CardForm("TEST_01");
        IssueCardController issueCardController = new IssueCardController();
        issueCardController.setCardform(cardForm);
        issueCardController.type_of_guest = "student";
        issueCardController.genACard();
        String card_number = issueCardController.getCard().getCard_number();
        assertEquals(true, card_number.length() > 0);
    }

}