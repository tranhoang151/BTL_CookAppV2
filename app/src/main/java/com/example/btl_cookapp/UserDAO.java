package com.example.btl_cookapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class UserDAO {
    private SQLiteDatabase database;
    private DBHelperLogin dbHelper;

    public UserDAO(Context context) {
        dbHelper = new DBHelperLogin(context);
        database = dbHelper.getWritableDatabase();
    }

    public Boolean insertData(String username, String password){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelperLogin.COLUMN_NAME,username);
        contentValues.put(DBHelperLogin.COLUMN_PASS, password);
        long ketqua= database.insert(DBHelperLogin.TABLE_USER, null,contentValues);
        return ketqua >= 0;
    }
    public  boolean updateData(int id, String password){
        ContentValues contentValues= new ContentValues();
        contentValues.put("password", password);
        long ketqua= database.update(DBHelperLogin.TABLE_USER, contentValues,"id = ?",new String[]{ String.valueOf(id) });
        return ketqua >= 0;
    }
    public boolean deleteData(int id){
        long ketqua= database.delete(DBHelperLogin.TABLE_USER,"id = ?", new String[]{ String.valueOf(id) });
        return ketqua >= 0;
    }
    public Boolean checkUsername(String username){
        Cursor cursor = database.query(
                DBHelperLogin.TABLE_USER,          // Tên bảng
                new String[]{DBHelperLogin.COLUMN_NAME}, // Cột cần lấy
                DBHelperLogin.COLUMN_NAME + " = ?", // Điều kiện WHERE
                new String[]{username},              // Tham số của WHERE
                null, null, null
        );
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }
    public boolean checkUsernamePassword(String username, String password){
        Cursor cursor = database.query(
                DBHelperLogin.TABLE_USER,
                new String[]{ DBHelperLogin.COLUMN_NAME },
                DBHelperLogin.COLUMN_NAME + " = ? AND " + DBHelperLogin.COLUMN_PASS + " = ?",
                new String[]{ username, password },
                null, null, null);

        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }


}
