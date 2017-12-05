package com.itss.Entity;
import com.itss.Boundary.Forms.BookCopyForm;
import com.itss.basic.BasicModel;
import com.itss.utilities.APIClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.print.Book;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import static com.itss.basic.BasicModel.getAll;
import static com.itss.basic.BasicModel.getUnique;
import static com.itss.basic.BasicModel.deleteUnique;
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

	public String getStatus() {
		return status;
	}

	public String getTitle() {
		return title;
	}

	private String copyID;
	private String type;
	private double price;
	private String bookID;
	private boolean valid;
	private String status;
	private String title;
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

//	public BookCopyInfo(String copyID, String type, double price, String bookID, String status, String title){
//		this.copyID = copyID;
//		this.type = type;
//		this.price = price;
//		this.bookID = bookID;
//		this.status = status;
//		this.title = title;
//	}

	public void setCopyID(String copyID) {
		this.copyID = copyID;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public void setBookID(String bookID) {
		this.bookID = bookID;
	}


	public BookCopyInfo() {

	}

	@Override
	public boolean checkConnection() {
		return true;
	}

	/**
	 * Get the copy on remote server by copyID and set to the current object
	 * @param ID copyID
	 */
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

	/**
	 * Extract information of bookcopy from json
	 * @param lineItems json string
	 * @return list of book copy
	 */
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

	/**
	 * Retrieve all book copy
	 * @return list of book copy
	 */
	public static Vector<BookCopyInfo> getAllCopy() {
		return dumpCopy(getAll("bookcopy"));
	}

	/**
	 * Retrieve list of book copy which are satisfied some conditions
	 * @param dict map of conditions
	 * @return list of book copy
	 */
	public static Vector<BookCopyInfo> getUniqueCopy(HashMap<String, String> dict) {
		return dumpCopy(getUnique("bookcopy", dict));
	}

	/**
	 * Add current book copy object and its status to remote database
	 */
	@Override
	public void add() {
		HashMap<String, String> data = new HashMap<>();
		data.put("copyID", copyID);
		data.put("type", type);
		data.put("price", String.valueOf(price));
		data.put("bookID", bookID);

		HashMap<String, Object> result = null;
		String endpoint = "bookcopy/post.php";
		String endpoint_stt = "copystatus/post.php";
		try {
			result = APIClient.post(BookCopyInfo.host + endpoint, data);
			valid = result.get("status_code").equals("Success");

			data.clear();
			data.put("bookID", bookID);
			BookInfo tmp = BookInfo.getUniqueBook(data);

			data.clear();
			data.put("copyID" , copyID);
			data.put("title", tmp.getTitle());
			data.put("status", "AVAILABLE");
			APIClient.post(BookCopyInfo.host + endpoint_stt, data);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Check if current book copy object is valid or not
	 * @return true or false
	 */
	@Override
	public boolean validObject() {
		return valid;
	}

	/**
	 * Return the last bookid index of book copy on remote database
	 * @param bookid bookID of the copy
	 * @return last index
	 */
	public static int getSum(String bookid) {
		Vector<BookCopyInfo> tmp = getAllCopy();
		int count = 0;
		for (BookCopyInfo c : tmp) {
			if ( c.getBookID().equals(bookid) )
				count += 1;
		}
		return count;
	}

	/**
	 * Return status and title of a copy from copystatus table
	 * @return Available or Borrowed
	 */
	public String[] getStatusOfACopy(){
		String data_return[] = new String[2];
		String endpoint = "copystatus/get.php";
		HashMap<String, String> dict = new HashMap<>();
		dict.put("copyID", this.copyID);
		try {
			HashMap<String, Object> result = APIClient.get(BookCopyInfo.host + endpoint, dict);
			if ( result.get("status_code").equals("Success") ) {
				JSONObject jsonLineItem = (JSONObject) ( (JSONArray) result.get("result") ).get(0);
				data_return[0] = jsonLineItem.getString("status");
				data_return[1] = jsonLineItem.getString("title");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data_return;
	}

	/**
	 * Change the status of a copy
	 * @param desire_status status
	 * @throws Exception
	 */
	public void changeStatusOfACopy(String desire_status) throws Exception {
		String endpoint_stt = "copystatus/update.php";
		HashMap<String, String> data = new HashMap<>();
		data.put("copyID", this.copyID);
		data.put("status", desire_status);
		APIClient.post(BookCopyInfo.host + endpoint_stt, data );
	}
//	public  void getByCopyIdWithStatus(String copyID) {
//		String endpoint = "bookcopy/get.php";
//		HashMap<String, String> dict = new HashMap<>();
//		dict.put("copyID", copyID);
//		try {
//			HashMap<String, Object> result = APIClient.get(BookInfo.host + endpoint, dict);
//			if ( result.get("status_code").equals("Success") ) {
//				JSONObject o = (JSONObject) result.get("result");
//				this.copyID = o.getString("copyID");
//				this.type = o.getString("type");
//				this.price = Double.parseDouble(o.getString("price"));
//				this.bookID = o.getString("bookID");
//				this.valid = true;
//				// get status and title for this book copy
//				String status_data[] = this.getStatusOfACopy();
//				this.status = status_data[0];
//				this.title = status_data[1];
//			}
//			else {
//				this.valid = false;
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	/**
	 * Update status and title to current object
	 */
	public void updateStatusAndTitle(){
		String data[] = this.getStatusOfACopy();
		this.status = data[0];
		this.title = data[1];
	}

	/**
	 * Delete a record of book copy on remote database
	 * @return success or not
	 */
	public boolean delete_row() {
		HashMap<String, String> dict = new HashMap<>();
		dict.put("copyID", this.getCopyID());
		String folder = "bookcopy";
		return deleteUnique(folder, dict);
	}

	/**
	 * Retrieve the first row on remote database of book copy
	 * @param dict condition
	 * @return
	 */
	public static BookCopyInfo getOneBookCopyInfo(HashMap<String, String> dict) {
		try {
			return dumpCopy(getUnique("bookcopy", dict)).get(0);
		} catch (Exception e) {
			return null;
		}
	}

	public static Vector<BookCopyInfo> getBookCopiesByTitle(String title){
		//get bookID by title first
		HashMap<String, String> dict = new HashMap<>();
		dict.put("title", title);
		BookInfo bookInfo = BookInfo.getUniqueBook(dict);
		String bookID = bookInfo.getBookID();
		//use that bookID to get rows in bookCopy
		dict.clear();
		dict.put("bookID", bookID);
		return getUniqueCopy(dict);

	}
	/**
	 * Delete the status of copyt on the remote database
	 */
	public void delete_copyStatus(){
		HashMap<String, String> dict = new HashMap<>();
		dict.put("copyID", this.copyID);
		String folder = "copystatus";
		deleteUnique(folder, dict);
	}

}