package com.itss.Controller;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by HarDToBelieve on 12/6/2017.
 */
public class BookCopyRegistrationControllerWhiteBoxTest {
    @Test
    public void testCase01() {
        BookCopyRegistrationController bcrc = new BookCopyRegistrationController();
        bcrc.setForm("PS0000", "BORROWABLE", "12.0", "12");
        assertEquals(true, bcrc.validateObject());
    }

    @Test
    public void testCase02() {
        BookCopyRegistrationController bcrc = new BookCopyRegistrationController();
        bcrc.setForm("PS0000", "REFERENCE", "12.0", "13");
        assertEquals(true, bcrc.validateObject());
    }

    @Test
    public void testCase03() {
        BookCopyRegistrationController bcrc = new BookCopyRegistrationController();
        bcrc.setForm("aopidnjfgfoqinf", "REFERENCE", "12.0", "14");
        assertEquals(false, bcrc.validateObject());
    }

    @Test
    public void testCase04() {
        BookCopyRegistrationController bcrc = new BookCopyRegistrationController();
        bcrc.setForm("@(&!$^*&!%$^", "BORROWABLE", "12.0", "12");
        assertEquals(false, bcrc.validateObject());
    }

    @Test
    public void testCase05() {
        BookCopyRegistrationController bcrc = new BookCopyRegistrationController();
        bcrc.setForm("PS0000", "IJ$WB%NFMO", "12.0", "12");
        assertEquals(false, bcrc.validateObject());
    }

    @Test
    public void testCase06() {
        BookCopyRegistrationController bcrc = new BookCopyRegistrationController();
        bcrc.setForm("PS0000", "BORROWABLE", "1asda", "12");
        assertEquals(false, bcrc.validateObject());
    }

    @Test
    public void testCase07() {
        BookCopyRegistrationController bcrc = new BookCopyRegistrationController();
        bcrc.setForm("PS0000", "BORROWABLE", "12.0", "zxgasrhj");
        assertEquals(false, bcrc.validateObject());
    }
}