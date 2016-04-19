package com.example.hpj_1996.scoober;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
    }

    public void webClick(View view) {
        Uri uri = Uri.parse(
                "http://scoober.loliloli.asia");
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(uri);
        startActivity(intent);
    }

    public void mailClick(View view) {
        Uri uri = Uri.parse(
                "mailto:scoober@loliloli.asia");
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        Intent intent1 = intent.setData(uri);
        startActivity(intent);
    }

    public void groupClick(View view) {
        Uri uri = Uri.parse(
                "https://telegram.me/joinchat/CDbU3Qi4huxj79kKohGATQ");
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(uri);
        startActivity(intent);
    }
}
