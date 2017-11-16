package com.itss.view;
import com.itss.BookCopyInfo;
import com.itss.basic.BasicController;
import com.itss.basic.BasicModel;
import com.itss.basic.BasicView;
import com.itss.exception.*;
import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by HarDToBelieve on 10/17/2017.
 */

public class BookCopyRegistrationForm implements BasicView {
	/**
	 * Initialize every component in BookCopyRegistrationForm
	 */
	public BookCopyRegistrationForm() {
		// TODO: 
		// - init button, text, ...
		// - add listeners to buttons
	}

	/**
	 * Get the status of a book by book id
	 * @param bookID the id of book
	 */
	public static void getBookStatus(String bookID) {
		// TODO: 
		// - Call method getBookStatus of BookCopyRegistrationController
	}

	/**
	 * Add copies of a book
	 * @param copies: copy instance which you want to add
	 */
	public static void addCopies(ArrayList<BookCopyInfo> copies) {
		// TODO:
		// - Create a new book copy controller
		// - Call method its saveData
	}

	@Override
	public BasicController getController() {
		return null;
	}

	@Override
	public void setController(BasicController bc) {

	}

	@Override
	public void updateModelFromView() {

	}

	@Override
	public void updateViewFromController() {

	}

	@Override
	public void submit() {

	}

	@Override
	public void close() {

	}

	@Override
	public void error() {

	}
}