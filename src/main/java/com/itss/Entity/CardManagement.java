package com.itss.Entity;

import com.itss.basic.BasicModel;
import com.itss.utilities.APIClient;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.smartcardio.Card;
import java.util.HashMap;
import java.util.Vector;

/**
 * Created by BuiAnhVu on 11/20/2017.
 */
public class CardManagement implements BasicModel {
    String activateCode;
    String expiredDate;
    String cardNumber;
    String isActivated;
    String isStudent;

    public String getUserID() {
        return userID;
    }

    String userID;
    private boolean valid;

    public CardManagement(){

    }
    public CardManagement(String activateCode, String expiredDate, String cardNumber, String isActivated, String isStudent, String userID){
        this.activateCode = activateCode;
        this.expiredDate = expiredDate;
        this.cardNumber = cardNumber;
        this.isActivated = isActivated;
        this.isStudent = isStudent;
        this.userID = userID;
    }
    public String getExpiredDate() {
        return expiredDate;
    }

    public String getCardNumber() {
        return cardNumber;
    }
    public String getActivateCode() {
        return activateCode;
    }


    public String isActivated() {
        return isActivated;
    }

    public String isStudent() {
        return isStudent;
    }


    public void updateCard() {
        this.add();
    }

    @Override
    public boolean checkConnection() {
        return false;
    }

    @Override
    public void getByID(String ID) {

    }

    @Override
    public void add() {
        HashMap<String, String> data = new HashMap<>();
        data.put("card_number", cardNumber);
        data.put("user_id", userID);
        data.put("is_active", isActivated );
        data.put("is_student", isStudent);
        data.put("expired_date", expiredDate);
        data.put("activate_code", activateCode);

        HashMap<String, Object> result = null;
        String endpoint = "bookinfo/post.php";
        try {
            result = APIClient.post(BookInfo.host + endpoint, data);
            valid = result.get("status_code").equals("Success");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean validObject() {
        return false;
    }
    static Vector<CardManagement> dumpCards (Object lineItems) {
        Vector<CardManagement> cards = new Vector<>();
        for (Object o : (JSONArray) lineItems) {
            JSONObject jsonLineItem = (JSONObject) o;
            String activatedCode = jsonLineItem.getString("activate_code");
            String expiredDate = jsonLineItem.getString("expired_date");
            String cardNumber = jsonLineItem.getString("card_number");
            String isActivated = jsonLineItem.getString("is_active");
            String isStudent = jsonLineItem.getString("is_student");
            String userID = jsonLineItem.getString("user_id");
            CardManagement tmp = new CardManagement(activatedCode, expiredDate, cardNumber, isActivated, isStudent, userID);
            cards.add(tmp);
        }
        return cards;
    }

    public static Vector<CardManagement> getAllCards() {
        return dumpCards(BasicModel.getAll("card"));
    }



}
