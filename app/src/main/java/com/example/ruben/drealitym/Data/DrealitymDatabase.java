package com.example.ruben.drealitym.Data;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {DreamEntry.class, RealityCheckEntry.class /*komma here ? */}, version = 5, exportSchema = false)
public abstract class DrealitymDatabase extends RoomDatabase {

    //DrealitymDatabase;
    //region
    private static DrealitymDatabase instance;

    public abstract DreamDao dreamDao(); // we can use this abstract method to access the DataBase operation methods created in the DreamDao interface
    public abstract RealityCheckDao realityCheckDao();

    //synchronized means that only one thread at a time can access this method
    static synchronized DrealitymDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    DrealitymDatabase.class, "dream_database")
                    .fallbackToDestructiveMigration() //at a migration, this method destroys the whole db and recreates it.
                    .addCallback(callback)// adds the callback below
                    .build();
        }

        // returns the built instance if there is not already a entity

        return instance;
    }

    //Callback gets called when the db is first created
    private static RoomDatabase.Callback callback  = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);

        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void>{
        private DreamDao mDreamDao;
        private RealityCheckDao mRealityCheckDao;

        PopulateDbAsyncTask(DrealitymDatabase db){
            mDreamDao = db.dreamDao();
            mRealityCheckDao = db.realityCheckDao();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            mDreamDao.insert(new DreamEntry(1,1,"100","DummyDreamTitle","DummyDreamText", 1, "//dummy/path/audio"));

            //TODO: No need for the first row in the db, crate enum or HashMap or StringArray ( ? ) for the interval to preset certain values
            //TODO: At the applications first start, this entry should be created for every user instead of this dummy data
            mRealityCheckDao.insert(new RealityCheckEntry(1,30,18,45,2,2));
            mRealityCheckDao.insert(new RealityCheckEntry(2,30,18,45,2,2));
            mRealityCheckDao.insert(new RealityCheckEntry(3,30,18,45,2,2));
            mRealityCheckDao.insert(new RealityCheckEntry(4,30,18,45,2,2));
            mRealityCheckDao.insert(new RealityCheckEntry(5,30,18,45,2,2));
            mRealityCheckDao.insert(new RealityCheckEntry(6,30,22,0,8,2));
            mRealityCheckDao.insert(new RealityCheckEntry(7,30,22,0,4,2));

            return null;
        }
    }




}
