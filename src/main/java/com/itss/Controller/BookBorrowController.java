package com.itss.Controller;

import com.itss.Boundary.Forms.BookBorrowForm;
import com.itss.Entity.BookCopyInfo;
import com.itss.Entity.BookLentHistory;
import com.itss.Entity.Card;
import com.itss.basic.BasicController;
import com.itss.utilities.DateHandling;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

/**
 * Created by Administrator on 12/1/2017.
 */
public class BookBorrowController implements BasicController {

    private ArrayList<BookCopyInfo> copy_list;
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

    /**
     * Retrieve copy information as vector of strings to put into table
     * @return Vector of strings of information
     */
    @Override
    public Vector<Object> getModel() {
        Vector<Object> result = new Vector<>();
        for (BookCopyInfo tmp : copy_list) {
            result.add(new String[]{tmp.getBookID(), tmp.getCopyID(), tmp.getTitle(), tmp.getType(), String.valueOf(tmp.getPrice()), tmp.getStatus()});
        }
        return result;
    }

    /**
     * Check whether invalid copy status, copy type or not, number of total borrowed book more than 5
     * @return True of False
     */
    @Override
    public boolean validateObject() {
        return checkCopyStatus()&&check_card_existed()&&checkNumLentBookUnder5();
    }

    /**
     * Update info of borrowed books to BookLentHistory, change status of new borrowed copies
     */
    @Override
    public void updateData() throws Exception {
        // do 2 things, change status of the current book, add a new row to booklenthistoy
        String card_number = cardNo;
        for(BookCopyInfo copy : list_picked_rows){
            String user_id = Card.getUserIdByCardNumber(card_number);
            BookLentHistory bookLentHistory = new BookLentHistory(user_id, copy.getCopyID(), DateHandling.getToday(), card_number, "NO");
            bookLentHistory.add();
            copy.changeStatusOfACopy("BORROWED");
        }
    }

    @Override
    public void selectData() {

    }

    /**
     * Search copies by book ID, return a list
     * @param bookID is book ID of the book
     */
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

    /**
     * Search copies by title, return a list
     * @param title title of the book
     */
    public void getCopiesByTitle(String title){
        Vector<BookCopyInfo> bookCopyInfoVector = BookCopyInfo.getBookCopiesByTitle(title);
        copy_list.clear();
        for (BookCopyInfo copy : bookCopyInfoVector) {
            copy.updateStatusAndTitle();
            copy_list.add(copy);
        }
    }

    /**
     * Get picked Book, return a list of that books to process Borrow
     */
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

    /**
     * Check Copy status and type
     * @return true when status = " AVAILABLE" and type ="BORROWABLE"
     */
    private boolean checkCopyStatus() {
        String status = "AVAILABLE";
        String type = "BORROWABLE";
        for (BookCopyInfo copy : list_picked_rows) {
            if (!copy.getStatus().equals(status) || !copy.getType().equals(type))
                return false;
        }
        return true;
    }

    /**
     * Check Card exist
     * @return boolean
     */
    public boolean check_card_existed() {
        Card card = new Card();
        return card.check_a_card_existed(cardNo);
    }

    /**
     * Check total of borrowed books including selecting books more than 5
     * @return true when <5
     */
    public boolean checkNumLentBookUnder5() {
        if ((BookLentHistory.countNumLentBook(cardNo) + list_picked_rows.size()) > 5) {
            return false;
        }
        return true;

    }

}
