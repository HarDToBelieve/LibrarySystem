package com.itss.Controller;

import com.itss.Boundary.Forms.LoginForm;
import com.itss.basic.BasicController;

import java.util.Vector;

/**
 * Created by HarDToBelieve on 11/25/2017.
 */
public class UserLoginController implements BasicController {
    @Override
    public Vector<Object> getModel() {
        return null;
    }

    @Override
    public boolean validateObject() {
        return false;
    }

    @Override
    public void updateData() {

    }

    @Override
    public void selectData() {

    }

    public void setForm(LoginForm lf) {

    }
}
