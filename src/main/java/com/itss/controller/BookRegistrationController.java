package com.itss.controller;
import com.itss.basic.BasicController;
import com.itss.basic.BasicForm;
import com.itss.model.BookInfo;
import com.itss.exception.*;
import com.itss.utilities.RandomString;
import com.itss.view.BookForm;

import java.util.Vector;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by HarDToBelieve on 10/17/2017.
 */

public class BookRegistrationController implements BasicController {
	private BookInfo book;
	private BookForm bookform;

	public BookRegistrationController() {
		book = new BookInfo();
	}

	@Override
	public Vector<Object> getModel() {
		Vector<Object> result = new Vector<>();
		result.add(new String[]{"Title" , book.getTitle()});
		result.add(new String[]{"Author" , book.getAuthor()});
		result.add(new String[]{"Publisher", book.getPublisher()});
		result.add(new String[]{"Type", book.getType()});
		result.add(new String[]{"Price", String.valueOf(book.getPrice())});
		result.add(new String[]{"BookID", book.getBookID()});
		return result;
	}

	/**
	 * Validate the book
	 * @return status of the book ( whether "Available" or "Duplicated" )
	 */
	@Override
	public boolean validateData() {
		boolean condTitle = !bookform.getTitle().isEmpty() && bookform.getTitle().matches("^[a-zA-Z0-9\\s]*$");
		boolean condAuthor = !bookform.getAuthor().isEmpty() && bookform.getAuthor().matches("^[a-zA-Z0-9\\s]*$");
		boolean condPublisher = !bookform.getPublisher().isEmpty() && bookform.getPublisher().matches("^[a-zA-Z0-9\\s]*$");
		boolean condType = !bookform.getTitle().isEmpty() && bookform.getType().matches("Reference|Borrowable$");
		boolean condPrice = !bookform.getPrice().isEmpty() && bookform.getPrice().matches("^[0-9\\.]*$");
		return condTitle && condAuthor && condPublisher && condType && condPrice;
	}

	/**
	 * Add a copy of a book
	 * @throws AddBookException if cannot insert copy to database
	 */
	@Override
	public void updateData() {
		book.add();
	}

	@Override
	public void selectData() {

	}

	public void genCode() {
		RandomString gen = new RandomString(8, ThreadLocalRandom.current());
        String bookid = gen.nextString();
		book = new BookInfo(bookform.getTitle(), bookform.getAuthor(), bookform.getPublisher(),
				bookform.getType(), Double.parseDouble(bookform.getPrice()), bookid);
	}

	public void setForm (BookForm bf) {
		bookform = bf;
	}
}