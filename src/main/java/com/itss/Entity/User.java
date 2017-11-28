package com.itss.Entity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Vector;

import static com.itss.basic.BasicModel.getAll;
import static com.itss.basic.BasicModel.getUnique;


/**
 * Created by BuiAnhVu on 11/28/2017.
 */
public class User {
    String user_id;
    String date_of_birth;
    String email;
    String job;
    String address;
    boolean valid;

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public String getUser_id() {
        return user_id;
    }

    public User(String user_id, String date_of_birth, String email, String job, String address) {
        this.user_id = user_id;
        this.date_of_birth = date_of_birth;
        this.email = email;
        this.job = job;
        this.address = address;
    }

    static Vector<User> dumpUsers (Object lineItems) {
        Vector<User> users = new Vector<>();
        for (Object o : (JSONArray) lineItems) {
            JSONObject jsonLineItem = (JSONObject) o;
            String user_id = jsonLineItem.getString("user_id");
            String date_of_birth = jsonLineItem.getString("date_of_birth");
            String email = jsonLineItem.getString("email");
            String job = jsonLineItem.getString("job");
            String address = jsonLineItem.getString("address");

            User tmp = new User(user_id, date_of_birth, email, job, address);
            tmp.setValid(true);
            users.add(tmp);
        }
        return users;
    }
    public static Vector<User> getAllUsers() {
        return dumpUsers(getAll("user"));
    }
    public static boolean check_a_user_existed(String user_id){
        Vector<User> all_users = getAllUsers();
        for(User a_user : all_users){
            if(a_user.getUser_id().equals(user_id))
                return true;
        }
        return false;
    }
}
