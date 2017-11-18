package com.itss.Controller;
import com.itss.Entity.BookCopyInfo;
import com.itss.basic.BasicController;
import com.itss.Entity.BookInfo;
import com.itss.exception.*;
import com.itss.utilities.RandomString;
import com.itss.Boundary.BookForm;

import java.util.Vector;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by HarDToBelieve on 10/17/2017.
 */

public class BookRegistrationController implements BasicController {
	private BookInfo book;
	private BookForm bookform;
	private BookCopyInfo bookcopy;
	private String bookid;

	public BookRegistrationController() {
		book = new BookInfo();
		bookcopy = new BookCopyInfo();
	}

	@Override
	public Vector<Object> getModel() {
		Vector<Object> result = new Vector<>();
		result.add(new String[]{"Title" , book.getTitle()});
		result.add(new String[]{"Author" , book.getAuthor()});
		result.add(new String[]{"Publisher", book.getPublisher()});
		result.add(new String[]{"ISBN", book.getIsbn()});
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
		boolean condISBN = !bookform.getIsbn().isEmpty() && bookform.getIsbn().matches("^[a-zA-Z0-9\\s]*$");
		return condTitle && condAuthor && condPublisher && condISBN;
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
        bookid = gen.nextString();
		book = new BookInfo(bookform.getTitle(), bookform.getAuthor(), bookform.getPublisher(),
				bookform.getIsbn(), bookid);
	}

	public void addSample(double price, String type) {
		bookcopy = new BookCopyInfo(bookid + "_00", type, price, bookid);
		bookcopy.add();
	}

	public void setForm (BookForm bf) {
		bookform = bf;
	}
}