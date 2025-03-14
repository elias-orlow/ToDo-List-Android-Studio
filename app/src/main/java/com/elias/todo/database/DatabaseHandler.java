package com.elias.todo.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.elias.todo.model.Task;
import com.elias.todo.utils.Util;

public class DatabaseHandler extends SQLiteOpenHelper {
    public DatabaseHandler(Context context) {
        super(context, Util.DATABASE_NAME, null, Util.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TASK_TABLE = "CREATE TABLE " + Util.TABLE_NAME + " ("
                + Util.KEY_ID + " INTEGER PRIMARY KEY, "
                + Util.KEY_DESCRIPTION + " TEXT, "
                + Util.KEY_TIME + " TEXT, "
                + Util.KEY_LEVEL + " TEXT, "
                + Util.KEY_FOLDER + " TEXT, "
                + Util.KEY_STATUS + " TEXT " + ")";

        db.execSQL(CREATE_TASK_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Util.TABLE_NAME);
        onCreate(db);
    }

    public void addTask (Task task){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Util.KEY_DESCRIPTION, task.getDescription());
        values.put(Util.KEY_TIME, task.getTime());
        values.put(Util.KEY_LEVEL, task.getLevel());
        values.put(Util.KEY_FOLDER, task.getFolder());
        values.put(Util.KEY_STATUS, task.getStatus());

        db.insert(Util.TABLE_NAME, null, values);
        db.close();
    }

    public Task getTask(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(Util.TABLE_NAME, new String[] {Util.KEY_ID, Util.KEY_DESCRIPTION,
                Util.KEY_TIME, Util.KEY_LEVEL, Util.KEY_FOLDER, Util.KEY_STATUS}, Util.KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null,null,null);
        if (cursor != null){
            cursor.moveToFirst();
        }
        Task task = new Task(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2),
                cursor.getString(3), cursor.getString(4), cursor.getString(5));

        cursor.close();
        return task;
    }

    public int updateTask(Task task){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Util.KEY_DESCRIPTION, task.getDescription());
        values.put(Util.KEY_TIME, task.getTime());
        values.put(Util.KEY_LEVEL, task.getLevel());
        values.put(Util.KEY_FOLDER, task.getFolder());
        values.put(Util.KEY_STATUS, task.getStatus());

        return db.update(Util.TABLE_NAME, values, Util.KEY_ID + "=?", new String[]{String.valueOf(task.getId())});
    }

    public void deleteTask(Task task){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Util.TABLE_NAME, Util.KEY_ID + "=?", new String[]{String.valueOf(task.getId())});
        db.close();
    }
}
