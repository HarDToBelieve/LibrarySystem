package com.itss.view;

import com.itss.basic.BasicForm;

/**
 * Created by tuandm on 11/17/2017.
 */
public class BookForm implements BasicForm {
    private String title;
    private String author;
    private String publisher;
    private String type;
    private String price;

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

    public String getPrice() {
        return price;
    }

    public BookForm(String title, String author, String publisher, String type, String price) {
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.type = type;
        this.price = price;
    }
}
