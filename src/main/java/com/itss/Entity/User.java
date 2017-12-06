package com.itss.Entity;

import com.itss.basic.BasicModel;
import com.itss.utilities.APIClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Vector;

import static com.itss.basic.BasicModel.getUnique;
import static com.itss.basic.BasicModel.getAll;


/**
 * Created by HarDToBelieve on 11/25/2017.
 */
public class User implements BasicModel {
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

    public User(String user_id, String name, String address, String date, String email, String job) {
        this.user_id = user_id;
        this.name = name;
        this.address = address;
        this.date = date;
        this.email = email;
        this.job = job;
    }
    public User(){

    }

    public User(User tmp) {
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

    /**
     * get and set information of a user by using user_id
     * @param ID user_id of a user, that is unique
     */
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
                this.address = o.getString("address");
                this.date = o.getString("date_of_birth");
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

    /**
     * add a new user to the User table in database
     */
    @Override
    public void add() {
        HashMap<String, String> data = new HashMap<>();
        data.put("user_id", user_id);
        data.put("name", name);
        data.put("address", address);
        data.put("email", email);
        data.put("job", job);
        data.put("date_of_birth", date);

        HashMap<String, Object> result = null;
        String endpoint = "user/post.php";
        try {
            result = APIClient.post(User.host + endpoint, data);
            valid = result.get("status_code").equals("Success");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * extract information gotten from database
     * @param lineItems
     * @return a vertor of Users
     */
    static Vector<User> dumpUser (Object lineItems) {
        Vector<User> users = new Vector<>();
        for (Object o : (JSONArray) lineItems) {
            JSONObject jsonLineItem = (JSONObject) o;
            String user_id = jsonLineItem.getString("user_id");
            String name = jsonLineItem.getString("name");
            String address = jsonLineItem.getString("address");
            String birth = jsonLineItem.getString("date_of_birth");
            String email = jsonLineItem.getString("email");
            String job = jsonLineItem.getString("job");

            User user = new User(user_id, name, address, birth, email, job);
            user.setValid(true);
            users.add(user);
        }
        return users;
    }

    /**
     * get one user with exact information that matches the parameter
     * @param dict a HashMap that matches the desired user
     * @return
     */
    public static User getUniqueUser(HashMap<String, String> dict) {
        try {
            return dumpUser(getUnique("user", dict)).get(0);
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

    /**
     *
     * @return all current users that appears in database
     */
    public static Vector<User> getAllUsers() {
        return dumpUser(getAll("user"));
    }
    public static boolean check_a_user_existed(String user_id){
        Vector<User> all_users = getAllUsers();
        for(User a_user : all_users){
            if(a_user.getUser_id().equals(user_id))
                return true;
        }
        return false;
    }

    /**
     * get job of a user
     * @param user_id
     * @return "student" or "guest" or null
     */
    public static String getJobOfAUser(String user_id){
        HashMap<String, String> dict=  new HashMap<>();
        dict.put("user_id", user_id);
        User a_user =  getUniqueUser(dict);
        return a_user.getJob();
    }

}
