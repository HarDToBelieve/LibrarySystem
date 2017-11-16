package com.itss.controller;
import com.itss.basic.BasicController;
import com.itss.model.BookInfo;
import com.itss.exception.*;
import com.itss.utilities.RandomString;

import java.util.Vector;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by HarDToBelieve on 10/17/2017.
 */

public class BookRegistrationController implements BasicController {
	private BookInfo book;

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
	public void validateData() {

	}

	/**
	 * Add a copy of a book
	 * @throws AddBookException if cannot insert copy to database
	 */
	@Override
	public void updateData() throws Exception {
		book.add();
	}

	@Override
	public void selectData() {

	}

	public void genCode(String title, String author, String publisher, String type, Double price) {
		RandomString gen = new RandomString(8, ThreadLocalRandom.current());
        String bookid = gen.nextString();
		book.setInfo(title, author, publisher, type, price, bookid);
	}
}