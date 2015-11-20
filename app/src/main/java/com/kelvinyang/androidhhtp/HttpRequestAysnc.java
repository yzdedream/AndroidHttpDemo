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
        Thread thread = new Thread(new HttpGetRunnable(url, handler));
        thread.start();
    }

    public void HttpGetAuthAsync(String url, String username, String password, Handler handler) {
        Thread thread = new Thread(new HttpGetAuthRunnable(url, username, password, handler));
        thread.start();
    }


    private class HttpGetRunnable implements Runnable {
        private String url;
        private Handler handler;

        public HttpGetRunnable(String url, Handler handler) {
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

    private class HttpGetAuthRunnable implements Runnable {
        private String url;
        private String username;
        private String password;
        private Handler handler;

        public HttpGetAuthRunnable(String url, String usernmae, String password, Handler handler) {
            this.url = url;
            this.handler = handler;
            this.username = usernmae;
            this.password = password;
        }

        @Override
        public void run() {
            String result;
            result = HttpRequest.httpGetAuth(url, username, password);
            Message message = Message.obtain();
            message.obj = result;

            handler.sendMessage(message);
        }
    }
}
