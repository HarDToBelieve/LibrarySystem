package com.itss.Controller;
import com.itss.Boundary.Forms.BookCopyForm;
import com.itss.Entity.BookCopyInfo;
import com.itss.Entity.BookInfo;
import com.itss.basic.BasicController;
import com.itss.utilities.RandomString;
import com.itss.Boundary.BookCopyRegistrationForm;

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
	private Vector<BookCopyInfo> db;

    public BookCopyRegistrationController() {
		copies = new ArrayList<>();
		bcf = new BookCopyForm();
		db = BookCopyInfo.getAllCopy();
    }

	@Override
	public Vector<Object> getModel() {
		Vector<Object> result = new Vector<>();
		for (BookCopyInfo tmp : copies) {
			result.add(new String[]{tmp.getCopyID(), tmp.getType(), String.valueOf(tmp.getPrice()), tmp.getBookID()});
		}
		return result;
	}

	@Override
	public boolean validateObject() {
		boolean condBookID = !bcf.getBookID().isEmpty() && bcf.getBookID().matches("^[a-zA-Z0-9\\s]*$");
		boolean condType = !bcf.getType().isEmpty() && bcf.getType().matches("Reference|Borrowable$");
		boolean condPrice = !bcf.getPrice().isEmpty() && bcf.getPrice().matches("^[0-9.]*$");
		boolean condNumCopies = !bcf.getNumOfCopy().isEmpty() && bcf.getNumOfCopy().matches("^[0-9.]*$");

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

	public int getLastCopy(String bookid) {
		return BookCopyInfo.getSum(bookid);
	}

	public void genCopyCode() {
		int last = getLastCopy(bcf.getBookID());
		copies.clear();
		for (int i=0; i<Integer.parseInt(bcf.getNumOfCopy()); i++) {
			String copyID = bcf.getBookID() + "_" + String.valueOf(i + last);
			copies.add(new BookCopyInfo(copyID, bcf.getType(),
					Double.parseDouble(bcf.getPrice()), bcf.getBookID()));
		}
	}

	public void setForm(String bookID, String type, String avgPrice, String numOfCopy) {
		bcf = new BookCopyForm(bookID, type, avgPrice, numOfCopy);
	}

	public void modifyData(String type, String price, int index) {
		BookCopyInfo tmp = copies.get(index);
		copies.set(index, new BookCopyInfo(tmp.getCopyID(), type, Double.parseDouble(price), tmp.getBookID()));
	}

	public static ArrayList<String> getUniqueBookCopyModel(BookCopyInfo b) {
		ArrayList<String> result = new ArrayList<>();
		result.add(b.getCopyID());
		result.add(b.getType());
		result.add(String.valueOf(b.getPrice()));
		result.add(b.getBookID());
		return result;
	}

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