package com.itss.Boundary;

import com.itss.Controller.BookCopyRegistrationController;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

/**
 * Created by HarDToBelieve on 11/25/2017.
 */
public class MainWindow extends JFrame {
    private JTabbedPane menuTabbed;

    public MainWindow() {
        super("Library System");
        getContentPane().add(menuTabbed);
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );

        BookRegistrationForm brf = new BookRegistrationForm();
        menuTabbed.add(brf.getMainPanel(), "Add new book");
        BookCopyRegistrationForm bcrf = new BookCopyRegistrationForm();
        menuTabbed.add(bcrf.getMainPanel(), "Add new copy");


        menuTabbed.setTabPlacement(JTabbedPane.LEFT);
    }
}
