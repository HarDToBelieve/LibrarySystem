package com.itss.Boundary.Forms;

/**
 * Created by BuiAnhVu on 11/26/2017.
 */
public class CardForm {
    private String user_id;
    private String is_student;

    public CardForm(String user_id, String is_student) {
        this.user_id = user_id;
        this.is_student = is_student;
    }
    public String getUser_id() {
        return user_id;
    }
    public String getIs_student() {
        return is_student;
    }
}
