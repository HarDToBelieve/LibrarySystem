package com.itss.Controller;

import com.itss.Boundary.Forms.LoginForm;
import com.itss.Boundary.MainWindow;
import com.itss.Entity.UserInfo;
import com.itss.basic.BasicController;

import java.util.Vector;

/**
 * Created by HarDToBelieve on 11/25/2017.
 */
public class UserLoginController implements BasicController {
    UserInfo u;
    @Override
    public Vector<Object> getModel() {
        return null;
    }

    @Override
    public boolean validateObject() {
        return true;
    }

    @Override
    public void updateData() {

    }

    @Override
    public void selectData() {

    }

    public void setForm(LoginForm lf) {

    }

    public void login() {
        MainWindow mw = new MainWindow();
        mw.pack();
        mw.setVisible(true);
    }
}
