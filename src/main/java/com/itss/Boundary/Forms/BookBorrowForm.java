package com.itss.Boundary.Forms;

import com.itss.basic.BasicForm;

/**
 * Created by Administrator on 12/1/2017.
 */
public class BookBorrowForm implements BasicForm {
    private String card_number;

    public BookBorrowForm(String card_number) {
        this.card_number = card_number;
    }

    public String getCardNumber() {
        return card_number;
    }
}
