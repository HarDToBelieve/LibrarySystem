package com.itss.Entity;

import com.itss.basic.BasicModel;
import com.itss.utilities.APIClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Vector;

/**
 * Created by HarDToBelieve on 11/25/2017.
 */
public class UserInfo implements BasicModel {
    private String job;
    private String user_id;
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

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    private boolean valid;

    public UserInfo (String user_id, String name, String address, String date, String email, String job) {
        this.user_id = user_id;
        this.name = name;
        this.address = address;
        this.date = date;
        this.email = email;
        this.job = job;
    }

    public UserInfo (UserInfo tmp) {
        this.user_id = tmp.getUser_id();
        this.name = tmp.getName();
        this.address = tmp.getAddress();
        this.date = tmp.getDate();
        this.email = tmp.getEmail();
        this.job = tmp.getJob();
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
                this.user_id = o.getString("user_id");
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
        data.put("user_id", user_id);
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

    static Vector<UserInfo> dumpUser (Object lineItems) {
        Vector<UserInfo> users = new Vector<>();
        for (Object o : (JSONArray) lineItems) {
            JSONObject jsonLineItem = (JSONObject) o;
            String user_id = jsonLineItem.getString("user_id");
            String name = jsonLineItem.getString("name");
            String address = jsonLineItem.getString("address");
            String birth = jsonLineItem.getString("date");
            String email = jsonLineItem.getString("email");
            String job = jsonLineItem.getString("job");

            UserInfo user = new UserInfo(user_id, name, address, birth, email, job);
            user.setValid(true);
            users.add(user);
        }
        return users;
    }

    public static UserInfo getUniqueUser(HashMap<String, String> dict) {
        try {
            return dumpUser(UserInfo.getUniqueUser(dict)).get(0);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public boolean validObject() {
        return valid;
    }

    public String getUser_id() {
        return user_id;
    }
}
