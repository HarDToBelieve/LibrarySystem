package com.itss.Controller;
import com.itss.Boundary.Forms.BookCopyForm;
import com.itss.Entity.BookCopyInfo;
import com.itss.Entity.BookInfo;
import com.itss.basic.BasicController;
import com.itss.utilities.RandomString;
import com.itss.Boundary.BookCopyRegistrationForm;

import java.awt.print.Book;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by HarDToBelieve on 10/17/2017.
 */

public class BookCopyRegistrationController implements BasicController {
	private ArrayList<BookCopyInfo> copies;
	private BookCopyForm bcf;

	/**
	 * Update local data from remote database
	 */
	public void setDb() {
		db = BookCopyInfo.getAllCopy();
		for (BookCopyInfo tmp : db) {
			tmp.updateStatusAndTitle();
		}
	}

	private Vector<BookCopyInfo> db;

    public BookCopyRegistrationController() {
		copies = new ArrayList<>();
		bcf = new BookCopyForm();
		db = BookCopyInfo.getAllCopy();
		for (BookCopyInfo tmp : db) {
			tmp.updateStatusAndTitle();
		}
    }

	/**
	 * Retrieve copy information as vector of strings to put into table
	 * @return Vector of strings of information
	 */
	@Override
	public Vector<Object> getModel() {
		Vector<Object> result = new Vector<>();
		for (BookCopyInfo tmp : copies) {
			result.add(new String[]{tmp.getCopyID(), tmp.getType(), String.valueOf(tmp.getPrice()), tmp.getBookID()});
		}
		return result;
	}

	/**
	 * Check whether the form was filled with specific condition or not
	 * @return True of False
	 */
	@Override
	public boolean validateObject() {
		boolean condBookID = !bcf.getBookID().isEmpty() && bcf.getBookID().matches("^[a-zA-Z0-9\\s]*$");
		boolean condType = !bcf.getType().isEmpty() && bcf.getType().matches("REFERENCE|BORROWABLE$");
		boolean condPrice = !bcf.getPrice().isEmpty() && bcf.getPrice().matches("^[0-9.]*$");
		boolean condNumCopies = !bcf.getNumOfCopy().isEmpty() && bcf.getNumOfCopy().matches("^[0-9]*$");

		HashMap<String, String> dict = new HashMap<>();
		dict.put("bookID", bcf.getBookID());
		boolean existBook = BookInfo.getUniqueBook(dict).validObject();
		return condBookID && condType && condPrice && existBook && condNumCopies;
	}

	@Override
	public void updateData() {
		copies.forEach(BookCopyInfo::add);
	}

	@Override
	public void selectData() {

	}

	/**
	 * Ge the last copy index with specific book id
	 * @param bookid Bookid of the copy
	 * @return The last index
	 */
	public int getLastCopy(String bookid) {
		HashMap<String, String> dict = new HashMap<>();
		dict.put("bookID", bookid);
		Vector<BookCopyInfo> tmp = BookCopyInfo.getUniqueCopy(dict);
		int maxnum = 0;
		for (BookCopyInfo b : tmp) {
			String[] tmp2 = b.getCopyID().split("_");
			if ( maxnum < Integer.parseInt(tmp2[1]) )
				maxnum = Integer.parseInt(tmp2[1]);
		}
		return maxnum;
//		return BookCopyInfo.getSum(bookid);
	}

	/**
	 * Generate the copyID based on its bookID
	 */
	public void genCopyCode() {
		int last = getLastCopy(bcf.getBookID());
		copies.clear();

		for (int i=0; i<Integer.parseInt(bcf.getNumOfCopy()); i++) {
			String copyID = bcf.getBookID() + "_" + String.valueOf(i + last + 1);


			copies.add(new BookCopyInfo(copyID, bcf.getType(),
					Double.parseDouble(bcf.getPrice()), bcf.getBookID()));
		}
	}

	/**
	 * Set all text in the form to controller
	 * @param bookID book id of the book
	 * @param type type of the copies
	 * @param avgPrice average price of all copies
	 * @param numOfCopy number of the copies
	 */
	public void setForm(String bookID, String type, String avgPrice, String numOfCopy) {
		bcf = new BookCopyForm(bookID, type, avgPrice, numOfCopy);
	}

	/**
	 * Edit the information of each copy
	 * @param type type of the copy
	 * @param price price of the copy
	 * @param index the index in list of copy
	 */
	public void modifyData(String type, String price, int index) {
		BookCopyInfo tmp = copies.get(index);
		copies.set(index, new BookCopyInfo(tmp.getCopyID(), type, Double.parseDouble(price), tmp.getBookID()));
	}

	/**
	 * Find a specific copy on the remote database
	 * @param b an object BookCopyInfo
	 * @return Informations of that book copy as a list of String
	 */
	public static ArrayList<String> getUniqueBookCopyModel(BookCopyInfo b) {
		ArrayList<String> result = new ArrayList<>();
		result.add(b.getCopyID());
		result.add(b.getType());
		result.add(String.valueOf(b.getPrice()));
		result.add(b.getBookID());
		result.add(b.getTitle());
		result.add(b.getStatus());
		return result;
	}

	/**
	 * Retrieve a list of copy which have a similar bookid
	 * @param bookid bookid of the copy
	 * @return
	 */
	public Vector<ArrayList<String>> getCopy(String bookid) {
		Vector<ArrayList<String>> result = new Vector<>();

		for (BookCopyInfo tmp : db) {
			if ( tmp.getBookID().contains(bookid) || bookid.isEmpty() ) {
				result.add(getUniqueBookCopyModel(tmp));
			}
		}

		return result;
	}
}