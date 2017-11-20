package com.itss.Entity;
import com.itss.Boundary.BookCopyForm;
import com.itss.basic.BasicModel;
import com.itss.utilities.APIClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.print.Book;
import java.util.HashMap;
import java.util.Vector;

import static com.itss.basic.BasicModel.getAll;
import static com.itss.basic.BasicModel.getUnique;

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

	private String copyID;
	private String type;
	private double price;
	private String bookID;
	private boolean valid;

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

	@Override
	public boolean checkConnection() {
		return true;
	}

	@Override
	public void getByID(String ID) {
		String endpoint = "bookcopy/get.php";
		HashMap<String, String> dict = new HashMap<>();
		dict.put("copyID", ID);
		try {
			HashMap<String, Object> result = APIClient.get(BookInfo.host + endpoint, dict);
			if ( result.get("status_code").equals("Success") ) {
				JSONObject o = (JSONObject) result.get("result");
				this.copyID = o.getString("copyID");
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

	public static Vector<BookCopyInfo> getAllCopy() {
		return dumpCopy(getAll("bookcopy"));
	}

	public static Vector<BookCopyInfo> getUniqueCopy(HashMap<String, String> dict) {
		return dumpCopy(getUnique("bookcopy", dict));
	}

	@Override
	public void add() {
		HashMap<String, String> data = new HashMap<>();
		data.put("copyID", copyID);
		data.put("type", type);
		data.put("price", String.valueOf(price));
		data.put("bookID", bookID);

		HashMap<String, Object> result = null;
		String endpoint = "bookcopy/post.php";
		try {
			result = APIClient.post(BookCopyInfo.host + endpoint, data);
			valid = result.get("status_code").equals("Success");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean validObject() {
		return valid;
	}

	public static int getSum() {
		Vector<BookCopyInfo> tmp = getAllCopy();
		return tmp.size();
	}
}