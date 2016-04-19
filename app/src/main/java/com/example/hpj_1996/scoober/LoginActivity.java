package com.example.hpj_1996.scoober;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    private Button login_button;
    private AutoCompleteTextView account;
    private EditText password;

    private final int ACCOUNT = 0;
    private final int PASSWORD = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTab();

        account = (AutoCompleteTextView)findViewById(R.id.prompt_account);
        password = (EditText)findViewById(R.id.password);

        login_button = (Button)findViewById(R.id.action_sign_in);
        login_button.setOnClickListener(loginButtonListener);
    }

    private View.OnClickListener loginButtonListener = new View.OnClickListener() {

        public void onClick(View v) {
            Intent intent = new Intent();
            intent.setClass(LoginActivity.this, SideActivity.class);
//            intent.putExtra(ACCOUNT, account.getText().toString());
//            intent.putExtra(PASSWORD, password.getText().toString());

            //startActivityForResult(intent, REQUEST_INPUT_NAME);

            startActivity(intent);

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
