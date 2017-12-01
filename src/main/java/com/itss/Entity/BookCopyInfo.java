package com.itss.Entity;
import com.itss.Boundary.Forms.BookCopyForm;
import com.itss.basic.BasicModel;
import com.itss.utilities.APIClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.print.Book;
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

	@Override
	public boolean validObject() {
		return valid;
	}

	public static int getSum(String bookid) {
		Vector<BookCopyInfo> tmp = getAllCopy();
		int count = 0;
		for (BookCopyInfo c : tmp) {
			if ( c.getBookID().equals(bookid) )
				count += 1;
		}
		return count;
	}

	private String[] getStatusOfACopy(){
		// return status and title of a copy from copystatus table
		String data_return[] = new String[2];
		String endpoint = "copystatus/get.php";
		HashMap<String, String> dict = new HashMap<>();
		dict.put("copyID", this.copyID);
		try {
			HashMap<String, Object> result = APIClient.get(BookCopyInfo.host + endpoint, dict);
			if ( result.get("status_code").equals("Success") ) {
				JSONObject o = (JSONObject) result.get("result");
				data_return[0] = o.getString("status");
				data_return[1] = o.getString("title");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data_return;
	}
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
	public void updateStatusAndTitle(){
		String data[] = this.getStatusOfACopy();
		this.status = data[0];
		this.title = data[1];
	}
	public boolean delete_row() {
		HashMap<String, String> dict = new HashMap<>();
		dict.put("copyID", this.getCopyID());
		String folder = "bookinfo";
		return deleteUnique(folder, dict);
	}
}