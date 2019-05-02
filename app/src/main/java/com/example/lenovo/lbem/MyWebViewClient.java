package com.example.lenovo.lbem;

import android.webkit.WebView;
import android.webkit.WebViewClient;


/**
 * Created by User on 10/14/2017.
 */

class MyWebViewClient extends WebViewClient {
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String request) {
        return false;
    }
}
