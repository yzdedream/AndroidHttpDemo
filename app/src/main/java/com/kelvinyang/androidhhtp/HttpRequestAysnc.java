package com.kelvinyang.androidhhtp;

import android.os.Handler;
import android.os.Message;

/**
 * Created by Kelvin on 2015/11/20.
 */
public class HttpRequestAysnc {

    public HttpRequestAysnc() {
    }


    public void HttpGetAsync(String url, Handler handler) {
        Thread thread = new Thread(new HttpWorkingRunnable(url, handler));
        thread.start();
    }


    private class HttpWorkingRunnable implements Runnable {
        private String url;
        private Handler handler;

        public HttpWorkingRunnable(String url, Handler handler) {
            this.url = url;
            this.handler = handler;
        }

        @Override
        public void run() {
            String result;
            result = HttpRequest.httpGet(this.url);
            Message message = Message.obtain();
            message.obj = result;

            handler.sendMessage(message);
        }
    }
}
