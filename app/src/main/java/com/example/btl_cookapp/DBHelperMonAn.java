package com.example.btl_cookapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelperMonAn extends SQLiteOpenHelper {
    public static final String DB_NAME="MonAn.db";
    public static final int DB_VERSION= 8;
    public static final String TABLE_MON_AN= "tblmonAn";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "tenMonAn";
    public static final String COLUMN_CONG_THUC = "congThuc";
    public static final String COLUMN_ANH = "anh";
    public static final String COLUMN_USER = "user";
    public static final String COLUMN_TYPE = "type";
    private static final String TABLE_CREATE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_MON_AN + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NAME + " TEXT, " +
                    COLUMN_CONG_THUC + " TEXT, " +
                    COLUMN_ANH + " BLOB, " +
                    COLUMN_USER + " TEXT, " +
                    COLUMN_TYPE + " INTEGER" + ")";

    public DBHelperMonAn(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MON_AN);
        onCreate(db);
    }
}
