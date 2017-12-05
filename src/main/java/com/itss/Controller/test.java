package com.itss.Controller;

import com.itss.Entity.BookCopyInfo;
import com.itss.Entity.BookLentHistory;

/**
 * Created by BuiAnhVu on 12/5/2017.
 */
public class test {
    public static void main(String args[]){
//        BookLentHistory bookLentHistory = new BookLentHistory();
//        bookLentHistory.setCopyID("SL04K_2");
//        bookLentHistory.setCard_number("hTdPOuMivt");
//        boolean result = bookLentHistory.delete_row();
//        System.out.println("RESULT = " + result);

        BookCopyInfo bookCopyInfo = new BookCopyInfo();
        bookCopyInfo.setCopyID("SL04K_0");
        try {
            bookCopyInfo.changeStatusOfACopy("AVAILABLE");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
