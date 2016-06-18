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

    //Login components
    private Button loginButton;
    private AutoCompleteTextView account;
    private EditText password;
    private TextView loginErrorMessage;

    //Register components
    private EditText registerName;
    private AutoCompleteTextView registerAccount;
    private EditText registerPassword;
    private EditText registerTwicePassword;
    private Button registerButton;
    private TextView registerErrorMessage;

    //Account Data Base
    private AccountDataBase accountDataBase;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTab();

        //Login components
        account = (AutoCompleteTextView)findViewById(R.id.account);
        password = (EditText)findViewById(R.id.password);
        loginButton = (Button)findViewById(R.id.action_sign_in);
        loginButton.setOnClickListener(loginListener);
        loginErrorMessage = (TextView)findViewById(R.id.login_error_message);

        //Register components
        registerAccount = (AutoCompleteTextView)findViewById(R.id.register_account);
        registerName = (EditText)findViewById(R.id.register_name);
        registerPassword = (EditText)findViewById(R.id.register_password);
        registerTwicePassword = (EditText)findViewById(R.id.register_twice_password);
        registerButton = (Button)findViewById(R.id.action_register);
        registerButton.setOnClickListener(registerListener);
        registerErrorMessage = (TextView)findViewById(R.id.register_error_message);

        accountDataBase = new AccountDataBase(this);

    }

    private View.OnClickListener loginListener = new View.OnClickListener() {

        public void onClick(View v) {
        if(accountDataBase.verifyAccount(account.getText().toString(), password.getText().toString())) {

            Intent intent = new Intent();
            intent.setClass(LoginActivity.this, SideActivity.class);
            intent.putExtra("account", account.getText().toString());
            startActivity(intent);
        }else{
            loginErrorMessage.setText("帳戶或密碼錯誤！");
        }
        }
    };

    private View.OnClickListener registerListener = new View.OnClickListener() {

        public void onClick(View v) {
        String name = registerName.getText().toString();
        String account = registerAccount.getText().toString();
        String password = registerPassword.getText().toString();
        String twicePassword = registerTwicePassword.getText().toString();

        try{
            if(!password.equals(twicePassword))
                throw new RegisterFailedException("密碼與第二次密碼不相符");
            accountDataBase.insertAccount(name, account, password);
            tabHost.setCurrentTab(0);
            Toast.makeText(LoginActivity.this, "完成註冊", Toast.LENGTH_SHORT).show();

        }catch(RegisterFailedException e){
            registerErrorMessage.setText(e.getMessage());
        }
        }
    };


    private void setTab(){
        tabHost = (TabHost)findViewById(R.id.tabHost);
        tabHost.setup();

        TabHost.TabSpec tabSpec = tabHost.newTabSpec("login");
        tabSpec.setContent(R.id.login);
        tabSpec.setIndicator("登入");
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("register");
        tabSpec.setIndicator("註冊");
        tabSpec.setContent(R.id.register);
        tabHost.addTab(tabSpec);

        tabHost.setCurrentTab(0);
    }
}

class AccountDataBase{
    public final static String ACCOUNT_TABLE = "ACCOUNT_TABLE";
    public final static String CREATE_TABLE = "create table " +
            ACCOUNT_TABLE + "(name, account, password);";
    private Context context;

    private SQLiteDatabase database;

    public AccountDataBase(Context context){
        this.context = context;
        DBOpenHelper openhelper = new DBOpenHelper(context);
        database = openhelper.getWritableDatabase();
    }

    private void checkRegisterValid(String name, String account, String password)
            throws RegisterFailedException {
        String errorMessage = "";
        if (name.equals("")) {
            errorMessage += "姓名欄不得為空白！\n";
        }
        if (account.equals("")) {
            errorMessage += "帳戶欄不得為空白！\n";
        }
        if (password.equals("")) {
            errorMessage += "密碼欄不得為空白！\n";
        }
        if (existAccount(account)) {
            errorMessage += "存在相同帳戶！\n";
        }

        if (!errorMessage.equals(""))
            throw new RegisterFailedException(errorMessage.substring(0, errorMessage.length()-1));
    }

    private void checkUpdateValid(String name, String password) throws UpdateFailedException{
        String errorMessage = "";
        if (name.equals("")) {
            errorMessage += "姓名欄不得為空白！\n";
        }
        if (password.equals("")) {
            errorMessage += "密碼欄不得為空白！\n";
        }

        if (!errorMessage.equals(""))
            throw new UpdateFailedException(errorMessage.substring(0, errorMessage.length()-1));
    }

    public void insertAccount(String name, String account, String password)
            throws RegisterFailedException{
        checkRegisterValid(name, account, password);
        ContentValues cv = new ContentValues();
        cv.put("name", name);
        cv.put("account", account);
        cv.put("password", password);

        database.insert(ACCOUNT_TABLE, null, cv);
    }

    public Cursor findAccount(String account) throws NotFoundAccountException{
        Cursor cursor = database.rawQuery("select * from " + ACCOUNT_TABLE, null);

        cursor.moveToFirst();
        for (int i = 0; i < cursor.getCount(); i++) {
            if(cursor.getString(1).equals(account)){
                return cursor;
            }
            cursor.moveToNext();
        }
        throw new NotFoundAccountException("找不到帳戶： " + account);
    }

    public boolean existAccount(String account){
        try{
            findAccount(account);
            return true;
        }catch(NotFoundAccountException e){
            return false;
        }
    }


    public boolean verifyAccount(String account, String password){
        Cursor cursor = database.rawQuery("select * from " + ACCOUNT_TABLE, null);

        cursor.moveToFirst();
        for (int i = 0; i < cursor.getCount(); i++) {
            if(cursor.getString(1).equals(account)){
                if(cursor.getString(2).equals(password)){
                    cursor.close();
                    return true;
                }else break;
            }
            cursor.moveToNext();
        }
        cursor.close();
        return false;
    }

    public void updateAccount(String name, String account, String password)throws UpdateFailedException{
        checkUpdateValid(name, password);
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("password", password);
        database.update(ACCOUNT_TABLE, contentValues, "account='" + account + "'", null);
        Toast.makeText(context, "完成修改", Toast.LENGTH_SHORT).show();
    }
}

class DBOpenHelper extends SQLiteOpenHelper {

    public DBOpenHelper(Context context) {
        super(context, "accounts.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(AccountDataBase.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
    }
}

class RegisterFailedException extends Exception{
    public RegisterFailedException(String message){
        super(message);
    }
}

class UpdateFailedException extends Exception{
    public UpdateFailedException(String message){
        super(message);
    }
}

class NotFoundAccountException extends Exception{
    public NotFoundAccountException(String message){
        super(message);
    }
}



