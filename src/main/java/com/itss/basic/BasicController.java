package com.itss.basic;

import java.util.Vector;

/**
 * Created by HarDToBelieve on 11/15/2017.
 */
public interface BasicController {
    Vector<Object> getModel();

    boolean validateObject();
    void updateData() throws Exception;
    void selectData();
}
