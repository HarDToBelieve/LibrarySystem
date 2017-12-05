package com.itss.Controller;
import com.itss.Boundary.Forms.CardForm;
import com.itss.Entity.Card;
import com.itss.Entity.User;
import com.itss.basic.BasicController;
import com.itss.utilities.DateHandling;
import com.itss.utilities.RandomString;
import java.text.ParseException;
import java.util.Vector;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by BuiAnhVu on 11/26/2017.
 */
public class IssueCardController implements BasicController{
    private CardForm cardform;
    private Card card;
    private String card_number;
    private final int card_length = 10;
    private final int code_length = 15;
    private String type_of_guest;
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
    public String getTypeOfGuest(){
        //return null if user_id is not existed => print out error
        // if result returned != null, => it could be "student" or "guest"
        if(!User.check_a_user_existed(cardform.getUser_id()))
            return null;
        String job = User.getJobOfAUser(cardform.getUser_id());
        this.type_of_guest = job;
        return job;
    }

    public boolean isValidToGetANewCard() throws ParseException {
        boolean is_valid = true;
        System.out.println("user_id " + cardform.getUser_id());
        // if return false, the user_id card is not expired yet.
        // else if it is true, user_id is legal to have a new card.
        Vector<Card> all_cards = Card.getAllCards();
        for(Card a_card : all_cards){
            if(a_card.getUser_id().equals(cardform.getUser_id())) {
                int days_diff = DateHandling.get_days_diff_with_today(a_card.getExpired_date());
                days_diff = (-1) * days_diff;
                System.out.println("days_diff: " + days_diff);
                if(days_diff <= DateHandling.card_expired_period) // if card is still in valid expired time
                    is_valid = false;
            }
        }
        System.out.println("is_valid = " + is_valid);
        return is_valid;
    }

    @Override
    public boolean validateObject() { // validate card form khop voi db
        User user = new User();
        if(user.check_a_user_existed(cardform.getUser_id()))
            return true;
        return false;
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
        card_number = getRandomString(this.card_length);
        Vector<Card> existedCard = card.getAllCards();
        for(int index = 0; index < existedCard.size(); index++){
            if(card_number.equals(existedCard.get(index).getCard_number()))
                is_ok = false;
        }
        return is_ok;
    }
    public void genACard(){
        while(genACardNumber() == false){
            continue;
        }
        String user_id = cardform.getUser_id();    // User_id ko phai la ma so sinh vien
        String is_student = "NO";
        if(type_of_guest.equals("student"))
            is_student = "YES";
        String activate_code = getRandomString(this.code_length);
        String expired_date = DateHandling.getADate(DateHandling.card_expired_period);
        card = new Card(user_id, "NO", is_student, expired_date,activate_code,this.card_number);
    }
    public String getRandomString(int length){
        RandomString gen = new RandomString(length, ThreadLocalRandom.current());
        String cardNumber = gen.nextString();
        return cardNumber;
    }
    public boolean isAddCardSuccess(){
        return card.getValid();
    }
}
