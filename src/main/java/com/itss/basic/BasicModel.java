package com.itss.basic;

import java.util.HashMap;

/**
 * Created by HarDToBelieve on 11/15/2017.
 */
public interface BasicModel {
    static final String host = "http://localhost/bookinfo";
    boolean checkConnection();
    void getByID(String ID);

    void add();
    boolean validObject();
}
