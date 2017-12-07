package com.itss.Controller;

import com.itss.Entity.BookLentHistory;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by BuiAnhVu on 12/7/2017.
 */
public class ReturnBookControllerTest {
    @Test
    public void getLentBooksByCardNumber() throws Exception {
        ReturnBookController returnBookController = new ReturnBookController();
        returnBookController.getLentBooksByCardNumber("7626276627");
        BookLentHistory bookLentHistory = ReturnBookController.list_books.get(0);
        BookLentHistory for_compare = new BookLentHistory();
        for_compare.setDate("07/12/2017");
        for_compare.setIs_returned("NO");
        for_compare.setCard_number("7626276627");
        for_compare.setCopyID("ED0003_4");
        for_compare.setUser_id("TEST_01");
        assertEquals(bookLentHistory.getCard_number(), for_compare.getCard_number());
        assertEquals(bookLentHistory.getDate(), for_compare.getDate());
        assertEquals(bookLentHistory.getIs_returned(), for_compare.getIs_returned());
        assertEquals(bookLentHistory.getCopyID(), for_compare.getCopyID());
        assertEquals(bookLentHistory.getUser_id(), for_compare.getUser_id());
    }

    @Test
    public void getLentBooksByCopyID() throws Exception {
        ReturnBookController returnBookController = new ReturnBookController();
        returnBookController.getLentBooksByCopyID("ED0003_4");
        BookLentHistory bookLentHistory = ReturnBookController.list_books.get(0);
        BookLentHistory for_compare = new BookLentHistory();
        for_compare.setDate("07/12/2017");
        for_compare.setIs_returned("NO");
        for_compare.setCard_number("7626276627");
        for_compare.setCopyID("ED0003_4");
        for_compare.setUser_id("TEST_01");
        assertEquals(bookLentHistory.getCard_number(), for_compare.getCard_number());
        assertEquals(bookLentHistory.getDate(), for_compare.getDate());
        assertEquals(bookLentHistory.getIs_returned(), for_compare.getIs_returned());
        assertEquals(bookLentHistory.getCopyID(), for_compare.getCopyID());
        assertEquals(bookLentHistory.getUser_id(), for_compare.getUser_id());
    }

}