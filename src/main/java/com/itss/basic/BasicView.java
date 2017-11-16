package com.itss.basic;

/**
 * Created by HarDToBelieve on 11/15/2017.
 */
public interface BasicView {
    BasicController getController();

    void setController(BasicController bc);

    void updateModelFromView() throws Exception;
    void updateViewFromController();

    void submit();
    void close();
    void error();
}
