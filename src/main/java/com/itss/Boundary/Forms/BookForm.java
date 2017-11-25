package com.itss.Boundary.Forms;

import com.itss.basic.BasicForm;

/**
 * Created by tuandm on 11/17/2017.
 */
public class BookForm implements BasicForm {
    private String title;
    private String author;
    private String publisher;
    private String isbn;

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getPublisher() {
        return publisher;
    }


    public BookForm(String title, String author, String publisher, String isbn) {
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.isbn = isbn;
    }

    public String getIsbn() {
        return isbn;
    }
}
