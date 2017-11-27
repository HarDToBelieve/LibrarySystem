package com.itss.Boundary.Forms;

/**
 * Created by BuiAnhVu on 11/26/2017.
 */
public class CardForm {
    private String user_id;
    private String is_active;
    private String is_student;
    private String expired_date;
    private String activate_code;

    public CardForm(String user_id, String is_active, String is_student, String expired_date, String activate_code) {
        this.user_id = user_id;
        this.is_active = is_active;
        this.is_student = is_student;
        this.expired_date = expired_date;
        this.activate_code = activate_code;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getIs_active() {
        return is_active;
    }

    public String getIs_student() {
        return is_student;
    }

    public String getExpired_date() {
        return expired_date;
    }

    public String getActivate_code() {
        return activate_code;
    }
}
