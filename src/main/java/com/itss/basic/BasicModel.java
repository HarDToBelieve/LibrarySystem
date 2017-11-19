package com.itss.basic;

import com.itss.Entity.BookInfo;
import com.itss.utilities.APIClient;
import org.json.JSONArray;

import java.util.HashMap;
import java.util.Vector;

/**
 * Created by HarDToBelieve on 11/15/2017.
 */
public interface BasicModel {
    static final String host = "http://2flf3l7wp7.hardtobelieve.me/";
    boolean checkConnection();
    void getByID(String ID);

    void add();
    boolean validObject();

    static JSONArray getAll(String folder) {
        String endpoint = folder + "/getall.php";
        try {
            HashMap<String, Object> result = APIClient.get(host + endpoint, new HashMap<>());
            if ( result.get("status_code").equals("Success") ) {
                return (JSONArray) result.get("result");
            }
            else return new JSONArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new JSONArray();
    }

    static JSONArray getUnique(String folder, HashMap<String, String> dict) {
        String endpoint = folder + "/get.php";
        try {
            HashMap<String, Object> result = APIClient.get(host + endpoint, dict);
            if ( result.get("status_code").equals("Success") ) {
                return (JSONArray) result.get("result");

            }
            else return new JSONArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new JSONArray();
    }
}
