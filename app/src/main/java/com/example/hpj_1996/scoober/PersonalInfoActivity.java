package com.example.hpj_1996.scoober;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class PersonalInfoActivity extends AppCompatActivity {
    private final static String NAME = "NAME";
    private final static String ACCOUNT = "ACCOUNT";
    private final static String PERSONAL_INFO = "PERSONAL_INFO";

    private EditText editName;
    private EditText editAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info);

        editName = (EditText)findViewById(R.id.edit_name);
        editAccount = (EditText)findViewById(R.id.edit_account);

        SharedPreferences pref = getSharedPreferences(PERSONAL_INFO,
                MODE_PRIVATE);
        editName.setText(pref.getString(NAME, "none"));
        editAccount.setText(pref.getString(ACCOUNT, "none"));
    }

    @Override
    public void onStop() {
        super.onStop();
        SharedPreferences pref = getSharedPreferences(PERSONAL_INFO,
                MODE_PRIVATE);
        SharedPreferences.Editor preEdt = pref.edit();
        preEdt.putString(NAME, editName.getText().toString());
        preEdt.putString(ACCOUNT, editAccount.getText().toString());
        preEdt.commit();
    }
}
