package com.example.hpj_1996.scoober;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;


public class NoteDatabase {
    final static String NOTETABLE = "notetable";

    static ArrayList<String> getTitleList(SQLiteDatabase db) {
        ArrayList<String> titlelist = new ArrayList<String>();
        Cursor c = db.rawQuery("select title from " + NOTETABLE, null);
        c.moveToFirst();
        for (int i = 0; i < c.getCount(); i++) {
            int titleIndex = c.getColumnIndex("title");
            String title = c.getString(titleIndex);
            titlelist.add(title);
            c.moveToNext();
        }
        return titlelist;
    }

    static String getBody(SQLiteDatabase db, String title) {
        Cursor c = db.rawQuery("select * from " + NOTETABLE + " where title='" + title +"';", null);
        c.moveToFirst();
        return c.getString(c.getColumnIndex("body"));
    }

    static void addNote(SQLiteDatabase db, String title, String body) {

        ArrayList<String> titlelist = getTitleList(db);
        boolean isNew = true;
        for (int i = 0; i < titlelist.size(); i++) {
            if (title.equals(titlelist.get(i))) {
                isNew = false;
                break;
            }
        }

        ContentValues cv = new ContentValues();
        cv.put("title", title);
        cv.put("body", body);

        if (isNew == true) {
            db.insert(NOTETABLE, null, cv);
        } else {
            db.update(NOTETABLE, cv, "title='" + title + "'", null);
        }
    }

    static void delNote(SQLiteDatabase db, String title) {
        db.delete(NOTETABLE, "title=" + "'" +
                title + "'", null);
    }
}

class DataBaseOpenHelper extends SQLiteOpenHelper {

    public DataBaseOpenHelper(Context context) {
        super(context, "note.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + NoteDatabase.NOTETABLE + "(title, body);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
