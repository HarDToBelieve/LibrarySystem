package com.itss.Controller;

import com.itss.Boundary.Forms.BookForm;
import com.itss.Boundary.Forms.CardForm;
import com.itss.Entity.BookCopyInfo;
import com.itss.Entity.BookInfo;
import com.itss.Entity.Card;
import com.itss.basic.BasicController;
import com.itss.utilities.RandomString;

import java.sql.Date;
import java.util.Calendar;
import java.util.Vector;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by BuiAnhVu on 11/26/2017.
 */
public class IssueCardController implements BasicController{
    private CardForm cardform;
    private Card card;
    private String card_number;
    @Override
    public Vector<Object> getModel() {
        Vector<Object> result = new Vector<>();
        result.add(new String[]{"user_id" , card.getUser_id()});
        result.add(new String[]{"is_active" , card.getIs_active()});
        result.add(new String[]{"is_student", card.getIs_student()});
        result.add(new String[]{"expired_date", card.getExpired_date()});
        result.add(new String[]{"activate_code", card.getActivate_code()});
        return result;
    }

    @Override
    public boolean validateObject() {
        return true;
    }

    @Override
    public void updateData() {
        card.add();
    }

    @Override
    public void selectData() {

    }

    public void setCardform(CardForm cardform) {
        this.cardform = cardform;
    }
    private boolean genACardNumber(){
        boolean is_ok = true;
        card_number = getRandomString(10);
        Vector<Card> existedCard = card.getAllCards();
        for(int index = 0; index < existedCard.size(); index++){
            if(card_number.equals(existedCard.get(index).getCard_number()))
                is_ok = false;
        }
        return is_ok;
    }
    private String getADate(int days_after){
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, days_after);
        return cal.getTime().toString();
    }
    public void genACard(){
        while(genACardNumber() == true){
            String user_id = cardform.getUser_id();
            String is_student = cardform.getIs_student();
            String activate_code = getRandomString(15);
            String expired_date = getADate(150);
            card = new Card(user_id, "No", is_student, expired_date,activate_code,this.card_number);
        }
    }
    public String getRandomString(int length){
        RandomString gen = new RandomString(length, ThreadLocalRandom.current());
        String cardNumber = gen.nextString();
        return cardNumber;
    }
}
