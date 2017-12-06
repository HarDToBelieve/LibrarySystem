package com.itss.Boundary;

import com.itss.Controller.ObserverController;
import com.itss.basic.BasicView;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
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

        try {
            Image img = ImageIO.read(new File(System.getProperty("user.dir") + "\\resource\\Webp.net-resizeimage (2).png"));
            Icon icon = new ImageIcon(img);
            menuTabbed.addTab("", icon, new JPanel());
        } catch (IOException e) {
            e.printStackTrace();
        }

        if ( job.equals("librarian") ) {
            BookRegistrationForm brf = new BookRegistrationForm();
            menuTabbed.add(brf.getMainPanel(), "<html><body><table height='1000'>Add new book</table></body></html>");
            tmp.add(brf);

            BookCopyRegistrationForm bcrf = new BookCopyRegistrationForm("");
            menuTabbed.add(bcrf.getMainPanel(), "<html><body><table height='1000'>Add new copy</table></body></html>");
            tmp.add(bcrf);

            BookDeleteForm bdv = new BookDeleteForm();
            menuTabbed.add(bdv.getMainPanel(), "<html><body><table height='1000'>Delete book</table></body></html>");
            tmp.add(bdv);

            ReturnBookForm rbv = new ReturnBookForm();
            menuTabbed.add(rbv.getMainPanel(), "<html><body><table height='1000'>Return Book</table></body></html>");
            tmp.add(rbv);

            IssueCardForm icv = new IssueCardForm();
            menuTabbed.add(icv.getMainPanel(), "<html><body><table height='1000'>Issue Card</table></body></html>");
            tmp.add(icv);
        }
        else if ( job.equals("student") ) {
            BorrowBookForm bbv = new BorrowBookForm(username, cardno);
            menuTabbed.add(bbv.getMainPanel(), "<html><body><table height='1000'>Borrow Book</table></body></html>");
            tmp.add(bbv);
        }

        ObserverController.setViews(tmp);
        UserLogoutForm ulf = new UserLogoutForm();
        menuTabbed.add(ulf.getMainPanel(), "<html><body><table height='1000'>Logout</table></body></html>");

        menuTabbed.setSelectedIndex(1);

        menuTabbed.setTabPlacement(JTabbedPane.LEFT);

        menuTabbed.addChangeListener(e -> {
            if ( menuTabbed.getSelectedIndex() == 0 ) {
                menuTabbed.setSelectedIndex(1);
            }
        });
    }
}
