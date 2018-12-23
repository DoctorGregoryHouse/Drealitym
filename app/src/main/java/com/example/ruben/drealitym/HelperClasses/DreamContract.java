package com.example.ruben.drealitym.HelperClasses;

import android.provider.BaseColumns;

public class  DreamContract {

    private DreamContract(){}

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

}

