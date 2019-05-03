package com.example.ruben.drealitym.HelperClasses;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.ruben.drealitym.HelperClasses.DreamContract.DreamEntry;

public class DreamDbHelper extends SQLiteOpenHelper {

    private static final String LOG_TAG = "DreamDbHelper";
    private static final String DATABASE_NAME = "Dream.db";
    private static final int DATABASE_VERSION = 1;

    public DreamDbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION); }


    @Override
    public void onCreate(SQLiteDatabase db) {

        Log.d(LOG_TAG,"onCreate executing...");

        String SQL_CREATE_ENTRIES_DREAMS_TABLE = "CREATE TABLE " + DreamEntry.TABLE_NAME + " ("
                + DreamEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + DreamEntry.COLUMN_DREAM_TYPE + " INTEGER NOT NULL, "
                + DreamEntry.COLUMN_DREAM_FILE + " INTEGER NOT NULL DEFAULT 0, "
                + DreamEntry.COLUMN_DREAM_DATE + " TEXT, "
                + DreamEntry.COLUMN_DREAM_TITLE + " TEXT, "
                + DreamEntry.COLUMN_DREAM_TEXT + " TEXT, "
                + DreamEntry.COLUMN_DREAM_FAV + " INTEGER DEFAULT 0, "
                + DreamEntry.COLUMN_AUDIO_PATH + " TEXT );";

    db.execSQL(SQL_CREATE_ENTRIES_DREAMS_TABLE);
        Log.d(LOG_TAG,"Table entries created...");

        String SQL_CREATE_ENTRIES_REMINDERS_TABLE = "CREATE TABLE " + DreamContract.ReminderEntry.TABLE_NAME + " ("
                + DreamContract.ReminderEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + DreamContract.ReminderEntry.COLUMN_REMINDER_NAME + " TEXT, "
                + DreamContract.ReminderEntry.COLUMN_REMINDER_DAYS +" TEXT, "
                + DreamContract.ReminderEntry.COLUMN_REMINDER_INTERVAL + " INTEGER NOT NULL DEFAULT 3, "
                + DreamContract.ReminderEntry.COLUMN_REMINDER_STARTHOUR + " INTEGER NOT NULL, "
                + DreamContract.ReminderEntry.COLUMN_REMINDER_STARTMIN + " INTEGER NOT NULL, "
                + DreamContract.ReminderEntry.COLUMN_REMINDER_STOPHOUR + " INTEGER NOT NULL, "
                + DreamContract.ReminderEntry.COLUMN_REMINDER_STOPMIN + " INTEGER NOT NULL );";

        db.execSQL(SQL_CREATE_ENTRIES_REMINDERS_TABLE);
        Log.d(LOG_TAG,"Table reminders created...");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
