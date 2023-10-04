package com.example.myapplication;

import android.database.sqlite.SQLiteDatabase;

public class MyDb {

    public static final String DB = "MyDB";
    public static final String KEY_ROWID = "_id";
    public static final String KEY_CODE = "code";
    public static final String KEY_NAME = "name";
    public static final String KEY_CONTINENT = "continent";

    public static final String SQLITE_TABLE = "Country";
    public static final String DATABASE_CREATE =
            "CREATE TABLE if not exists " + SQLITE_TABLE + " (" +
                    KEY_ROWID + " integer PRIMARY KEY autoincrement," +
                    KEY_CODE + "," +
                    KEY_NAME + "," +
                    KEY_CONTINENT + "," +
                    " UNIQUE (" + KEY_CODE +"));";

    public static void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
    }
}
