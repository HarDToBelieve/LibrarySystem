package com.itss.Entity;

import com.itss.basic.BasicModel;
import com.itss.utilities.APIClient;
import com.itss.utilities.DateHandling;
import org.json.JSONArray;
import org.json.JSONObject;
import static com.itss.basic.BasicModel.getUnique;
import static com.itss.basic.BasicModel.deleteUnique;

import java.awt.print.Book;
import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.*;


public class BookLentHistory implements BasicModel {
    private String user_id;
    private String copyID;
    private String date;
    private String card_number;
    private String is_returned;
    private double compensation;
    private String title;
    private String user_name;

    private Boolean valid;
    public final int overdue_days = 100;
    public final double fine_per_day = 0.03; //if book is overdue charge 0.03*1000 VND per day


    public BookLentHistory(String user_id, String copyID, String date, String card_number, String is_returned) {
        this.user_id = user_id;
        this.copyID = copyID;
        this.date = date;
        this.card_number = card_number;
        this.is_returned = is_returned;
    }
    public BookLentHistory(){};

    public double getCompensation() {
        return compensation;
    }

    public void setCompensation(double compensation) {
        this.compensation = compensation;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
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

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public void setCopyID(String copyID) {
        this.copyID = copyID;
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
        int days_diffs = DateHandling.get_days_diff_with_today(this.date);
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

    public static int countNumLentBook(String card_number_tofind) {
        Vector<BookLentHistory> list = getBooksByCardNumber(card_number_tofind);
        return list.size();
    }
    public static String getToday(){
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Calendar calendar = Calendar.getInstance();
        return formatter.format(calendar.getTime());
    }
    public void set_title_and_name_fromDB(){
        //this function workds after the user_id and copyID is updated.
        HashMap<String, String> dict = new HashMap<>();
        //set name
        dict.put("user_id", this.user_id);
        User user = User.getUniqueUser(dict);
        this.setUser_name(user.getName());
        //set title
         //find bookID in BookCopy table
        dict.clear();
        dict.put("copyID", this.copyID);
        BookCopyInfo bookCopyInfo = BookCopyInfo.getOneBookCopyInfo(dict);
        String bookID = bookCopyInfo.getBookID();
         // use bookID to find title of book in BookInfo table
        dict.clear();
        dict.put("bookID", bookID);
        BookInfo bookInfo = BookInfo.getUniqueBook(dict);
        this.setTitle(bookInfo.getTitle());
    }
}
