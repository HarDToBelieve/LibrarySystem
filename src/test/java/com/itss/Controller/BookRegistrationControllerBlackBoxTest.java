package com.itss.Controller;

import com.itss.Boundary.Forms.BookForm;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by HarDToBelieve on 11/21/2017.
 */
public class BookRegistrationControllerBlackBoxTest {
    @Test
    public void testCase01() {
        BookRegistrationController brc = new BookRegistrationController();
        BookForm bf = new BookForm("Digital Fortress", "Dan Brown", "NXB Van Hoa", "123asd");
        brc.setForm(bf);
        assertEquals(true, brc.validateObject());
    }

    @Test
    public void testCase02() {
        BookRegistrationController brc = new BookRegistrationController();
        BookForm bf = new BookForm(".//././/.", "Dan Brown", "NXB Van Hoa", "123asd");
        brc.setForm(bf);
        assertEquals(false, brc.validateObject());
    }

    @Test
    public void testCase03() {
        BookRegistrationController brc = new BookRegistrationController();
        BookForm bf = new BookForm("Digital Fortress", "././././.", "NXB Van Hoa", "123asd");
        brc.setForm(bf);
        assertEquals(false, brc.validateObject());
    }

    @Test
    public void testCase04() {
        BookRegistrationController brc = new BookRegistrationController();
        BookForm bf = new BookForm("Digital Fortress", "Dan Brown", "!@#@!#@!#!@#@", "123asd");
        brc.setForm(bf);
        assertEquals(false, brc.validateObject());
    }

    @Test
    public void testCase05() {
        BookRegistrationController brc = new BookRegistrationController();
        BookForm bf = new BookForm("Digital Fortress", "Dan Brown", "NXB Van Hoa", "@#!$#$#@%");
        brc.setForm(bf);
        assertEquals(false, brc.validateObject());
    }
}