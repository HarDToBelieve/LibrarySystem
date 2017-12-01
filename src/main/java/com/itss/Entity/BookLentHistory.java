package com.itss.Entity;

import com.itss.basic.BasicModel;
import com.itss.utilities.APIClient;
import org.json.JSONArray;
import org.json.JSONObject;
import static com.itss.basic.BasicModel.getUnique;
import static com.itss.basic.BasicModel.deleteUnique;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;


public class BookLentHistory implements BasicModel {
    private String user_id;
    private String copyID;
    private String date;
    private String card_number;
    private String is_returned;
    private double compensation;
    private Boolean valid;
    public final int overdue_days = 100;

    public BookLentHistory(String user_id, String copyID, String date, String card_number, String is_returned) {
        this.user_id = user_id;
        this.copyID = copyID;
        this.date = date;
        this.card_number = card_number;
        this.is_returned = is_returned;
    }

    public double getCompensation() {
        return compensation;
    }

    public void setCompensation(double compensation) {
        this.compensation = compensation;
    }

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

    public String getIsReturned() {
        return is_returned;
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
        HashMap<String, String> data = new HashMap<>();
        data.put("user_id", user_id);
        data.put("copyID", copyID);
        data.put("date", date);
        data.put("card_number", card_number);
        data.put("is_returned", is_returned);
        HashMap<String, Object> result = null;
        String endpoint = "booklenthistory/post.php";
        try {
            result = APIClient.post(BookLentHistory.host + endpoint, data);
            valid = result.get("status_code").equals("Success");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean validObject() {
        return valid;
    }

    public static Vector<BookLentHistory> getBooksByCardNumber(String card_number_to_find){
        Vector<BookLentHistory> bookLentHistoryVector = new Vector<>();
        HashMap<String, String> dict = new HashMap<>();
        dict.put("card_number", card_number_to_find);
        String folder = "booklenthistory";
        JSONArray array = getUnique(folder, dict);
        for(Object o : array){
            JSONObject jsonObject = (JSONObject) o;
            String user_id = jsonObject.getString("user_id");
            String copyID = jsonObject.getString("copyID");
            String date = jsonObject.getString("date");
            String card_number = jsonObject.getString("card_number");
            String is_returned = jsonObject.getString("is_returned");
            BookLentHistory tmp = new BookLentHistory(user_id, copyID,date, card_number, is_returned);
            bookLentHistoryVector.add(tmp);
        }
        return bookLentHistoryVector;
    }

    public static Vector<BookLentHistory> getBooksByCopyID(String copyID_to_find){
        Vector<BookLentHistory> bookLentHistoryVector = new Vector<>();
        HashMap<String, String> dict = new HashMap<>();
        dict.put("copyID", copyID_to_find);
        String folder = "booklenthistory";
        JSONArray array = getUnique(folder, dict);
        for(Object o : array){
            JSONObject jsonObject = (JSONObject) o;
            String user_id = jsonObject.getString("user_id");
            String copyID = jsonObject.getString("copyID");
            String date = jsonObject.getString("date");
            String card_number = jsonObject.getString("card_number");
            String is_returned = jsonObject.getString("is_returned");
            BookLentHistory tmp = new BookLentHistory(user_id, copyID,date, card_number, is_returned);
            bookLentHistoryVector.add(tmp);
        }
        return bookLentHistoryVector;
    }
    public double calCompensation() throws ParseException {
        Double fine_per_day = 4000.0;
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date old_date = formatter.parse(this.date);
//        System.out.println("old date " + old_date.getTime());
        Calendar calendar = Calendar.getInstance();
        String str_today = formatter.format(calendar.getTime());
        Date today = formatter.parse(str_today);
//        System.out.println("today " + today.getTime());
        long diffs = today.getTime() - old_date.getTime();
        // cal culate the days difference and set the money
        int days_diffs = (int) (diffs/ 86400000);
        if(days_diffs < overdue_days)
            return 0.0;
        double fine = fine_per_day * days_diffs;
        this.compensation = fine;
        return fine;
    }
    public boolean delete_row(){
        HashMap<String, String> dict = new HashMap<>();
        dict.put("card_number", this.card_number);
        dict.put("copyID", this.copyID);
        String folder = "booklenthistory";
        return deleteUnique(folder, dict);
    }

    public int countNumLentBook(String card_number_tofind) {
        Vector<BookLentHistory> bookLentHistoryVector = new Vector<>();
        HashMap<String, String> dict = new HashMap<>();
        dict.put("card_number", card_number_tofind);
        String folder = "booklenthistory";
        JSONArray array = getUnique(folder, dict);
        for(Object o : array){
            JSONObject jsonObject = (JSONObject) o;
            String user_id = jsonObject.getString("user_id");
            String copyID = jsonObject.getString("copyID");
            String date = jsonObject.getString("date");
            String card_number = jsonObject.getString("card_number");
            String is_returned = jsonObject.getString("is_returned");
            BookLentHistory tmp = new BookLentHistory(user_id, copyID,date, card_number, is_returned);
            bookLentHistoryVector.add(tmp);
        }
        return bookLentHistoryVector.size();

    }
}
