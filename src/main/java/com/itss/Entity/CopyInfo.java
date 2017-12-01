package com.itss.Entity;

import com.itss.basic.BasicModel;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Vector;

import static com.itss.basic.BasicModel.getUnique;

/**
 * Created by Administrator on 12/1/2017.
 */
public class CopyInfo implements BasicModel {
    public String getCopyID() {
        return copyID;
    }

    public String getType() {
        return type;
    }

    public double getPrice() {
        return price;
    }

    public String getBookID() {
        return bookID;
    }

    public String getCopyStatus() {
        return copyStatus;
    }

    public String getTitle() {
        return title;
    }

    private String copyID;
    private String type;
    private double price;
    private String bookID;
    private String copyStatus;
    private String title;

    public CopyInfo(String copyID, String type, double price, String bookID, String copyStatus, String title) {
        this.copyID = copyID;
        this.type = type;
        this.price = price;
        this.bookID = bookID;
        this.copyStatus = copyStatus;
        this.title = title;
    }

    public CopyInfo() {

    }

    public static Vector<CopyInfo> getCopyByID(String copyID_to_find){
        Vector<CopyInfo> bookCopyVector = new Vector<>();
        HashMap<String, String> dict = new HashMap<>();
        dict.put("copyID", copyID_to_find);
        String folder = "bookcopyinfo";
        JSONArray array = getUnique(folder, dict);
        for(Object o : array){
            JSONObject jsonObject = (JSONObject) o;
            String type = jsonObject.getString("type");
            String copyID = jsonObject.getString("copyID");
            String bookID = jsonObject.getString("bookID");
            String copyStatus = jsonObject.getString("copyStatus");
            String title = jsonObject.getString("title");
            String price = jsonObject.getString("price");
            CopyInfo tmp = new CopyInfo(copyID, type, Double.parseDouble(price), bookID, copyStatus, title);
            bookCopyVector.add(tmp);
        }
        return bookCopyVector;
    }

    public static Vector<CopyInfo> getCopyByCopyTitle(String title_to_find){
        Vector<CopyInfo> bookCopyVector = new Vector<>();
        HashMap<String, String> dict = new HashMap<>();
        dict.put("title", title_to_find);
        String folder = "bookcopyinfo";
        JSONArray array = getUnique(folder, dict);
        for(Object o : array){
            JSONObject jsonObject = (JSONObject) o;
            String type = jsonObject.getString("type");
            String copyID = jsonObject.getString("copyID");
            String bookID = jsonObject.getString("bookID");
            String copyStatus = jsonObject.getString("copyStatus");
            String title = jsonObject.getString("title");
            String price = jsonObject.getString("price");
            CopyInfo tmp = new CopyInfo(copyID, type, Double.parseDouble(price), bookID, copyStatus, title);
            bookCopyVector.add(tmp);
        }
        return bookCopyVector;
    }

    public void delete_row() {

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

    }

    @Override
    public boolean validObject() {
        return true;
    }
}
