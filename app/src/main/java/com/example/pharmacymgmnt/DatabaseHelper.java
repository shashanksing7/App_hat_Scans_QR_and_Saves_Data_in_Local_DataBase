package com.example.pharmacymgmnt;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "text_database.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "text_table";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TEXT = "text_column";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE " + TABLE_NAME + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_TEXT + " TEXT)";
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public long insertText(String text) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TEXT, text);
        long id = db.insert(TABLE_NAME, null, values);
        db.close();
        return id;
    }

    public ArrayList<String> searchByText(String query) {
        ArrayList<String> searchResults = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_NAME +
                " WHERE " + COLUMN_TEXT + " LIKE '%" + query + "%'";
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                String text = cursor.getString(cursor.getColumnIndex(COLUMN_TEXT));
                searchResults.add(text);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return searchResults;
    }
//public ArrayList<String> searchByText(String query) {
//    ArrayList<String> searchResults = new ArrayList<>();
//    SQLiteDatabase db = this.getReadableDatabase();
//    String selectQuery = "SELECT * FROM " + TABLE_NAME +
//            " WHERE " + COLUMN_TEXT + " LIKE '%" + query + "%'";
//    Cursor cursor = db.rawQuery(selectQuery, null);
//    if (cursor != null) {
//        Log.d("DatabaseHelper", "Number of columns in cursor: " + cursor.getColumnCount());
//        if (cursor.moveToFirst()) {
//            do {
//                int columnIndex = cursor.getColumnIndex(COLUMN_TEXT);
//                if (columnIndex >= 0) {
//                    String text = cursor.getString(columnIndex);
//                    searchResults.add(text);
//                } else {
//                    Log.e("DatabaseHelper", "COLUMN_TEXT not found in the cursor.");
//                }
//            } while (cursor.moveToNext());
//        } else {
//            Log.d("DatabaseHelper", "No results found for the query.");
//        }
//        cursor.close();
//    } else {
//        Log.e("DatabaseHelper", "Cursor is null. Error in the query.");
//    }
//    db.close();
//    return searchResults;
//}

}
