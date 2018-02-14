package com.p2psample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

import com.p2psample.util.Constants;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class LogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);

        WebView logWebView = findViewById(R.id.webViewLog);
        File file = new File("/data/data/" + getPackageName() + "/files/" + Constants.LOG_FILE);
        logWebView.loadUrl("file:///" + file);
    }

    private String readLogFromFile() {
        try {
            FileInputStream inputStream = openFileInput(Constants.LOG_FILE);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            return sb.toString();
        } catch (IOException e) {
            return "";
        }
    }
}