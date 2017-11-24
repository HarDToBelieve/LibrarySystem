package com.itss.Controller;

import com.itss.Boundary.CardForm;
import com.itss.Entity.CardManagement;
import com.itss.basic.BasicController;
import com.itss.utilities.RandomString;

import java.util.Vector;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by BuiAnhVu on 11/20/2017.
 */
public class IssueCardController implements BasicController{
    public void setForm(CardForm card) {
        this.cardForm = card;
    }

    private CardForm cardForm;
    private CardManagement card;
    public IssueCardController(){
        card = new CardManagement();
    }

    public void returnCardInfo(){

    }
    public void getCardInfo(){

    }
    public void updateCard(){
        card.updateCard();
    }
    public String genACardNumber(){
         RandomString gen = new RandomString(15, ThreadLocalRandom.current());
         return gen.toString();
    }

    @Override
    public Vector<Object> getModel() {
        Vector<Object> result = new Vector<>();
        result.add(new String[]{"activate_code", card.getActivateCode()}) ;
        result.add(new String[]{"expired_date", card.getExpiredDate()});
        result.add(new String[]{"card_number", card.getCardNumber()});
        result.add(new String[]{"is_active", String.valueOf(card.isActivated())});
        result.add(new String[]{"is_student", String.valueOf(card.isStudent())});
        result.add(new String[]{"user_id", card.getUserID()});
        return result;
    }

    @Override
    public boolean validateData() {
        boolean activateCode = !cardForm.getActivateCode().isEmpty() && cardForm.getActivateCode().matches("^[a-zA-Z0-9\\s]*$");
//        System.out.println(activateCode);
        boolean expiredDate = !cardForm.getExpiredDate().isEmpty() && cardForm.getExpiredDate().matches("^[a-zA-Z0-9\\s]*$");
//        System.out.println(expiredDate);
        boolean isActivated = !cardForm.isActivated().isEmpty() && cardForm.isActivated().matches("^[a-zA-Z0-9\\s]*$");
//        System.out.println(isActivated);
        boolean cardNumber = !cardForm.getCardNumber().isEmpty() && cardForm.getCardNumber().matches("^[a-zA-Z0-9\\s]*$");
//        System.out.println(cardNumber);
        boolean userID = !cardForm.getUserID().isEmpty() && cardForm.getUserID().matches("^[a-zA-Z0-9\\s]*$");
//        System.out.println(userID);
        boolean isStudent = !cardForm.isStudent().isEmpty() && cardForm.isStudent().matches("^[a-zA-Z0-9\\s]*$");
//        System.out.println(isStudent);
        return activateCode && expiredDate && isActivated && cardNumber && userID && isStudent;
    }

    @Override
    public void updateData() {
        updateCard();
    }

    @Override
    public void selectData() {

    }
}
