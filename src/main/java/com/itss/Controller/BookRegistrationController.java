package com.itss.Controller;
import com.itss.Entity.BookCopyInfo;
import com.itss.basic.BasicController;
import com.itss.Entity.BookInfo;
import com.itss.utilities.RandomString;
import com.itss.Boundary.Forms.BookForm;

import java.awt.print.Book;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

/**
 * Created by HarDToBelieve on 10/17/2017.
 */

public class BookRegistrationController implements BasicController {
	private BookInfo book;
	private BookForm bookform;
	private BookCopyInfo bookcopy;
	private String bookid;

	public void setDb() {
		db = BookInfo.getAllBook();
	}

	private Vector<BookInfo> db;

	public BookRegistrationController() {
		book = new BookInfo();
		bookcopy = new BookCopyInfo();
		db = BookInfo.getAllBook();
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

	@Override
	public boolean validateObject() {
		boolean condTitle = !bookform.getTitle().isEmpty() && bookform.getTitle().matches("^[a-zA-Z0-9\\s]*$");
		boolean condAuthor = !bookform.getAuthor().isEmpty() && bookform.getAuthor().matches("^[a-zA-Z0-9\\s]*$");
		boolean condPublisher = !bookform.getPublisher().isEmpty() && bookform.getPublisher().matches("^[a-zA-Z0-9\\s]*$");
		boolean condISBN = !bookform.getIsbn().isEmpty() && bookform.getIsbn().matches("^[a-zA-Z0-9\\s]*$");
		return condTitle && condAuthor && condPublisher && condISBN ;
	}

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

	public static ArrayList<String> getUniqueBookModel(BookInfo b) {
		ArrayList<String> result = new ArrayList<>();
		result.add(b.getTitle());
		result.add(b.getAuthor());
		result.add(b.getPublisher());
		result.add(b.getIsbn());
		result.add(b.getBookID());
		return result;
	}

	public Vector<ArrayList<String>> getBook(String title) {
		Vector<ArrayList<String>> result = new Vector<>();

		for (BookInfo tmp : db) {
			if ( tmp.getTitle().contains(title) || title.isEmpty() ) {
				result.add(getUniqueBookModel(tmp));
			}
		}

		return result;
	}


}