package com.itss.basic;

import java.util.HashMap;

/**
 * Created by HarDToBelieve on 11/15/2017.
 */
public interface BasicModel {
    boolean checkConnection();
    boolean getAll();

    boolean add() throws Exception;
}
