package com.itss.Boundary;

import com.itss.Controller.BookCopyRegistrationController;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Vector;

/**
 * Created by HarDToBelieve on 11/25/2017.
 */
public class MainWindow extends JFrame {
    private JTabbedPane menuTabbed;

    public MainWindow(String job) {
        super("Library System");
        getContentPane().add(menuTabbed);
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        this.setPreferredSize(new Dimension(1000, 600));

        if ( job.equals("librarian") ) {
            BookRegistrationForm brf = new BookRegistrationForm();
            menuTabbed.add(brf.getMainPanel(), "Add new book");

            BookCopyRegistrationForm bcrf = new BookCopyRegistrationForm();
            menuTabbed.add(bcrf.getMainPanel(), "Add new copy");

            BookDeleteView bdv = new BookDeleteView();
            menuTabbed.add(bdv.getMainPanel(), "Delete book");
            ReturnBookView rbv = new ReturnBookView();
            menuTabbed.add(rbv.getMainPanel(), "Return Book");
            IssueCardView icv = new IssueCardView();
            menuTabbed.add(icv.getMainPanel(), "Issue Card");
        }

        UserLogoutForm ulf = new UserLogoutForm();
        menuTabbed.add(ulf.getMainPanel(), "Logout");


        menuTabbed.setTabPlacement(JTabbedPane.LEFT);

//        menuTabbed.addChangeListener(e -> {
//            if ( menuTabbed.getSelectedIndex() == 0 )
//                menuTabbed.setComponentAt(0, new BookRegistrationForm().getMainPanel());
//            else if ( menuTabbed.getSelectedIndex() == 1 )
//                menuTabbed.setComponentAt(1, new BookCopyRegistrationForm().getMainPanel());
//            else if ( menuTabbed.getSelectedIndex() == 2 )
//                menuTabbed.setComponentAt(2, new BookDeleteView().getMainPanel());
//            else if ( menuTabbed.getSelectedIndex() == 3 )
//                menuTabbed.setComponentAt(3, new ReturnBookView().getMainPanel());
//            else if ( menuTabbed.getSelectedIndex() == 4 )
//                menuTabbed.setComponentAt(4, new IssueCardView().getMainPanel());
//        });
    }
}
