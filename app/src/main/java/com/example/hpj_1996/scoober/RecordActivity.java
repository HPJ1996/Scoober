package com.example.hpj_1996.scoober;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class RecordActivity extends AppCompatActivity {

    private ListView listView;
    SQLiteDatabase database;
    ArrayList<String> titlelist;

    private String[] list = {"120.648274 ,24.179955"};
    private ArrayAdapter<String> listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        listView = (ListView)findViewById(R.id.list_view);
        listView.setOnItemClickListener(itemClickListenerlick);
        listView.setOnItemLongClickListener(itemLongClickListenerlick);

    }

    @Override
    protected void onPause() {
        super.onPause();
        database.close();
    }

    @Override
    protected void onResume() {
        super.onResume();

        DataBaseOpenHelper openhelper = new DataBaseOpenHelper(this);
        database = openhelper.getWritableDatabase();

        titlelist = NoteDatabase.getTitleList(database);
        ArrayAdapter<String> adapter =new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, titlelist);
        listView.setAdapter(adapter);
    }

    OnItemLongClickListener itemLongClickListenerlick = new OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> av, View v, int position, long id) {
            String title = titlelist.get(position);
            NoteDatabase.delNote(database, title);
            titlelist = NoteDatabase.getTitleList(database);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>
                    (RecordActivity.this, android.R.layout.simple_list_item_1, titlelist);
            listView.setAdapter(adapter);
            return false;
        }
    };

    OnItemClickListener itemClickListenerlick = new OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> av, View v, int position, long id) {

        }
    };


}



