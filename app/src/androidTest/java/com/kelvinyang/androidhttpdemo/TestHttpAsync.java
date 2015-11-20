package com.kelvinyang.androidhttpdemo;

import android.nfc.Tag;
import android.os.Handler;
import android.os.Message;
import android.test.AndroidTestCase;
import android.util.Log;

import com.kelvinyang.androidhhtp.HttpRequestAysnc;

/**
 * Created by Kelvin on 2015/11/20.
 */
public class TestHttpAsync extends AndroidTestCase {
    private static final String TAG = "HttpAsync test";

    private HttpRequestAysnc httpRequestAysnc = new HttpRequestAysnc();

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    public void testHttpGetAsync() {
        Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                String result = msg.toString();
                Log.d(TAG, result);
                assertTrue(result != "");
            }
        };
        httpRequestAysnc.HttpGetAsync("http://www.bbc.com", handler);
        try{
            Thread.sleep(30000);
        }
        catch (Exception e){
            Log.e(TAG,""+e);
        }
    }
}
