package com.itss.Controller;

import com.itss.Entity.BookCopyInfo;
import com.itss.Entity.BookLentHistory;
import com.itss.basic.BasicController;
import com.itss.basic.BookLentForm;
import org.json.JSONArray;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by BuiAnhVu on 11/26/2017.
 */
public class ReturnBookController implements BasicController {
    BookLentForm form;
    Vector<String[]>  pick_from_view; // holds list of row that picked by librarian String[0] card_number, String[1] copyID
    static ArrayList<BookLentHistory> list_books;
    ArrayList<BookLentHistory> list_picked_rows;

    public double getTotal_compensation() {
        return total_compensation;
    }

    double total_compensation;

    public void setPick_from_view(Vector<String[]> pick_from_view) {
        this.pick_from_view = pick_from_view;
    }

    public ReturnBookController() {
        this.form = new BookLentForm();
        this.pick_from_view = new Vector<>();
        this.list_picked_rows = new ArrayList<>();
        this.list_books = new ArrayList<>();
    }

    @Override
    public Vector<Object> getModel() {
        Vector<Object> result = new Vector<>();
        BookCopyInfo bookCopyInfo = new BookCopyInfo();
        for (BookLentHistory tmp : list_books) {
            // get name of books add to result?
            result.add(new String[]{tmp.getCopyID(), tmp.getUser_id(), tmp.getDate(), tmp.getCard_number()});
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
    public void setForm(BookLentForm form){
        this.form = form;
    }
    public void getLentBooksByCardNumber(String card_number){
        Vector<BookLentHistory> lentbooks = BookLentHistory.getBooksByCardNumber(card_number);
        list_books.clear();
        for(BookLentHistory lentbook : lentbooks){
            BookLentHistory tmp = new BookLentHistory(lentbook.getUser_id(), lentbook.getCopyID(), lentbook.getDate(), lentbook.getCard_number(),lentbook.getIsReturned());
            list_books.add(tmp);
        }
    }
    public void getLentBooksByCopyID(String copyID){
        Vector<BookLentHistory> lentbooks = BookLentHistory.getBooksByCopyID(copyID);
        list_books.clear();
        for (BookLentHistory lentbook : lentbooks){
            BookLentHistory tmp = new BookLentHistory(lentbook.getUser_id(), lentbook.getCopyID(), lentbook.getDate(), lentbook.getCard_number(),lentbook.getIsReturned());
            list_books.add(tmp);
        }
    }
    public void getPickedLentBook() throws ParseException { //used for displaying rows after picked
        // set picked rows into a class's variable
        list_picked_rows.clear();
        for(String[] a_pick : pick_from_view){
            String card_number = a_pick[0];
            String copyID = a_pick[1];
            for(BookLentHistory a_lent : list_books){
                if (a_lent.getCard_number().equals(card_number) && a_lent.getCopyID().equals(copyID))
                    list_picked_rows.add(a_lent);
            }
        }
        cal_compensation_for_all_picked_rows();
    }
    private void delete_picked_rows(){
        // only works after calling function getPickedLentBook
        // this function calls to model then delete the row selected in the db of booklenthistory
        //param holds card_number and copyID make a unique key for a row
        for(BookLentHistory a_lent : list_picked_rows){
            a_lent.delete_row();
        }
    }
    private void cal_compensation_for_all_picked_rows() throws ParseException {
        total_compensation = 0;
        for (BookLentHistory a_lent : list_picked_rows){
            total_compensation += a_lent.calCompensation();
        }
    }

}
