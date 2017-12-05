package com.itss.Boundary;

import com.itss.Boundary.Forms.LoginForm;
import com.itss.Controller.UserLoginController;
import com.itss.basic.BasicController;
import com.itss.basic.BasicView;

import javax.swing.*;
import java.awt.event.*;

public class UserLoginForm extends JDialog implements BasicView {
    private JPanel contentPane;
    private JButton btnLogin;
    private JButton buttonCancel;
    private JTextField inputUsername;
    private JTextField inputPassword;
    private JButton btnGmail;

    private UserLoginController ulc;

    public UserLoginForm() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(btnLogin);

        ulc = new UserLoginController();
        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                submit();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    @Override
    public BasicController getController() {
        return ulc;
    }

    @Override
    public void setController(BasicController bc) {
        ulc = (UserLoginController) bc;
    }

    @Override
    public void updateModel() {

    }

    @Override
    public void updateViewFromController() {
        close();
        MainWindow mw = ulc.login();
        if ( mw == null ) {
            error();
        }
        else {
            mw.pack();
            mw.setVisible(true);
        }
    }

    @Override
    public void submit() {
        String username = inputUsername.getText();
        String password = inputPassword.getText();

        LoginForm lf = new LoginForm(username, password);
        ulc.setForm(lf);
        if ( ulc.validateObject() ) {
            updateViewFromController();
        }
        else {
            error();
        }
    }

    @Override
    public void close() {
        dispose();
    }

    @Override
    public void error() {
        JOptionPane.showMessageDialog(null, "Wrong username or password");
    }

    @Override
    public void refresh() {

    }

    public String MD5(String md5) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
        }
        return null;
    }

    @Override
    public JPanel getMainPanel() {
        return contentPane;
    }
}
