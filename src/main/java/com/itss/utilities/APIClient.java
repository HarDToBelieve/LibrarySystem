package com.itss.utilities;

import javax.net.ssl.HttpsURLConnection;
import java.net.HttpURLConnection;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by HarDToBelieve on 11/16/2017.
 */
public class APIClient {

    public static HashMap<String, Object> get(String url, HashMap<String, String> param) throws Exception {
        String tmp_url = url + "/?";
        for (Map.Entry<String, String> entry : param.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            tmp_url += key + "=" + value + "&";
        }
        tmp_url = tmp_url.substring(0, tmp_url.length() - 1);
        URL obj = new URL(tmp_url);

        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        JSONObject jsonResp = new JSONObject(response.toString());
        return JsonUtils.parse(jsonResp, new HashMap<>());
    }

    public static HashMap<String, Object> post(String url, HashMap<String, String> data) throws Exception {
        URL obj = new URL(url);
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

        con.setRequestMethod("POST");

        JSONObject js = new JSONObject();
        for (Map.Entry<String, String> entry : data.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            js.put(key, value);
        }
        String urlParameters = String.valueOf(js);

        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        JSONObject jsonResp = new JSONObject(response.toString());
        return JsonUtils.parse(jsonResp, new HashMap<>());
    }
}
