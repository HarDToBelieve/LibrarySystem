package com.itss;
import com.itss.basic.BasicModel;
import com.itss.exception.*;

/**
 * Created by HarDToBelieve on 10/17/2017.
 */

public class BookCopyInfo implements BasicModel {
	private String copyID;
	private String author;
	private String type;
	private double price;
	private String bookID;

	private final String endpoint = "http://localhost/bookcopyinfo";
	private final String keyLocation = ".keydb";

	/**
	 * Constructor
	 * @param copyID id of copy
	 * @param author author of copy
	 * @param type type of copy
	 * @param price price of copy
	 * @param bookID orginal book id of copy
	 */
	public BookCopyInfo(String copyID, String author, String type, double price, String bookID) {
		// TODO:
		// - init attribute
		// - connect to db
	}

	/**
	 * Insert copy information to db
	 * @param copy copy instance which need to be added
	 * @throws InsertDBException if there are any errors
	 */
	public static void insertCopy(BookCopyInfo copy) throws InsertDBException {
		// TODO:
		// - query to DB
		// - throw exception if it exist
	}

	@Override
	public boolean checkConnection() {
		return true;
	}

	@Override
	public boolean getAll() {
		return true;
	}

	public boolean getUnique() {
		return true;
	}

	@Override
	public boolean add() {
		return true;
	}
}