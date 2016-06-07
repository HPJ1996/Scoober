package com.example.hpj_1996.scoober;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebActivity extends AppCompatActivity {
    WebView wv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        wv = (WebView)findViewById(R.id.wv);
        wv.getSettings().setJavaScriptEnabled(true);
        wv.setWebViewClient(client);
        wv.loadUrl("http://scoober.loliloli.asia");
    }

    WebViewClient client = new WebViewClient() {
        @Override
        public boolean shouldOverrideUrlLoading(
                WebView view, String url) {
            view.loadUrl("http://scoober.loliloli.asia");
            return true;
        }
    };
}
