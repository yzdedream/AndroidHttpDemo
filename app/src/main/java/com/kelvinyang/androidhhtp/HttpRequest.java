package com.kelvinyang.androidhhtp;

import android.nfc.Tag;
import android.util.Base64;
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

    private static HttpURLConnection buildConnection(String szUrl) {
        HttpURLConnection connection = null;
        try {
            URL url = new URL(szUrl);
            connection = (HttpURLConnection) url.openConnection();
        } catch (Exception e) {
            Log.e(TAG, "" + e);
        }

        return connection;
    }

    public static String httpGet(String szUrl) {
        String result = "";
        try {
            HttpURLConnection connection = buildConnection(szUrl);
            result = readContentFromGet(connection);
        } catch (Exception e) {
            Log.e(TAG, "" + e);
        }
        return result;
    }

    public static String httpGetAuth(String szURL, String username, String password) {
        String authString = username + ":" + password;
        byte[] authEncBytes = Base64.encode(authString.getBytes(), Base64.DEFAULT);
        String authStringEnc = new String(authEncBytes);

        String result = "";
        try {
            HttpURLConnection connection = buildConnection(szURL);
            connection.setRequestProperty("Authorization", "Basic " + authStringEnc);
            result = readContentFromGet(connection);
        } catch (Exception e) {
            Log.e(TAG, "" + e);
        }
        return result;
    }

    private static String readContentFromGet(HttpURLConnection connection) throws IOException {
        String result = "";
        BufferedReader reader = null;

        int retryCount = MAX_INTERNET_RETRY;
        while (retryCount > 0) {
            try {
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
