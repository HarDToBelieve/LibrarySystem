package com.itss.Controller;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by HarDToBelieve on 11/21/2017.
 */
public class BookCopyRegistrationControllerBlackBoxTest {
    @Test
    public void testCase01() {
        BookCopyRegistrationController bcrc = new BookCopyRegistrationController();
        bcrc.setForm("asda123", "Borrowable", "12.0", "12");
        assertEquals(false, bcrc.validateObject());
    }

    @Test
    public void testCase02() {
        BookCopyRegistrationController bcrc = new BookCopyRegistrationController();
        bcrc.setForm("abc", "asd", "wqe", "1323");
        assertEquals(false, bcrc.validateObject());
    }

    @Test
    public void testCase03() {
        BookCopyRegistrationController bcrc = new BookCopyRegistrationController();
        bcrc.setForm("abc", "asd", "wqe", "1323");
        assertEquals(false, bcrc.validateObject());
    }

    @Test
    public void testCase04() {
        BookCopyRegistrationController bcrc = new BookCopyRegistrationController();
        bcrc.setForm("abc", "asd", "wqe", "1323");
        assertEquals(false, bcrc.validateObject());
    }

    @Test
    public void testCase05() {
        BookCopyRegistrationController bcrc = new BookCopyRegistrationController();
        bcrc.setForm("abc", "asd", "wqe", "1323");
        assertEquals(false, bcrc.validateObject());
    }
}