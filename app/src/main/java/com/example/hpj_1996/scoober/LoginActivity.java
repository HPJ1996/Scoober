package com.example.hpj_1996.scoober;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class LoginActivity extends AppCompatActivity {

    TabHost tabHost;

    //Login-component
    private Button loginButton;
    private AutoCompleteTextView account;
    private EditText password;
    private TextView errorMessage;

    //Register-component
    private EditText registerName;
    private AutoCompleteTextView registerAccount;
    private EditText registerPassword;
    private EditText registerTwicePassword;
    private Button registerButton;

    //Data base keys
    public final static String NAME = "NAME";
    public final static String ACCOUNT = "ACCOUNT";
    public final static String PASSWORD = "PASSWORD";
    public final static String REGISTER_ACCOUNT = "REGISTER_ACCOUNT";
    public final static String ACCOUNT_TABLE = "ACCOUNT_TABLE";
    public final static String CREATETABLE = "create table " +
            ACCOUNT_TABLE + "(name, account, password);";

    private SQLiteDatabase db;
    private DBOpenHelper openhelper;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTab();

        //Login-component
        account = (AutoCompleteTextView)findViewById(R.id.account);
        password = (EditText)findViewById(R.id.password);
        loginButton = (Button)findViewById(R.id.action_sign_in);
        loginButton.setOnClickListener(loginListener);
        errorMessage = (TextView)findViewById(R.id.error_message);

        //Register-component
        registerAccount = (AutoCompleteTextView)findViewById(R.id.register_account);
        registerName = (EditText)findViewById(R.id.register_name);
        registerPassword = (EditText)findViewById(R.id.register_password);
        registerTwicePassword = (EditText)findViewById(R.id.register_twice_password);
        registerButton = (Button)findViewById(R.id.action_register);
        registerButton.setOnClickListener(registerListener);

        //Account data base
        openhelper = new DBOpenHelper(this);
        db = openhelper.getWritableDatabase();
    }

    private View.OnClickListener loginListener = new View.OnClickListener() {

        public void onClick(View v) {
//            if(!checkAccount(account.getText().toString(), password.getText().toString())) {
//                return;
//            }
            Intent intent = new Intent();
            intent.setClass(LoginActivity.this, SideActivity.class);
            intent.putExtra(ACCOUNT, account.getText().toString());

            startActivity(intent);
        }
    };

    private View.OnClickListener registerListener = new View.OnClickListener() {

        public void onClick(View v) {
            ContentValues cv = new ContentValues();
            cv.put(ACCOUNT, registerAccount.getText().toString());
            cv.put(NAME, registerName.getText().toString());
            cv.put(PASSWORD, registerPassword.getText().toString());

            db.insert(ACCOUNT_TABLE, null, cv);

            Cursor cursor = db.rawQuery("select * from " + ACCOUNT_TABLE, null);

            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++) {
                Log.d("db",cursor.getString(0));
                cursor.moveToNext();
            }

            tabHost.setCurrentTab(0);
            Toast.makeText(LoginActivity.this,"Complete register!", Toast.LENGTH_SHORT).show();
        }
    };

    private boolean checkAccount(String account, String password){
        SharedPreferences pref = getSharedPreferences(account, MODE_PRIVATE);
        if(pref.getString(password, "").equals("")){
            errorMessage.setText("Account or password error!");
            return false;
        }
        return true;
    }


    private void setTab(){
        tabHost = (TabHost)findViewById(R.id.tabHost);
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

    class DBOpenHelper extends SQLiteOpenHelper {

        public DBOpenHelper(Context context) {
            super(context, "accounts.db", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATETABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
        }
    }
}


