package com.example.marcus.webviewtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    private final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        WebView show = (WebView) findViewById(R.id.show);
        show.loadUrl("file:///android_asset/Test.html");
        show.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        WebSettings settings = show.getSettings();
        settings.setJavaScriptEnabled(true);
        //将对象暴露给JS
        show.addJavascriptInterface(new MyObject(this),"myObj");
    }
}
