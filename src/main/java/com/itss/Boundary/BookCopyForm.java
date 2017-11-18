package com.itss.Boundary;

import com.itss.basic.BasicForm;

public class BookCopyForm implements BasicForm {
    private String type;
    private String price;
    private String bookID;

    public String getNumOfCopy() {
        return numOfCopy;
    }

    private String numOfCopy;

    public BookCopyForm() {

    }

    public String getType() {
        return type;
    }

    public String getPrice() {
        return price;
    }

    public String getBookID() {
        return bookID;
    }

    public BookCopyForm(String type, String price, String bookID, String numOfCopy) {
        this.type = type;
        this.price = price;
        this.bookID = bookID;
        this.numOfCopy = numOfCopy;
    }
}
