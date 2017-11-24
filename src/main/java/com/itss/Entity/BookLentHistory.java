package com.itss.Entity;
import com.itss.basic.BasicModel;
import com.itss.utilities.APIClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Vector;
import com.itss.basic.BasicModel;
/**
 * Created by BuiAnhVu on 11/20/2017.
 */
public class BookLentHistory implements BasicModel{
    String userID;
    String copyID;
    String card_number;
    String date;
    String isReturned;

    public BookLentHistory(String userID, String copyID, String card_number, String date, String isReturned) {
        this.userID = userID;
        this.copyID = copyID;
        this.card_number = card_number;
        this.date = date;
        this.isReturned = isReturned;
    }

    boolean valid;

    public String getUserID() {
        return userID;
    }

    public String getCopyID() {
        return copyID;
    }

    public String getCard_number() {
        return card_number;
    }

    public String getDate() {
        return date;
    }

    public String getIsReturned() {
        return isReturned;
    }

    public void getBorrowedBooksOf(){

    }
    public void updateBookStatus(){

    }

    @Override
    public boolean checkConnection() {
        return true;
    }

    @Override
    public void getByID(String copyID) {
        String endpoint = "booklenthistory/get.php";
        HashMap<String, String> dict = new HashMap<>();
        dict.put("copyID", copyID);
        try {
            HashMap<String, Object> result = APIClient.get(BookInfo.host + endpoint, dict);
            if ( result.get("status_code").equals("Success") ) {
                JSONObject o = (JSONObject) result.get("result");
                this.copyID = o.getString("copyID");
                this.date = o.getString("date");
                this.userID = o.getString("user_id");
                this.card_number = o.getString("card_number");
                this.valid = true;
            }
            else {
                this.valid = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void add() {
    }

    @Override
    public boolean validObject() {
        return false;
    }
    static Vector<BookLentHistory> dumpCopy (Object lineItems) {
        Vector<BookLentHistory> booklents = new Vector<>();
        for (Object o : (JSONArray) lineItems) {
            JSONObject jsonLineItem = (JSONObject) o;
            String userID1 = jsonLineItem.getString("user_id");
            String copyID1 = jsonLineItem.getString("copyID");
            String date1 = jsonLineItem.getString("date");
            String isReturned1 = jsonLineItem.getString("is_returned");
            String cardNumber1 = jsonLineItem.getString("card_number");

            BookLentHistory tmp = new BookLentHistory(userID1, copyID1, cardNumber1, date1, isReturned1);
            booklents.add(tmp);
        }
        return booklents;
    }
    public static Vector<BookLentHistory> getUniqueCopy(HashMap<String, String> dict) {
        return dumpCopy(BasicModel.getUnique("booklenthistory", dict));
    }
    public void changeStatusOfLent(){

    }
}
