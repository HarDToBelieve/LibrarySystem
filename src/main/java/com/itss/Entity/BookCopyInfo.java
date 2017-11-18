package com.itss.Entity;
import com.itss.Boundary.BookCopyForm;
import com.itss.basic.BasicModel;
import com.itss.exception.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Vector;

/**
 * Created by HarDToBelieve on 10/17/2017.
 */

public class BookCopyInfo implements BasicModel {
	public String getCopyID() {
		return copyID;
	}

	public String getType() {
		return type;
	}

	public double getPrice() {
		return price;
	}

	public String getBookID() {
		return bookID;
	}

	public String getEndpoint() {
		return endpoint;
	}

	public String getKeyLocation() {
		return keyLocation;
	}

	private String copyID;
	private String type;
	private double price;
	private String bookID;

	private final String endpoint = "http://localhost/bookcopyinfo";
	private final String keyLocation = ".keydb";

	/**
	 * Constructor
	 * @param copyID id of copy
	 * @param type type of copy
	 * @param price price of copy
	 * @param bookID orginal book id of copy
	 */
	public BookCopyInfo(String copyID, String type, double price, String bookID) {
		this.copyID = copyID;
		this.type = type;
		this.price = price;
		this.bookID = bookID;
	}

	public BookCopyInfo() {

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
	public void getByID(String ID) {

	}

	static Vector<BookCopyInfo> dumpCopy (Object lineItems) {
		Vector<BookCopyInfo> books = new Vector<>();
		for (Object o : (JSONArray) lineItems) {
			JSONObject jsonLineItem = (JSONObject) o;
			String type = jsonLineItem.getString("type");
			String price = jsonLineItem.getString("price");
			String copyID = jsonLineItem.getString("copyID");
			String bookID = jsonLineItem.getString("bookID");

			BookCopyInfo tmp = new BookCopyInfo(copyID, type, Double.parseDouble(price), bookID);
			books.add(tmp);
		}
		return books;
	}

	public static Vector<BookCopyInfo> getAll() {
		return dumpCopy(BookCopyInfo.getAll());
	}

	public static Vector<BookCopyInfo> getUnique(HashMap<String, String> dict) {
		return dumpCopy(BookCopyInfo.getUnique(dict));
	}

	@Override
	public void add() {
	}

	@Override
	public boolean validObject() {
		return false;
	}

	public static int getSum() {
		return 0;
	}
}