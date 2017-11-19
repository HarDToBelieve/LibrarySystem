package com.itss.Entity;
import com.itss.basic.BasicModel;
import com.itss.exception.*;
import com.itss.utilities.APIClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Vector;

import static com.itss.basic.BasicModel.getAll;
import static com.itss.basic.BasicModel.getUnique;

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

	public String getBookID() {
		return bookID;
	}

	public String getHost() {
		return host;
	}

	private String title;
	private String author;
	private String publisher;
	private String bookID;

	public String getIsbn() {
		return isbn;
	}

	private String isbn;
	private boolean valid;

	public BookInfo(String title, String author, String publisher, String isbn, String bookID) {
		this.title = title;
		this.author = author;
		this.publisher = publisher;
		this.bookID = bookID;
		this.isbn = isbn;
	}

	/**
	 * Get the status of a book by book id
	 * @param ID the id of book
	 * @return BookInfo instance
	 * @throws BookNotFoundException if there is no such book
	 */

	@Override
	public void getByID(String ID) {
		String endpoint = "bookinfo/get.php";
		HashMap<String, String> dict = new HashMap<>();
		dict.put("bookID", ID);
		try {
			HashMap<String, Object> result = APIClient.get(BookInfo.host + endpoint, dict);
			if ( result.get("status_code").equals("Success") ) {
				JSONObject o = (JSONObject) result.get("result");
				this.title = o.getString("title");
				this.author = o.getString("author");
				this.publisher = o.getString("publisher");
				this.isbn = o.getString("isbn");
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

	static Vector<BookInfo> dumpBooks (Object lineItems) {
		Vector<BookInfo> books = new Vector<>();
		for (Object o : (JSONArray) lineItems) {
			JSONObject jsonLineItem = (JSONObject) o;
			String title = jsonLineItem.getString("title");
			String author = jsonLineItem.getString("author");
			String publisher = jsonLineItem.getString("publisher");
			String isbn = jsonLineItem.getString("isbn");
			String bookID = jsonLineItem.getString("bookID");

			BookInfo tmp = new BookInfo(title, author, publisher, isbn, bookID);
			books.add(tmp);
		}
		return books;
	}

	public static Vector<BookInfo> getAllBook() {
		return dumpBooks(getAll("bookinfo"));
	}

	public static Vector<BookInfo> getUniqueBook(HashMap<String, String> dict) {
		return dumpBooks(getUnique("bookinfo", dict));
	}

	@Override
	public void add() {
		HashMap<String, String> data = new HashMap<>();
		data.put("title", title);
		data.put("author", author);
		data.put("publisher", publisher);
		data.put("isbn", isbn);
		data.put("bookID", bookID);

		HashMap<String, Object> result = null;
		String endpoint = "bookinfo/post.php";
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