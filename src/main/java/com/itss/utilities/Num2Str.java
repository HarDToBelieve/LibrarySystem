package com.itss.utilities;

/**
 * Created by HarDToBelieve on 12/6/2017.
 */
public class Num2Str {
    public static String convert4Num(int x) {
        String result = String.valueOf(x);
        while ( result.length() < 4 )
            result = "0" + result;
        return result;
    }

}
