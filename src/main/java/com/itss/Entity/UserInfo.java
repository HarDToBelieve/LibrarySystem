package com.itss.Entity;

import com.itss.basic.BasicModel;
import com.itss.utilities.APIClient;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by HarDToBelieve on 11/25/2017.
 */
public class UserInfo implements BasicModel {
    private String job;
    private int user_id;
    private String name;

    public String getJob() {
        return job;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getDate() {
        return date;
    }

    public String getEmail() {
        return email;
    }

    private String address;
    private String date;
    private String email;
    private boolean valid;

    public UserInfo (int user_id, String name, String address, String date, String email, String job) {
        this.user_id = user_id;
        this.name = name;
        this.address = address;
        this.date = date;
        this.email = email;
        this.job = job;
    }

    @Override
    public boolean checkConnection() {
        return false;
    }

    @Override
    public void getByID(String ID) {
        String endpoint = "user/get.php";
        HashMap<String, String> dict = new HashMap<>();
        dict.put("user_id", ID);
        try {
            HashMap<String, Object> result = APIClient.get(BookInfo.host + endpoint, dict);
            if ( result.get("status_code").equals("Success") ) {
                JSONObject o = (JSONObject) result.get("result");
                this.user_id = Integer.parseInt(o.getString("user_id"));
                this.name = o.getString("name");
                this.address = o.getString("date");
                this.email = o.getString("email");
                this.job = o.getString("job");
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
        HashMap<String, String> data = new HashMap<>();
        data.put("name", name);
        data.put("address", address);
        data.put("email", email);
        data.put("job", job);

        HashMap<String, Object> result = null;
        String endpoint = "user/post.php";
        try {
            result = APIClient.post(UserInfo.host + endpoint, data);
            valid = result.get("status_code").equals("Success");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean validObject() {
        return valid;
    }

    public int getUser_id() {
        return user_id;
    }
}
