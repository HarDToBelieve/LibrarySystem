package com.itss.Controller;

import com.itss.Boundary.BookLentForm;
import com.itss.Entity.BookLentHistory;
import com.itss.basic.BasicController;

import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by BuiAnhVu on 11/21/2017.
 */
public class BookReturnController implements BasicController{
    ArrayList<BookLentHistory> bookLentHistory;
    BookLentForm bookLentForm;
    public BookReturnController(){
        bookLentForm = new BookLentForm();
        bookLentHistory = new ArrayList<>();
    }
    @Override
    public Vector<Object> getModel() {
        return null;
    }

    @Override
    public boolean validateData() {
        return false;
    }

    @Override
    public void updateData() {
        for (BookLentHistory blh : bookLentHistory){
            blh.changeStatusOfLent();
        }
    }

    @Override
    public void selectData() {

    }
    public void setForm(BookLentForm form){
        this.bookLentForm = form;
    }

}
