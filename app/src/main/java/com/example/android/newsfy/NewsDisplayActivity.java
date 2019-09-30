package com.example.android.newsfy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class NewsDisplayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_display);

        int position = getIntent().getExtras().getInt("position");
        int news = getIntent().getExtras().getInt("news");

        WebView webView = findViewById(R.id.web_view);
        webView.setWebViewClient(new WebViewClient());

        switch (news) {
            case 0:
                webView.loadUrl(MainActivity.mNews.get(position).getNewsUrl());
                break;
            case 1:
                webView.loadUrl(MainActivity.mNewsVertical.get(position).getNewsUrl());
                break;
        }
        WebSettings webSettings = webView.getSettings();
    }
}
