package com.itss.utilities;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by BuiAnhVu on 12/5/2017.
 */
public class DateHandling {
    public static final int card_expired_period = 150;

    public static String getADate(int days_after){
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, days_after);
        String str_date = formatter.format(cal.getTime()); // date after today days_after days
        return str_date;
    }

    public static int get_days_diff_with_today(String date) throws ParseException {
        // get num of day diffs with today
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date old_date = formatter.parse(date);
//        System.out.println("old date " + old_date.getTime());
        Calendar calendar = Calendar.getInstance();
        String str_today = formatter.format(calendar.getTime());
        Date today = formatter.parse(str_today);
//        System.out.println("today " + today.getTime());
        long diffs = today.getTime() - old_date.getTime();
        // cal culate the days difference and set the money
        int days_diffs = (int) (diffs/ 86400000);
        return days_diffs;
    }
}
