package com.itss.Entity;


/**
 * Created by BuiAnhVu on 12/5/2017.
 */
public class test {
    public static void main(String args[]){
        BookLentHistory bookLentHistory = new BookLentHistory();
        bookLentHistory.setUser_id("20145292");
        bookLentHistory.setCopyID("SL04K_2");
        bookLentHistory.set_title_and_name_fromDB();
        System.out.println(bookLentHistory.getTitle());
        System.out.println(bookLentHistory.getUser_name());
    }
}
