package com.example.news.models.api;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;

import com.example.news.R;

public class WebActivity extends AppCompatActivity {
    NewsHeadlines headlines;
    WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

            webView = findViewById(R.id.web);
            headlines = (NewsHeadlines) getIntent().getSerializableExtra("data");
            webView.loadUrl(headlines.getUrl());
        }
    }
