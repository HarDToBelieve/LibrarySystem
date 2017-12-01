package com.itss.Controller;
import com.itss.Boundary.Forms.BookCopyForm;
import com.itss.Entity.BookCopyInfo;
import com.itss.Entity.BookInfo;
import com.itss.Entity.CopyInfo;
import com.itss.basic.BasicController;
import com.itss.utilities.RandomString;
import com.itss.Boundary.BookCopyRegistrationForm;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;
import java.util.concurrent.ThreadLocalRandom;


/**
 * Created by HDT on 11/29/2017.
 */
public class BookDeleteController implements BasicController {
    private ArrayList<CopyInfo> copy_list;
    private BookCopyForm bookcopyform;
    Vector<String[]> pick_from_view;
    ArrayList<CopyInfo> list_picked_rows;

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
        for (CopyInfo tmp : copy_list) {
            result.add(new String[]{tmp.getCopyID(), tmp.getType(), String.valueOf(tmp.getPrice()), tmp.getBookID(), tmp.getCopyStatus(), tmp.getTitle()});
        }
        return result;
    }

    @Override
    public boolean validateObject() {
        return true;
    }

    @Override
    public void updateData() {
        copy_list.forEach(CopyInfo::delete_row);
    }

    @Override
    public void selectData() {

    }

    public void getCopyByCopyID(String copyID) {
        Vector<CopyInfo> deletebooks = CopyInfo.getCopyByID(copyID);
        copy_list.clear();
        for (CopyInfo copy : deletebooks) {
            CopyInfo tmp = new CopyInfo(copy.getCopyID(), copy.getType(), copy.getPrice(), copy.getBookID(), copy.getCopyStatus(), copy.getTitle());
            copy_list.add(tmp);
        }

    }

    public void getCopyByTitle(String title) {
        Vector<CopyInfo> deletebooks = CopyInfo.getCopyByCopyTitle(title);
        copy_list.clear();
        for (CopyInfo copy : deletebooks) {
            CopyInfo tmp = new CopyInfo(copy.getCopyID(), copy.getType(), copy.getPrice(), copy.getBookID(), copy.getCopyStatus(), copy.getTitle());
            copy_list.add(tmp);
        }
    }

    public void getPickedDeleteBook() throws ParseException { //used for displaying rows after picked
        // set picked rows into a class's variable
        list_picked_rows.clear();
        for (String[] a_pick : pick_from_view) {
            String copyID = a_pick[0];
            for (CopyInfo copy : copy_list) {
                if (copy.getCopyID().equals(copyID))
                    list_picked_rows.add(copy);
            }
        }
    }

    private void delete_picked_rows(){
        // only works after calling function getPickedDeleteBook
        // this function calls to model then delete the row selected in the db of bookcopyinfo
        //param holds copyID make a unique key for a row
        String copyStatus = "available";
        for(CopyInfo copy : list_picked_rows){
            if(copy.getCopyStatus().equals(copyStatus))
                copy.delete_row();
        }
    }

}