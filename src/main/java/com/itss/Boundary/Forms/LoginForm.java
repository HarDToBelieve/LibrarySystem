package com.itss.Boundary.Forms;

import com.itss.basic.BasicForm;

/**
 * Created by HarDToBelieve on 11/25/2017.
 */
public class LoginForm implements BasicForm {
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    private String username;
    private String password;

    public LoginForm(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
