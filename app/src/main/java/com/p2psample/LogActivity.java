package com.p2psample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

public class LogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);

        WebView logWebView = findViewById(R.id.webViewLog);

        String url = "";
        logWebView.loadUrl(url);
    }
}