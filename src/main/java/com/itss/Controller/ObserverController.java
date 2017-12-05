package com.itss.Controller;

import com.itss.basic.BasicView;

import java.util.Vector;

public class ObserverController {
    public static Vector<BasicView> controllers;

    public static void setViews(Vector<BasicView> tmp) {
        controllers = tmp;
    }

    public static void notify(int ind) {
        controllers.get(ind).refresh();
    }
}
