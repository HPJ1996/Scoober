package com.example.hpj_1996.scoober;

import android.content.ContentValues;
import android.content.SharedPreferences;
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
    private final static String NAME = "NAME";
    private final static String ACCOUNT = "ACCOUNT";
    private final static String PERSONAL_INFO = "PERSONAL_INFO";

    private EditText editName;
    private EditText editAccount;
    private TextView fixPassword;
    private SQLiteDatabase db;
    private Button applyPersonalInfo;
    String account;
    Cursor accountCursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info);

        editName = (EditText)findViewById(R.id.edit_name);
        editAccount = (EditText)findViewById(R.id.edit_account);
        fixPassword = (TextView)findViewById(R.id.fix_password);
        applyPersonalInfo = (Button)findViewById(R.id.apply_personal_info);

        try{
            account = getIntent().getStringExtra("account");
            accountCursor = findAccount(account);
            applyPersonalInfo.setOnClickListener(new ApplyListener());

            Log.d("info", "name: " + accountCursor.getString(0));
            Log.d("info", "account: " + accountCursor.getString(1));
            Log.d("info", "password: " + accountCursor.getString(2));

            //set personal info
            editName.setText(accountCursor.getString(0));
            editAccount.setText(accountCursor.getString(1));
            fixPassword.setText(accountCursor.getString(2));
        }catch (NotFoundAccountException e){
            Log.d("info", e.getMessage());
        }
    }

    private Cursor findAccount(String account) throws NotFoundAccountException{
        db = new DBOpenHelper(this).getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + LoginActivity.ACCOUNT_TABLE, null);

        cursor.moveToFirst();
        for (int i = 0; i < cursor.getCount(); i++) {
            if(cursor.getString(1).equals(account)){
                return cursor;
            }
            cursor.moveToNext();
        }
        throw new NotFoundAccountException("Not found account: " + account);
    }

    class ApplyListener implements View.OnClickListener{

        public void onClick(View v){
            ContentValues contentValues = new ContentValues();
            contentValues.put("name", editName.getText().toString());
            contentValues.put("account", editAccount.getText().toString());
            db.update(LoginActivity.ACCOUNT_TABLE, contentValues, "account='" + account + "'", null);
            Log.d("info","change name: " + accountCursor.getString(0));
        }
    };
}

class NotFoundAccountException extends Exception{
    public NotFoundAccountException(String message){
        super(message);
    }
}