package com.itss.Boundary;

import com.itss.Controller.ObserverController;
import com.itss.basic.BasicView;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;

/**
 * Created by HarDToBelieve on 11/25/2017.
 */
public class MainWindow extends JFrame {
    private JTabbedPane menuTabbed;

    public MainWindow(String username, String job, String cardno) {
        super("Library System");
        getContentPane().add(menuTabbed);
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        this.setPreferredSize(new Dimension(1000, 600));
        Vector<BasicView> tmp = new Vector<>();

        if ( job.equals("librarian") ) {
            BookRegistrationForm brf = new BookRegistrationForm();
            menuTabbed.add(brf.getMainPanel(), "Add new book");
            tmp.add(brf);

            BookCopyRegistrationForm bcrf = new BookCopyRegistrationForm();
            menuTabbed.add(bcrf.getMainPanel(), "Add new copy");
            tmp.add(bcrf);

            BorrowBookForm bbv = new BorrowBookForm(username, cardno);
            menuTabbed.add(bbv.getMainPanel(), "Borrow Book");
            tmp.add(bbv);

            BookDeleteForm bdv = new BookDeleteForm();
            menuTabbed.add(bdv.getMainPanel(), "Delete book");
            tmp.add(bdv);

            ReturnBookForm rbv = new ReturnBookForm();
            menuTabbed.add(rbv.getMainPanel(), "Return Book");
            tmp.add(rbv);

            IssueCardForm icv = new IssueCardForm();
            menuTabbed.add(icv.getMainPanel(), "Issue Card");
            tmp.add(icv);
        }

        ObserverController.setViews(tmp);
        UserLogoutForm ulf = new UserLogoutForm();
        menuTabbed.add(ulf.getMainPanel(), "Logout");


        menuTabbed.setTabPlacement(JTabbedPane.LEFT);

//        menuTabbed.addChangeListener(e -> {
//            if ( menuTabbed.getSelectedIndex() == 0 ) {
//                menuTabbed.setComponentAt(0, new BookRegistrationForm().getMainPanel());
//            }
//            else if ( menuTabbed.getSelectedIndex() == 1 )
//                menuTabbed.setComponentAt(1, new BookCopyRegistrationForm().getMainPanel());
//            else if ( menuTabbed.getSelectedIndex() == 2 )
//                menuTabbed.setComponentAt(2, new BookDeleteForm().getMainPanel());
//            else if ( menuTabbed.getSelectedIndex() == 3 )
//                menuTabbed.setComponentAt(3, new ReturnBookForm().getMainPanel());
//            else if ( menuTabbed.getSelectedIndex() == 4 )
//                menuTabbed.setComponentAt(4, new IssueCardForm().getMainPanel());
//        });
    }
}
