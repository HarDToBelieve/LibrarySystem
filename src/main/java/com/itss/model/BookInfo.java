package com.itss.model;
import com.itss.basic.BasicModel;
import com.itss.exception.*;
import com.itss.utilities.APIClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.print.Book;
import java.util.HashMap;
import java.util.Vector;

/**
 * Created by HarDToBelieve on 10/17/2017.
 */

public class BookInfo implements BasicModel {
	public String getTitle() {
		return title;
	}

	public String getAuthor() {
		return author;
	}

	public String getPublisher() {
		return publisher;
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

	private String title;
	private String author;
	private String publisher;
	private String type;
	private double price;
	private String bookID;

	private final String endpoint = "http://localhost/bookinfo";
	private final String keyLocation = ".keydb";

	/**
	 * Init attribute
	 * @param title title of the book
	 * @param author author of the book
	 * @param publisher publisher of the book
	 * @param type type of the book
	 * @param price price of the book
	 * @param bookID id of the book
	 */
	public BookInfo(String title, String author, String publisher, String type, double price, String bookID) {
		this.title = title;
		this.author = author;
		this.publisher = publisher;
		this.type = type;
		this.price = price;
		this.bookID = bookID;
	}

	public BookInfo() {

	}

	/**
	 * Get the status of a book by book id
	 * @param bookID the id of book
	 * @return BookInfo instance
	 * @throws BookNotFoundException if there is no such book
	 */
	public static BookInfo getBookByID(String bookID) throws BookNotFoundException {

		return new BookInfo("", "", "", "", 0, "");
	}

	@Override
	public boolean checkConnection() {
		return true;
	}

	@Override
	public boolean getAll() {
		return false;
	}

	public static Vector<BookInfo> getUnique(String url, HashMap<String, String> dict) throws Exception {
		HashMap<String, Object> result = APIClient.get(url, dict);
		if ( result.get("status_code").equals("Success") ) {
			Vector<BookInfo> books = new Vector<>();
			JSONArray lineItems = (JSONArray) result.get("result");
			for (Object o : lineItems) {
				JSONObject jsonLineItem = (JSONObject) o;
				String title = jsonLineItem.getString("title");
				String author = jsonLineItem.getString("author");
				String publisher = jsonLineItem.getString("publisher");
				String type = jsonLineItem.getString("type");
				double price = Double.parseDouble(jsonLineItem.getString("price"));
				String bookID = jsonLineItem.getString("bookID");

				BookInfo tmp = new BookInfo(title, author, publisher, type, price, bookID);
				books.add(tmp);
			}
			return books;
		}
		else return new Vector<>();
	}

	@Override
	public boolean add() throws Exception {
		HashMap<String, String> data = new HashMap<>();
		data.put("title", title);
		data.put("author", author);
		data.put("publisher", publisher);
		data.put("type", type);
		data.put("price", String.valueOf(price));
		data.put("bookID", bookID);

		HashMap<String, Object> result = APIClient.post(endpoint, data);
		if ( result.get("status_code").equals("Success") )
			return true;
		else return false;
	}

	public void setInfo(String title, String author, String publisher, String type, Double price, String bookid) {
		this.title = title;
		this.author = author;
		this.publisher = publisher;
		this.type = type;
		this.price = price;
		this.bookID = bookid;
	}
}