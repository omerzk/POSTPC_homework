package com.example.cptsop.app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Date;


public class DBOpenhelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "todo_db";
    private static final int DATABASE_VERSION = 2;

    private static final String TABLE_NAME = "TODO_ITEMS";
    private static String ID_COLUMN = "ID INTEGER PRIMARY KEY AUTOINCREMENT";
    private static String TITLE_COLUMN = "TITLE TEXT";
    private static String DUE_COLUMN = "DUE LONG";
    private static final String TABLE_CREATE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(" +
                    ID_COLUMN + ", " +
                    TITLE_COLUMN + ", " +
                    DUE_COLUMN + ");";

    DBOpenhelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public boolean insert(TodoItem item) {
        ContentValues todoValues = new ContentValues();
        todoValues.put("TITLE", item.task);
        if (item.due != null) {
            todoValues.put("DUE", item.due.getTime());
        }
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_NAME, null, todoValues);
        db.close();
        return true;
    }

    public boolean delete(TodoItem todoItem) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_NAME, "title = ?", new String[]{todoItem.task});
        db.close();
        return true;
    }


    public ArrayList<TodoItem> GetAll() {
        String selectQuery = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        ArrayList<TodoItem> list = new ArrayList<TodoItem>();
        Date due;
        if (cursor.getCount() > 0 && cursor.moveToFirst()) {
            do {
                int TEXT_IDX = 1;
                String title = cursor.getString(TEXT_IDX);
                int DUE_IDX = 2;
                long milliSecond = cursor.getLong(DUE_IDX);
                if (milliSecond != 0) {
                    due = new Date(cursor.getLong(2));
                    list.add(new TodoItem(due, title));
                } else {
                    list.add(new TodoItem(null, title));
                }

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return list;
    }

}
