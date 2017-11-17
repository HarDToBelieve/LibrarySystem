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
	public BookInfo() {

	}

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

	public String getHost() {
		return host;
	}

	private String title;
	private String author;
	private String publisher;
	private String type;
	private double price;
	private String bookID;
	private boolean valid;

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

	public BookInfo(String title, String author, String publisher, String type, double v) {

	}

	/**
	 * Get the status of a book by book id
	 * @param ID the id of book
	 * @return BookInfo instance
	 * @throws BookNotFoundException if there is no such book
	 */

	@Override
	public void getByID(String ID) {
		String endpoint = "/get.php";
		HashMap<String, String> dict = new HashMap<>();
		dict.put("bookID", ID);
		try {
			HashMap<String, Object> result = APIClient.get(BookInfo.host + endpoint, dict);
			if ( result.get("status_code").equals("Success") ) {
				JSONObject o = (JSONObject) result.get("result");
				this.title = o.getString("title");
				this.author = o.getString("author");
				this.publisher = o.getString("publisher");
				this.type = o.getString("type");
				this.price = Double.parseDouble(o.getString("price"));
				this.bookID = o.getString("bookID");
				this.valid = true;
			}
			else {
				this.valid = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean checkConnection() {
		return true;
	}

	static Vector<BookInfo> dumpBooks (JSONArray lineItems) {
		Vector<BookInfo> books = new Vector<>();
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

	public static Vector<BookInfo> getAll() {
		String endpoint = "/getall.php";
		HashMap<String, Object> result = null;
		try {
			result = APIClient.get(BookInfo.host + endpoint, new HashMap<>());
			if ( result.get("status_code").equals("Success") ) {
				return dumpBooks((JSONArray) result.get("result"));
			}
			else return new Vector<>();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new Vector<>();
	}

	public static Vector<BookInfo> getUnique(HashMap<String, String> dict) {
		HashMap<String, Object> result = null;
		String endpoint = "/get.php";
		try {
			result = APIClient.get(BookInfo.host + endpoint, dict);
			if ( result.get("status_code").equals("Success") ) {
				return dumpBooks((JSONArray) result.get("result"));

			}
			else return new Vector<>();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new Vector<>();
	}

	@Override
	public void add() {
		HashMap<String, String> data = new HashMap<>();
		data.put("title", title);
		data.put("author", author);
		data.put("publisher", publisher);
		data.put("type", type);
		data.put("price", String.valueOf(price));
		data.put("bookID", bookID);

		HashMap<String, Object> result = null;
		String endpoint = "/post.php";
		try {
			result = APIClient.post(BookInfo.host + endpoint, data);
			valid = result.get("status_code").equals("Success");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean validObject() {
		return valid;
	}
}