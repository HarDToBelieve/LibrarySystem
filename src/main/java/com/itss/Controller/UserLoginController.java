package com.itss.Controller;

import com.itss.Boundary.Forms.LoginForm;
import com.itss.Boundary.MainWindow;
import com.itss.Entity.UserInfo;
import com.itss.basic.BasicController;

import javax.swing.*;
import java.util.HashMap;
import java.util.Vector;

/**
 * Created by HarDToBelieve on 11/25/2017.
 */
public class UserLoginController implements BasicController {
    UserInfo u;
    LoginForm lf;
    @Override
    public Vector<Object> getModel() {
        return null;
    }

    @Override
    public boolean validateObject() {
        HashMap<String, String> dict = new HashMap<>();
        dict.put("name", lf.getUsername());
        dict.put("password", lf.getPassword());
        UserInfo tmp = UserInfo.getUniqueUser(dict);
        if ( tmp == null ) {
            u = null;
            return false;
        }
        else {
            u = new UserInfo(tmp);
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
            MainWindow mw = new MainWindow(u.getJob());
            return mw;
        }
    }
}
