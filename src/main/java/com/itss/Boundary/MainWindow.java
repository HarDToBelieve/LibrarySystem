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

    public MainWindow(String job) {
        super("Library System");
        getContentPane().add(menuTabbed);
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );

        if ( job.equals("librarian") ) {
            BookRegistrationForm brf = new BookRegistrationForm();
            menuTabbed.add(brf.getMainPanel(), "Add new book");
            BookCopyRegistrationForm bcrf = new BookCopyRegistrationForm();
            menuTabbed.add(bcrf.getMainPanel(), "Add new copy");
            IssueCardView icv = new IssueCardView();
            menuTabbed.add(icv.getMainPanel(), "Issue Card");
        }

        UserLogoutForm ulf = new UserLogoutForm();
        menuTabbed.add(ulf.getMainPanel(), "Logout");


        menuTabbed.setTabPlacement(JTabbedPane.LEFT);
    }
}
