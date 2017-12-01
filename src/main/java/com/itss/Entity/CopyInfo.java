package com.itss.Entity;

import com.itss.basic.BasicModel;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Vector;
import static com.itss.basic.BasicModel.deleteUnique;

import static com.itss.basic.BasicModel.getUnique;

/**
 * Created by Administrator on 12/1/2017.
 */
public class CopyInfo extends BookCopyInfo {

    public String getCopyStatus() {
        return copyStatus;
    }

    public String getTitle() {
        return title;
    }


    private String copyStatus;
    private String title;

    public CopyInfo(String copyID, String type, double price, String bookID, String copyStatus, String title) {
        setCopyID(copyID);
        setType(type);
        setPrice(price);
        setBookID(bookID);
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
            String title = jsonObject.getString("title");
            String price = jsonObject.getString("price");
            CopyInfo tmp = new CopyInfo(copyID, type, Double.parseDouble(price), bookID, copyStatus, title);
            bookCopyVector.add(tmp);
        }
        return bookCopyVector;
    }

    public boolean delete_row() {
        HashMap<String, String> dict = new HashMap<>();
        dict.put("copyID", this.getCopyID());
        String folder = "bookinfo";
        return deleteUnique(folder, dict);
    }
    private String get_copy_status(){

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
