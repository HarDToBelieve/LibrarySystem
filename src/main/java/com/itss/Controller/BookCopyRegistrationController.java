package com.itss.Controller;
import com.itss.Boundary.BookCopyForm;
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

    public BookCopyRegistrationController() {
		copies = new ArrayList<>();
		bcf = new BookCopyForm();
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
	public boolean getBookStatus() {
		boolean condBookID = !bcf.getBookID().isEmpty() && bcf.getBookID().matches("^[a-zA-Z0-9\\s]*$");
		boolean condType = !bcf.getType().isEmpty() && bcf.getType().matches("Reference|Borrowable$");
		boolean condPrice = !bcf.getPrice().isEmpty() && bcf.getPrice().matches("^[0-9.]*$");

		HashMap<String, String> dict = new HashMap<>();
		dict.put("bookID", bcf.getBookID());
		boolean existBook = BookInfo.getUniqueBook(dict).validObject();
		return condBookID && condType && condPrice && existBook;
	}

	@Override
	public void updateData() {
		copies.forEach(BookCopyInfo::add);
	}

	@Override
	public void selectData() {

	}

	public int getLastCopy() {
		return BookCopyInfo.getSum();
	}

	public void genCopyCode() {
		int last = getLastCopy();
		copies = new ArrayList<>(Integer.parseInt(bcf.getNumOfCopy()));
		for (BookCopyInfo bci : copies) {
			String copyID = bcf.getBookID() + "_" + String.valueOf(copies.indexOf(bci) + last);
			copies.set(copies.indexOf(bci), new BookCopyInfo(copyID, bcf.getType(),
					Double.parseDouble(bcf.getPrice()), bcf.getBookID()));
		}
	}

	public void setForm(String bookID, String type, String avgPrice, String numOfCopy) {
		bcf = new BookCopyForm(bookID, type, avgPrice, numOfCopy);
	}
}