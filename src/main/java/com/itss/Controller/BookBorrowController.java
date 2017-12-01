package com.itss.Controller;

import com.itss.Boundary.Forms.BookBorrowForm;
import com.itss.Boundary.Forms.BookCopyForm;
import com.itss.Entity.BookCopyInfo;
import com.itss.basic.BasicController;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by Administrator on 12/1/2017.
 */
public class BookBorrowController implements BasicController {

    private ArrayList<BookCopyInfo> copy_list;
    private BookBorrowForm bookBorrowForm;
    Vector<String[]> pick_from_view;
    ArrayList<BookCopyInfo> list_picked_rows;

    public BookBorrowController() {
        this.copy_list = new ArrayList<>();
        this.pick_from_view = new Vector<>();
        this.list_picked_rows = new ArrayList<>();
    }

    public void setPick_from_view(Vector<String[]> pick_from_view) {
        this.pick_from_view = pick_from_view;
    }


    @Override
    public Vector<Object> getModel() {
        Vector<Object> result = new Vector<>();
        for (BookCopyInfo tmp : copy_list) {
            result.add(new String[]{tmp.getCopyID(), tmp.getType(), String.valueOf(tmp.getPrice()), tmp.getBookID(), tmp.getCopyStatus(), tmp.getTitle()});
        }
        return result;
    }

    @Override
    public boolean validateObject() {
        return false;
    }

    @Override
    public void updateData() {

    }

    @Override
    public void selectData() {

    }

    public void getCopyByCopyID(String copyID) {
        Vector<BookCopyInfo> borrowbooks = BookCopyInfo.getCopyByID(copyID);
        copy_list.clear();
        for (BookCopyInfo copy : borrowbooks) {
            BookCopyInfo tmp = new BookCopyInfo(copy.getCopyID(), copy.getType(), copy.getPrice(), copy.getBookID(), copy.getCopyStatus(), copy.getTitle());
            copy_list.add(tmp);
        }

    }

    public void getCopyByTitle(String title) {
        Vector<BookCopyInfo> borrowbooks = BookCopyInfo.getCopyByCopyTitle(title);
        copy_list.clear();
        for (BookCopyInfo copy : borrowbooks) {
            BookCopyInfo tmp = new BookCopyInfo(copy.getCopyID(), copy.getType(), copy.getPrice(), copy.getBookID(), copy.getCopyStatus(), copy.getTitle());
            copy_list.add(tmp);
        }
    }



    public void getPickedBorrowBook() throws ParseException { //used for displaying rows after picked
        // set picked rows into a class's variable
        list_picked_rows.clear();
        for (String[] a_pick : pick_from_view) {
            String copyID = a_pick[0];
            for (BookCopyInfo copy : copy_list) {
                if (copy.getCopyID().equals(copyID))
                    list_picked_rows.add(copy);
            }
        }
    }



}
