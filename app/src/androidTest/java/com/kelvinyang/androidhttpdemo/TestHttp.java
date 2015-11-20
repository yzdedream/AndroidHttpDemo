package com.kelvinyang.androidhttpdemo;

import android.test.AndroidTestCase;

import com.kelvinyang.androidhhtp.HttpRequest;

/**
 * Created by Kelvin on 2015/11/20.
 */
public class TestHttp extends AndroidTestCase {
    HttpRequest httpRequest;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        httpRequest = HttpRequest.getInstance();
    }

    public void testHttpGet(){
        assertTrue(httpRequest.httpGet("http://www.bbc.com") != "");
    }

    public void testHttpsGet(){
        // well if you run this in china it will fail
        // but it has nothing to do with the code
        assertTrue(httpRequest.httpGet("https://www.google.com") != "");
    }

    public void testIllegalUrl(){
        assertTrue(HttpRequest.httpGet("thisIsAIllegalUrl") == "");
    }

    public void testHttpsLogin(){
        String result = HttpRequest.httpGetAuth("https://aukene.fi/api/address/?format=json", "admin", "adm1n1strat0r");

        assertTrue(result != "");
    }
}
