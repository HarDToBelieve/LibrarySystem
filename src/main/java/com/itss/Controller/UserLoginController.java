package com.itss.Controller;

import com.itss.Boundary.Forms.LoginForm;
import com.itss.Boundary.MainWindow;
import com.itss.Entity.Card;
import com.itss.Entity.User;
import com.itss.basic.BasicController;

import java.util.HashMap;
import java.util.Vector;

/**
 * Created by HarDToBelieve on 11/25/2017.
 */
public class UserLoginController implements BasicController {
    User u;
    LoginForm lf;
    @Override
    public Vector<Object> getModel() {
        return null;
    }

    @Override
    public boolean validateObject() {
        HashMap<String, String> dict = new HashMap<>();
        dict.put("email", lf.getUsername());
        dict.put("password", lf.getPassword());
        User tmp = User.getUniqueUser(dict);
        if ( tmp == null ) {
            u = null;
            return false;
        }
        else {
            u = new User(tmp);
            return true;
        }
    }

    @Override
    public void updateData() {

    }

    @Override
    public void selectData() {

    }

    public void setForm(LoginForm lf) {
        this.lf = lf;
    }

    public MainWindow login() {
        if ( u == null ) {
            return null;
        }
        else {
            MainWindow mw = new MainWindow(u.getName(), u.getJob(), Card.getCardNumberByUserID(u.getUser_id()));
            return mw;
        }
    }
}
