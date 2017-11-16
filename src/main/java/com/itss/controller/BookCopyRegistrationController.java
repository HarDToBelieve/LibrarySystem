package com.itss.controller;
import com.itss.BookCopyInfo;
import com.itss.basic.BasicController;
import com.itss.exception.*;
import com.itss.view.BookCopyRegistrationForm;

import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by HarDToBelieve on 10/17/2017.
 */

public class BookCopyRegistrationController implements BasicController {
	private ArrayList<BookCopyInfo> copies;
	private BookCopyRegistrationForm form;

	/**
	 * Constructor
	 * @param copies list of copies
	 * @param form registration form
	 */
	public BookCopyRegistrationController(ArrayList<BookCopyInfo> copies,
											BookCopyRegistrationForm form) {
		// TODO:
		// - init attributes
	}

	/**
	 * Get the status of a book by book id
	 * @param bookID the id of book
	 * @return status of the book ( whether "Available" or "Unavailable" )
	 */
	public static String getBookStatus(String bookID) {
		// TODO: 
		// - Call method getBookByID of BookInfo
		// - return result
		return new String("Available");
	}

	/**
	 * Add a copy of a book
	 * @throws AddCopyException if cannot insert copy to database
	 */
	public void saveData() throws AddCopyException {
		// TODO:
		// - Call method getBookStatus
		// - Call method genCopyCode
		// - Call method insertCopy of each BookCopyInfo elements
	}

	/**
	 * Generate code for each copy
	 */
	public void genCopyCode() {
		// TODO:
		// - Generate code for each element in the list
	}

	@Override
	public Vector<Object> getModel() {
		return null;
	}

	@Override
	public void validateData() {

	}

	@Override
	public void updateData() {

	}

	@Override
	public void selectData() {

	}
}