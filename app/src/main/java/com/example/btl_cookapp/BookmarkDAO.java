package com.example.btl_cookapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class BookmarkDAO {
    private SQLiteDatabase database;
    private DBHelperBookmark dbHelper;
    private MonAnDAO monAnDAO;

    public BookmarkDAO(Context context) {
        dbHelper = new DBHelperBookmark(context);
        database = dbHelper.getWritableDatabase();
        monAnDAO = new MonAnDAO(context);
    }

    public boolean addBookmark(String username, int monanId) {
        // Check if already bookmarked
        if (isBookmarked(username, monanId)) {
            return false;
        }

        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelperBookmark.COLUMN_USERNAME, username);
        contentValues.put(DBHelperBookmark.COLUMN_MONAN_ID, monanId);

        long result = database.insert(DBHelperBookmark.TABLE_BOOKMARK, null, contentValues);
        return result != -1;
    }

    public boolean removeBookmark(String username, int monanId) {
        long result = database.delete(
                DBHelperBookmark.TABLE_BOOKMARK,
                DBHelperBookmark.COLUMN_USERNAME + " = ? AND " +
                        DBHelperBookmark.COLUMN_MONAN_ID + " = ?",
                new String[]{username, String.valueOf(monanId)}
        );

        return result > 0;
    }

    public boolean isBookmarked(String username, int monanId) {
        Cursor cursor = database.query(
                DBHelperBookmark.TABLE_BOOKMARK,
                null,
                DBHelperBookmark.COLUMN_USERNAME + " = ? AND " +
                        DBHelperBookmark.COLUMN_MONAN_ID + " = ?",
                new String[]{username, String.valueOf(monanId)},
                null, null, null
        );

        boolean bookmarked = cursor.getCount() > 0;
        cursor.close();
        return bookmarked;
    }

    public ArrayList<MonAn> getBookmarkedMonAn(String username) {
        ArrayList<MonAn> bookmarkedList = new ArrayList<>();

        Cursor cursor = database.query(
                DBHelperBookmark.TABLE_BOOKMARK,
                new String[]{DBHelperBookmark.COLUMN_MONAN_ID},
                DBHelperBookmark.COLUMN_USERNAME + " = ?",
                new String[]{username},
                null, null, null
        );

        if (cursor.moveToFirst()) {
            do {
                int monanId = cursor.getInt(cursor.getColumnIndexOrThrow(DBHelperBookmark.COLUMN_MONAN_ID));
                MonAn monAn = monAnDAO.getMonAnById(monanId);
                if (monAn != null) {
                    bookmarkedList.add(monAn);
                }
            } while (cursor.moveToNext());
        }

        cursor.close();
        return bookmarkedList;
    }
}