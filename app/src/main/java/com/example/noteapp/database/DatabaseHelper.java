package com.example.noteapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.noteapp.models.Note;

import java.sql.Date;
import android.database.DatabaseErrorHandler;
import java.util.ArrayList;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    //Constants Declarations for column and table names
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "noteapp.db";
    public static final String TABLE_NAME_NOTES = "Notes";

    //Column names
    public static String COLUMN_NOTE_ID = "id";
    public static String COLUMN_NOTE_TITLE = "noteTitle";
    public static String COLUMN_NOTE_DESC = "noteDesc";
    public static String COLUMN_NOTE_CREATED_DATE = "createdDate";

    //SQL Statements
    private static final String SQL_CREATE_NOTES_TABLE = "CREATE TABLE " + TABLE_NAME_NOTES + " (" +
            COLUMN_NOTE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMN_NOTE_TITLE + " VARCHAR(100)," +
            COLUMN_NOTE_DESC + " VARCHAR(250)," +
            COLUMN_NOTE_CREATED_DATE + " DATETIME" +
            ")";

    private static final String SQL_DELETE_NOTES =
            "DROP TABLE IF EXISTS " + TABLE_NAME_NOTES;


    public DatabaseHelper(@Nullable Context context, @Nullable String name,
                          @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    /**
     * Insert new note
     */
    public long createNewNote(Note note) {
        // Gets the data repository in write mode
        SQLiteDatabase db = getWritableDatabase();
        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(COLUMN_NOTE_TITLE, note.getTitle());
        values.put(COLUMN_NOTE_DESC, note.getDetail());
        values.put(COLUMN_NOTE_CREATED_DATE, note.getCreatedDateInString());

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(TABLE_NAME_NOTES, null, values);
        db.close();
        return newRowId;
    }

    /*
     * Delet note by ID
     * **/
    public void deleteNote(Note note) {
        String deleteQuery = "DELETE FROM " + TABLE_NAME_NOTES +
                " WHERE " + COLUMN_NOTE_ID + "=" + note.getId() + ";";
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(deleteQuery);
        db.close();
    }


    public Note getNote(int id) {
        String selectQuery = "SELECT * FROM " + TABLE_NAME_NOTES +
                " WHERE " + COLUMN_NOTE_ID + "=" + id + ";";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        Note item = new Note();
        if (cursor.moveToFirst()) {
            do {
                item.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_NOTE_ID)));
                item.setTitle(cursor.getString(cursor.getColumnIndex(COLUMN_NOTE_TITLE)));
                item.setDetail(cursor.getString(cursor.getColumnIndex(COLUMN_NOTE_DESC)));
                item.setDateInString(cursor.getString(cursor.getColumnIndex(COLUMN_NOTE_CREATED_DATE)));
            } while (cursor.moveToNext());
        }
        db.close();
        return item;

    }


    public ArrayList<Note> getAllNotes() {
        String selectQuery = "SELECT * FROM " + TABLE_NAME_NOTES + ";";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        ArrayList<Note> itemsList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                Note item = new Note();
                item.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_NOTE_ID)));
                item.setTitle(cursor.getString(cursor.getColumnIndex(COLUMN_NOTE_TITLE)));
                item.setDetail(cursor.getString(cursor.getColumnIndex(COLUMN_NOTE_DESC)));
                item.setDateInString(cursor.getString(cursor.getColumnIndex(COLUMN_NOTE_CREATED_DATE)));
                itemsList.add(item);
            } while (cursor.moveToNext());
        }
        db.close();
        return itemsList;

    }

    public int updateNote(Note note) {
        int rowsAffected = -1;
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NOTE_TITLE, note.getTitle());
        cv.put(COLUMN_NOTE_DESC, note.getDetail());

        SQLiteDatabase db = this.getWritableDatabase();

        try {
            rowsAffected = db.update(TABLE_NAME_NOTES, cv, COLUMN_NOTE_ID + "=" +
                    note.getId(), null);
        } catch (SQLException exp) {
            exp.printStackTrace();
        }
        db.close();
        return rowsAffected;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_NOTES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_NOTES);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }

    @Override
    public String getDatabaseName() {
        return super.getDatabaseName();
    }

    @Override
    public SQLiteDatabase getWritableDatabase() {
        return super.getWritableDatabase();
    }

    @Override
    public SQLiteDatabase getReadableDatabase() {
        return super.getReadableDatabase();
    }


}
