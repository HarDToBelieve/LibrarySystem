package com.itss.basic;

import javax.swing.*;

/**
 * Created by HarDToBelieve on 11/15/2017.
 */
public interface BasicView {
    BasicController getController();

    void setController(BasicController bc);

    void updateModel();
    void updateViewFromController();

    void submit();
    void close();
    void error();

    JPanel getMainPanel();
}
