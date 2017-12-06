package com.itss.Entity;

import com.itss.basic.BasicModel;
import com.itss.utilities.APIClient;
import com.itss.utilities.DateHandling;
import org.json.JSONArray;
import org.json.JSONObject;
import static com.itss.basic.BasicModel.getAll;
import static com.itss.basic.BasicModel.getUnique;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Vector;

public class Card implements BasicModel {
    public Card(String user_id, String is_active, String is_student, String expired_date, String activate_code, String card_number) {
        this.user_id = user_id;
        this.is_active = is_active;
        this.is_student = is_student;
        this.expired_date = expired_date;
        this.activate_code = activate_code;
        this.card_number = card_number;
    }
    public Card(){
    }

    public String getUser_id() {
        return user_id;
    }

    public String getIs_active() {
        return is_active;
    }

    public String getIs_student() {
        return is_student;
    }

    public String getExpired_date() {
        return expired_date;
    } // Fix date

    public String getActivate_code() {
        return activate_code;
    }

    public String getCard_number() {
        return card_number;
    }

    public Boolean getValid() {
        return valid;
    }

    private String card_number;
    private String user_id;
    private String is_active;
    private String is_student;
    private String expired_date;
    private String activate_code;

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    private Boolean valid;

    @Override
    public boolean checkConnection() {
        return true;
    }

    @Override
    public void getByID(String ID) {

    }

    /**
     * add a card with fully added information to the database on remote
     */
    @Override
    public void add() {
        HashMap<String, String> data = new HashMap<>();
        data.put("user_id", user_id);
        data.put("is_active", is_active);
        data.put("is_student", is_student);
        data.put("expired_date", expired_date);
        data.put("activate_code", activate_code);
        data.put("card_number", card_number);

        HashMap<String, Object> result = null;
        String endpoint = "card/post.php";
        try {
            result = APIClient.post(Card.host + endpoint, data);
            valid = result.get("status_code").equals("Success");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean validObject() {
        return false;
    }

    /**
     * extract information getten from databse
     * @param lineItems
     * @return a Vertor of Cards
     */
    static Vector<Card> dumpCards (Object lineItems) {
        Vector<Card> cards = new Vector<>();
        for (Object o : (JSONArray) lineItems) {
            JSONObject jsonLineItem = (JSONObject) o;
            String user_id = jsonLineItem.getString("user_id");
            String is_active = jsonLineItem.getString("is_active");
            String is_student = jsonLineItem.getString("is_student");
            String expired_date = jsonLineItem.getString("expired_date");
            String activate_code = jsonLineItem.getString("activate_code");
            String card_number = jsonLineItem.getString("card_number");
            Card tmp = new Card(user_id, is_active, is_student, expired_date, activate_code, card_number);
            tmp.setValid(true);
            cards.add(tmp);
        }
        return cards;
    }

    /**
     *
     * @return all current card on the database
     */
    public static Vector<Card> getAllCards() {
        return dumpCards(getAll("card"));
    }

    /**
     * check if a card_number is in database
     * @param card_number
     * @return true if existed, false if not.
     */
    public boolean check_a_card_existed(String card_number){
        Vector<Card> all_cards = getAllCards();
        for(Card a_card : all_cards){
            if(a_card.getCard_number().equals(card_number))
                return true;
        }
        return false;
    }

    /**
     * get a username by user card number
     * @param card_number
     * @return user's name
     */
    public static String getUserIdByCardNumber(String card_number){
        HashMap<String, String> dict = new HashMap<>();
        dict.put("card_number", card_number);
        Vector<Card> cards = dumpCards(getUnique("card", dict));
        if(cards.size() == 0)
            return null;
        else
            return  cards.get(0).getUser_id();
    }

    /**
     * get card number by using user_id
     * @param userid id of user
     * @return card_number of that user
     */
    public static String getCardNumberByUserID(String userid) {
        HashMap<String, String> dict = new HashMap<>();
        dict.put("user_id", userid);
        Vector<Card> cards = dumpCards(getUnique("card", dict));
        if (cards.size() == 0)
            return "";
        else
            return cards.get(0).getCard_number();
    }

}
