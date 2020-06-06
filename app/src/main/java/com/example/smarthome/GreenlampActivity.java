package com.example.smarthome;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class GreenlampActivity extends AppCompatActivity {
    WebView webView;
    String URL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_greenlamp);

        URL = "http://192.168.43.61/";

        webView = findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().getUseWideViewPort();
        webView.loadUrl(URL);
        webView.setWebViewClient(new WebViewClient());
    }
}
