package com.kelvinyang.androidhttpdemo;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.kelvinyang.androidhhtp.HttpRequestAysnc;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "HttpDemoActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        testHttpGetAsync();
    }

    private void testHttpGetAsync(){
        HttpRequestAysnc httpRequestAysnc = new HttpRequestAysnc();
        Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                String result = msg.toString();
                Log.e(TAG, result);
            }
        };

        httpRequestAysnc.HttpGetAsync("http://www.bbc.com", handler);
    }

}
