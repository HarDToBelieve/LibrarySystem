package com.itss.Boundary;

import com.itss.basic.BasicController;
import com.itss.basic.BasicView;

import javax.swing.*;
import java.awt.event.*;

public class UserLogoutForm extends JDialog implements BasicView {
    private JPanel contentPane;
    private JButton btnYES;

    public UserLogoutForm() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(btnYES);

        btnYES.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                submit();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                close();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                close();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    @Override
    public BasicController getController() {
        return null;
    }

    @Override
    public void setController(BasicController bc) {

    }

    @Override
    public void updateModel() {

    }

    @Override
    public void updateViewFromController() {

    }

    @Override
    public void submit() {
        System.exit(0);
    }

    @Override
    public void close() {

    }

    @Override
    public void error() {

    }

    @Override
    public void refresh() {

    }

    @Override
    public JPanel getMainPanel() {
        return contentPane;
    }
}
