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

	public void setDb() {
		db = BookInfo.getAllBook();
	}

	private Vector<BookInfo> db;

	public BookRegistrationController() {
		book = new BookInfo();
		db = BookInfo.getAllBook();
	}

	/**
	 * Retrieve copy information as vector of strings to put into table
	 * @return Vector of strings of information
	 */
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
	 * Check whether the form was filled with specific condition or not
	 * @return True of False
	 */
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

	/**
	 * Generate bookID based on its category
	 * @param type the book's category
	 */
	public void genCode(String type) {
		int last = BookInfo.getSum(type);
		book = new BookInfo(bookform.getTitle(), bookform.getAuthor(), bookform.getPublisher(),
				bookform.getIsbn(), type + String.valueOf(last));
	}

	public void setForm (BookForm bf) {
		bookform = bf;
	}

	/**
	 * Find a specific book on the remote database
	 * @param b an object BookInfo
	 * @return Informations of that book as a list of String
	 */
	public static ArrayList<String> getUniqueBookModel(BookInfo b) {
		ArrayList<String> result = new ArrayList<>();
		result.add(b.getTitle());
		result.add(b.getAuthor());
		result.add(b.getPublisher());
		result.add(b.getIsbn());
		result.add(b.getBookID());
		return result;
	}

	/**
	 * Retrieve a list of copy which have a similar title
	 * @param title title of the book
	 * @return
	 */
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