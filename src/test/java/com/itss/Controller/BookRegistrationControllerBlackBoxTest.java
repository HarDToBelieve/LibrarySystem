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
        BookForm bf = new BookForm("arnmyr", "w[e5o7k", ";otdk", "aslf");
        brc.setForm(bf);
        assertEquals(false, brc.validateObject());
    }

    @Test
    public void testCase02() {
        BookRegistrationController brc = new BookRegistrationController();
        BookForm bf = new BookForm("aryh", "123 23", "adt63", "asds");
        brc.setForm(bf);
        assertEquals(true, brc.validateObject());
    }

    @Test
    public void testCase03() {
        BookRegistrationController brc = new BookRegistrationController();
        BookForm bf = new BookForm("wlkejm6", "AStg ", "as4     ", "asdg ger5 3");
        brc.setForm(bf);
        assertEquals(true, brc.validateObject());
    }

    @Test
    public void testCase04() {
        BookRegistrationController brc = new BookRegistrationController();
        BookForm bf = new BookForm("a132bc", "asd_+)+_+_", "{}{}wqe$", "1323");
        brc.setForm(bf);
        assertEquals(false, brc.validateObject());
    }

    @Test
    public void testCase05() {
        BookRegistrationController brc = new BookRegistrationController();
        BookForm bf = new BookForm("abc_ASDyy", "asasdaa3d", "wqe@%^&", "1323&AS");
        brc.setForm(bf);
        assertEquals(false, brc.validateObject());
    }
}