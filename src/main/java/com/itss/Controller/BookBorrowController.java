package com.itss.Controller;

import com.itss.Boundary.Forms.BookBorrowForm;
import com.itss.Entity.BookCopyInfo;
import com.itss.Entity.BookLentHistory;
import com.itss.Entity.Card;
import com.itss.basic.BasicController;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

/**
 * Created by Administrator on 12/1/2017.
 */
public class BookBorrowController implements BasicController {

    private ArrayList<BookCopyInfo> copy_list;
    private BookBorrowForm bookBorrowForm;
    Vector<String[]> pick_from_view;
    ArrayList<BookCopyInfo> list_picked_rows;
    private String cardNo;

    public BookBorrowController(String cardNo) {
        this.copy_list = new ArrayList<>();
        this.pick_from_view = new Vector<>();
        this.list_picked_rows = new ArrayList<>();
        this.cardNo = cardNo;
    }

    public void setPick_from_view(Vector<String[]> pick_from_view) {
        this.pick_from_view = pick_from_view;
    }


    @Override
    public Vector<Object> getModel() {
        Vector<Object> result = new Vector<>();
        for (BookCopyInfo tmp : copy_list) {
            result.add(new String[]{tmp.getBookID(), tmp.getCopyID(), tmp.getTitle(), tmp.getType(), String.valueOf(tmp.getPrice()), tmp.getStatus()});
        }
        return result;
    }

    @Override
    public boolean validateObject() {
        return checkCopyStatus()&check_card_existed()&checkNumLentBookUnder5();
    }

    @Override
    public void updateData() throws Exception {
        // do 2 things, change status of the current book, add a new row to booklenthistoy
        String card_number = cardNo;
        for(BookCopyInfo copy : list_picked_rows){
            String user_id = Card.getUserIdByCardNumber(card_number);
            BookLentHistory bookLentHistory = new BookLentHistory(user_id, copy.getCopyID(), BookLentHistory.getToday(), card_number, "NO");
            copy.changeStatusOfACopy("BORROWED");
        }
    }

    @Override
    public void selectData() {

    }

    public void getCopiesByBookId(String bookID) {
        HashMap<String, String> dict = new HashMap<>();
        dict.put("bookID", bookID);
        Vector<BookCopyInfo> borrowbooks = BookCopyInfo.getUniqueCopy(dict);
        copy_list.clear();
        for (BookCopyInfo copy : borrowbooks) {
            copy.updateStatusAndTitle();
            copy_list.add(copy);
        }
    }

    public void getPickedBorrowBook() throws ParseException { //used for displaying rows after picked
        // set picked rows into a class's variable
        list_picked_rows.clear();
        for (String[] a_pick : pick_from_view) {
            String copyID = a_pick[1];
            for (BookCopyInfo copy : copy_list) {
                if (copy.getCopyID().equals(copyID))
                    list_picked_rows.add(copy);
            }
        }
    }

    private boolean checkCopyStatus() {
        String status = "AVAILABLE";
        for (BookCopyInfo copy : list_picked_rows) {
            if (!copy.getStatus().equals(status))
                return false;
        }
        return true;
    }

    public boolean check_card_existed() {
        Card card = new Card();
        return card.check_a_card_existed(bookBorrowForm.getCardNumber());

    }

    public boolean checkNumLentBookUnder5() {
        if ((BookLentHistory.countNumLentBook(bookBorrowForm.getCardNumber()) + list_picked_rows.size()) > 5) {
            return false;
        }
        return true;

    }

}
