package com.itss.Controller;

import com.itss.Boundary.Forms.BookForm;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by HarDToBelieve on 12/6/2017.
 */
public class BookRegistrationControllerWhiteBoxTest {
    @Test
    public void CorrectBook() {
        BookRegistrationController brc = new BookRegistrationController();
        BookForm bf = new BookForm("Inferno", "Dan Brown", "NXB Giao Duc", "zxcdfg");
        brc.setForm(bf);
        assertEquals(true, brc.validateObject());
    }

    @Test
    public void WrongBook01() {
        BookRegistrationController brc = new BookRegistrationController();
        BookForm bf = new BookForm(".//././/.", "Dan Brown", "NXB Giao Duc", "zxcdfg");
        brc.setForm(bf);
        assertEquals(false, brc.validateObject());
    }

    @Test
    public void WrongBook02() {
        BookRegistrationController brc = new BookRegistrationController();
        BookForm bf = new BookForm("Inferno", "././././.", "NXB Giao Duc", "zxcdfg");
        brc.setForm(bf);
        assertEquals(false, brc.validateObject());
    }

    @Test
    public void WrongBook03() {
        BookRegistrationController brc = new BookRegistrationController();
        BookForm bf = new BookForm("Inferno", "Dan Brown", "!@#@!#@!#!@#@", "zxcdfg");
        brc.setForm(bf);
        assertEquals(false, brc.validateObject());
    }

    @Test
    public void WrongBook04() {
        BookRegistrationController brc = new BookRegistrationController();
        BookForm bf = new BookForm("Inferno", "Dan Brown", "NXB Giao Duc", "@#!$#$#@%");
        brc.setForm(bf);
        assertEquals(false, brc.validateObject());
    }
}