package com.example.hpj_1996.scoober;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TabHost;

public class LoginActivity extends AppCompatActivity {

    Button login_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTab();

        login_button = (Button)findViewById(R.id.action_sign_in);
        login_button.setOnClickListener(loginButtonListener);
    }

    private View.OnClickListener loginButtonListener = new View.OnClickListener() {

        public void onClick(View v) {
            Intent intent = new Intent();
            intent.setClass(LoginActivity.this,MapsActivity.class);
            //startActivityForResult(intent, REQUEST_INPUT_NAME);
        }
    };

    private void setTab()
    {
        TabHost tabHost = (TabHost)findViewById(R.id.tabHost);
        tabHost.setup();

        TabHost.TabSpec tabSpec = tabHost.newTabSpec("login");
        tabSpec.setContent(R.id.login);
        tabSpec.setIndicator("login");
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("register");
        tabSpec.setIndicator("register");
        tabSpec.setContent(R.id.register);
        tabHost.addTab(tabSpec);

        tabHost.setCurrentTab(0);
    }
}
