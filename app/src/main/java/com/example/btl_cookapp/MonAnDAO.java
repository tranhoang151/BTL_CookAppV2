package com.example.btl_cookapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class MonAnDAO {
    private SQLiteDatabase database;
    private DBHelperMonAn dbHelper;

    public MonAnDAO(Context context) {
        dbHelper = new DBHelperMonAn(context);
        database = dbHelper.getWritableDatabase();
    }

    public ArrayList<MonAn> getAllData() {
        ArrayList<MonAn> list = new ArrayList<>();
        Cursor cursor = database.query(DBHelperMonAn.TABLE_MON_AN, null, null, null, null, null, "tenMonAn ASC");
        if (cursor.moveToFirst()) {
            do {
                MonAn monAn = new MonAn(
                        cursor.getInt(cursor.getColumnIndexOrThrow(DBHelperMonAn.COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DBHelperMonAn.COLUMN_NAME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DBHelperMonAn.COLUMN_CONG_THUC)),
                        cursor.getBlob(cursor.getColumnIndexOrThrow(DBHelperMonAn.COLUMN_ANH)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DBHelperMonAn.COLUMN_USER)),
                        TypeMonAn.getTypeByValue(cursor.getInt(cursor.getColumnIndexOrThrow(DBHelperMonAn.COLUMN_TYPE)))
                );
                list.add(monAn);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }
    public Boolean insertData(MonAn monAn){
        ContentValues contentValues=new ContentValues();
        contentValues.put(DBHelperMonAn.COLUMN_NAME,monAn.getTenMonAn());
        contentValues.put(DBHelperMonAn.COLUMN_CONG_THUC,monAn.getCongThuc());
        contentValues.put(DBHelperMonAn.COLUMN_USER,monAn.getUser());
        contentValues.put(DBHelperMonAn.COLUMN_ANH,monAn.getImage());
        contentValues.put(DBHelperMonAn.COLUMN_TYPE,monAn.getType().getValue());
        long ketqua= database.insert(DBHelperMonAn.TABLE_MON_AN, null,contentValues);
        if(ketqua<0)
            return  false;
        else
            return true;
    }
    /*public boolean updateData(MonAn monAn){
        ContentValues contentValues=new ContentValues();
        contentValues.put(DBHelperMonAn.COLUMN_NAME,monAn.getTenMonAn());
        contentValues.put(DBHelperMonAn.COLUMN_CONG_THUC,monAn.getCongThuc());
        contentValues.put(DBHelperMonAn.COLUMN_USER,monAn.getUser());
        contentValues.put(DBHelperMonAn.COLUMN_ANH,monAn.getImage());
        contentValues.put(DBHelperMonAn.COLUMN_TYPE,monAn.getType().getValue());
        long ketqua= database.update(DBHelperMonAn.TABLE_MON_AN,contentValues,"ID=?",new String[]{ String.valueOf(monAn.getID()) });
        if(ketqua<0)
            return  false;
        else
            return true;
    }*/
    public boolean deleteData(MonAn monAn){
        long ketqua= database.delete(DBHelperMonAn.TABLE_MON_AN, "ID=?", new String[]{ String.valueOf(monAn.getID()) });
        if(ketqua<0)
            return  false;
        else
            return true;
    }

    public MonAn getMonAnById(int id) {
        MonAn monAn = null;
        Cursor cursor = database.query(DBHelperMonAn.TABLE_MON_AN,
                null,
                "ID=?",
                new String[]{String.valueOf(id)},
                null, null, null);

        if (cursor.moveToFirst()) {
            monAn = new MonAn(
                    cursor.getInt(cursor.getColumnIndexOrThrow(DBHelperMonAn.COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DBHelperMonAn.COLUMN_NAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DBHelperMonAn.COLUMN_CONG_THUC)),
                    cursor.getBlob(cursor.getColumnIndexOrThrow(DBHelperMonAn.COLUMN_ANH)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DBHelperMonAn.COLUMN_USER)),
                    TypeMonAn.getTypeByValue(cursor.getInt(cursor.getColumnIndexOrThrow(DBHelperMonAn.COLUMN_TYPE)))
            );
        }
        cursor.close();
        return monAn;
    }

    public boolean updateData(MonAn monAn) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelperMonAn.COLUMN_NAME, monAn.getTenMonAn());
        contentValues.put(DBHelperMonAn.COLUMN_CONG_THUC, monAn.getCongThuc());
        contentValues.put(DBHelperMonAn.COLUMN_USER, monAn.getUser());
        contentValues.put(DBHelperMonAn.COLUMN_ANH, monAn.getImage());
        contentValues.put(DBHelperMonAn.COLUMN_TYPE, monAn.getType().getValue());

        long ketqua = database.update(
                DBHelperMonAn.TABLE_MON_AN,
                contentValues,
                "ID=?",
                new String[]{ String.valueOf(monAn.getID()) }
        );

        return ketqua > 0;
    }
}
