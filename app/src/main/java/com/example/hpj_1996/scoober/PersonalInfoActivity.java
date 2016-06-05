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
    private EditText editAccount;
    private TextView fixPassword;
    private SQLiteDatabase db;
    private Button modifyPersonalInfo;
    private AccountDataBase accountDataBase;
    private String account;
    private Cursor accountCursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info);

        editName = (EditText)findViewById(R.id.edit_name);
        editAccount = (EditText)findViewById(R.id.edit_account);
        fixPassword = (TextView)findViewById(R.id.fix_password);
        modifyPersonalInfo = (Button)findViewById(R.id.modify_personal_info);
        accountDataBase = new AccountDataBase(this);

        try{
            account = getIntent().getStringExtra("account");
            accountCursor = accountDataBase.findAccount(account);
            modifyPersonalInfo.setOnClickListener(new ModifyListener());

            //set personal info
            editName.setText(accountCursor.getString(0));
            editAccount.setText(accountCursor.getString(1));
            fixPassword.setText(accountCursor.getString(2));
        }catch (NotFoundAccountException e){
            Log.d("error", e.getMessage());
        }
    }

    class ModifyListener implements View.OnClickListener{

        public void onClick(View v){
            try{
                accountDataBase.updateAccount(editName.getText().toString(),
                        editAccount.getText().toString());
                finish();
            }catch(UpdateFailedException e){
                Toast.makeText(PersonalInfoActivity.this,
                        e.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        }
    };
}

