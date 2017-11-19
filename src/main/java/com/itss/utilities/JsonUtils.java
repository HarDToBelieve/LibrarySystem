package com.itss.utilities;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by HarDToBelieve on 11/16/2017.
 */
public class JsonUtils {
    public static HashMap<String, Object> parse(JSONObject json , HashMap<String, Object> out) throws JSONException {
        Iterator<String> keys = json.keys();
        while(keys.hasNext()){
            String key = keys.next();
            Object val = null;
            try{
                JSONObject value = json.getJSONObject(key);
                parse(value, out);
            } catch(Exception e){
                try {
                    val = json.getJSONObject(key);
                } catch (Exception ee) {
                    try {
                        val = json.getJSONArray(key);
                    } catch (Exception eee) {
                        val = json.getString(key);
                    }
                }
            }

            if(val != null){
                out.put(key, val);
            }
        }

        return out;
    }
}
