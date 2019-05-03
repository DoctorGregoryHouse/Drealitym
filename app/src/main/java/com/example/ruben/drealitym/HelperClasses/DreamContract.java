package com.example.ruben.drealitym.HelperClasses;

import android.provider.BaseColumns;

public class  DreamContract {

    private DreamContract(){}

    //Dream Diary table
    public static final class DreamEntry implements BaseColumns {

        public final static String TABLE_NAME = "Dreams";
        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_DREAM_TYPE = "type";
        public final static String COLUMN_DREAM_FILE ="file_bool";
        public final static String COLUMN_DREAM_DATE = "date";
        public final static String COLUMN_DREAM_TITLE = "title";
        public final static String COLUMN_DREAM_TEXT = "text";
        public final static String COLUMN_AUDIO_PATH = "audio_path";
        public final static String COLUMN_DREAM_FAV = "favourite";

    }

    // Dream Reminders table
    public static final class ReminderEntry implements  BaseColumns {

        public final static String TABLE_NAME = "Reminders";
        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_REMINDER_NAME = "name";
        public final static String COLUMN_REMINDER_DAYS = "days";
        public final static String COLUMN_REMINDER_INTERVAL = "interval";
        public final static String COLUMN_REMINDER_STARTHOUR = "start_hour";
        public final static String COLUMN_REMINDER_STARTMIN = "start_min";
        public final static String COLUMN_REMINDER_STOPHOUR = "stop_hour";
        public final static String COLUMN_REMINDER_STOPMIN ="stop_min";

    }


}

