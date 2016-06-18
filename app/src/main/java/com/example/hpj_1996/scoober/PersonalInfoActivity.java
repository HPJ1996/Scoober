package com.example.hpj_1996.scoober;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class PersonalInfoActivity extends AppCompatActivity {

    private EditText editName;
    private TextView fixAccount;
    private EditText editPassword;
    private Button modifyPersonalInfo;
    private AccountDataBase accountDataBase;
    private String account;
    private Cursor accountCursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info);

        editName = (EditText)findViewById(R.id.edit_name);
        fixAccount = (TextView) findViewById(R.id.fix_account);
        editPassword = (EditText)findViewById(R.id.edit_password);
        modifyPersonalInfo = (Button)findViewById(R.id.modify_personal_info);
        accountDataBase = new AccountDataBase(this);

        try{
            account = getIntent().getStringExtra("account");
            accountCursor = accountDataBase.findAccount(account);
            modifyPersonalInfo.setOnClickListener(new ModifyListener());

            //initializing personal info messages
            editName.setText(accountCursor.getString(0));
            fixAccount.setText(accountCursor.getString(1));
            editPassword.setText(accountCursor.getString(2));
        }catch (NotFoundAccountException e){
            Log.d("error", e.getMessage());
        }
    }

    class ModifyListener implements View.OnClickListener{
        public void onClick(View v){
            String name = editName.getText().toString();
            String account = fixAccount.getText().toString();
            String password = editPassword.getText().toString();

            try{
                accountDataBase.updateAccount(name, account, password);
                finish();
            }catch(UpdateFailedException e){
                Toast.makeText(PersonalInfoActivity.this,
                        e.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        }
    };
}

