package com.kelvinyang.androidhhtp;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.UnknownHostException;

/**
 * Created by Kelvin on 2015/11/20.
 */
public class HttpRequest {
    private HttpRequest() {

    }

    private static final int MAX_INTERNET_RETRY = 3;
    private static final String TAG = "Http-request";

    private static HttpRequest instance = null;

    public static HttpRequest getInstance() {
        if (instance == null) {
            instance = new HttpRequest();
        }
        return instance;
    }

    public static String httpGet(String szUrl) {
        String result = "";
        try {
            result = readContentFromGet(szUrl);
        } catch (Exception e) {
            Log.e(TAG, "" + e);
        }
        return result;
    }

    private static String readContentFromGet(String szURL) throws IOException {
        HttpURLConnection connection = null;
        String result = "";
        BufferedReader reader = null;

        int retryCount = MAX_INTERNET_RETRY;
        while (retryCount > 0) {
            try {
                URL url = new URL(szURL);
                connection = (HttpURLConnection) url.openConnection();
                connection.setConnectTimeout(5000);
                connection.setReadTimeout(5000);

                connection.connect();
                reader = new BufferedReader(new InputStreamReader(
                        connection.getInputStream()));

                if (connection.getResponseCode() != 200) {
                    continue;
                }

                String lines;
                while ((lines = reader.readLine()) != null) {
                    result += lines + "\n";
                }
                retryCount = 0;

            } catch (SocketTimeoutException e) {
                Log.e(TAG, "" + e);
                retryCount -= 1;
            } catch (UnknownHostException e) {
                Log.e(TAG, "" + e);
                retryCount -= 1;
            }
        }
        reader.close();
        connection.disconnect();

        return result;
    }
}
