package com.itss.Controller;
import com.itss.Boundary.Forms.BookCopyForm;
import com.itss.Entity.BookCopyInfo;
import com.itss.basic.BasicController;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;


/**
 * Created by HDT on 11/29/2017.
 */
public class BookDeleteController implements BasicController {
    private ArrayList<BookCopyInfo> copy_list;
    private BookCopyForm bookcopyform;
    Vector<String[]> pick_from_view;
    ArrayList<BookCopyInfo> list_picked_rows;
    private final String STATUS_AVAILABLE = "AVAILABLE";

    public BookDeleteController() {
        this.copy_list = new ArrayList<>();
        this.bookcopyform = new BookCopyForm();
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
            result.add(new String[]{tmp.getCopyID(), tmp.getType(), String.valueOf(tmp.getPrice()), tmp.getBookID(), tmp.getTitle(), tmp.getStatus()});
        }
        return result;
    }

    @Override
    public boolean validateObject() {
        return true;
    }

    @Override
    public void updateData() {
        delete_picked_rows();
    }

    @Override
    public void selectData() {

    }

    public void getCopyByCopyID(String copyID) {
        HashMap<String, String> dict = new HashMap<>();
        dict.put("copyID", copyID);
        Vector<BookCopyInfo> deletebooks = BookCopyInfo.getUniqueCopy(dict);
        copy_list.clear();
        for (BookCopyInfo copy : deletebooks) {
            copy.updateStatusAndTitle();
            copy_list.add(copy);
        }
    }

    public void getPickedDeleteBook() throws ParseException { //used for displaying rows after picked
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

    private void delete_picked_rows(){
        // only works after calling function getPickedDeleteBook
        // this function calls to model then delete the row selected in the db of bookcopyinfo
        //param holds copyID make a unique key for a row
        for(BookCopyInfo copy : list_picked_rows){
            if(copy.getStatus().equals(this.STATUS_AVAILABLE))
                copy.delete_row();
        }
    }

}