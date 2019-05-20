package com.example.ruben.drealitym.Data;

import android.app.Application;
import android.os.AsyncTask;

import java.util.List;

import androidx.lifecycle.LiveData;

public class DrealitymRepository {

    private DreamDao dreamDao;
    private ReminderDao reminderDao;

    private LiveData<List<DreamEntry>> allDreams;


    private LiveData<List<ReminderEntry>> allReminders;
    //
    public DrealitymRepository(Application application){ //application is a subclass of context
        DrealitymDatabase database = DrealitymDatabase.getInstance(application);

        //The statements below call the abstract method of the Database class, usual its not possible to call abstract classes, but room creates the necessary code to call the class
        dreamDao = database.dreamDao();
        //reminderDao = database.reminderDao();

        allDreams =  dreamDao.getAllDreams();
        //reminderDao.getAllReminders();


    }

    //databaseOperations Dream table
    //region
    public void insertDream(DreamEntry dreamEntry){
        new InsertDreamAsyncTask(dreamDao).execute(dreamEntry);
    }
    public void updateDream(DreamEntry dreamEntry){
        new UpdateDreamAsyncTask(dreamDao).execute(dreamEntry);

    }
    public void deleteDream(DreamEntry dreamEntry){
        new DeleteDreamAsyncTask(dreamDao).execute(dreamEntry);
    }

    public void deleteAllDreams(){
        new DeleteAllDreamsAsyncTask(dreamDao).execute();
    }

    public LiveData<List<DreamEntry>> getAllDreams(){
        return allDreams;
    }

    private static class InsertDreamAsyncTask extends AsyncTask<DreamEntry, Void, Void>{
        private DreamDao dreamDao;

        private InsertDreamAsyncTask(DreamDao dreamDao){
            this.dreamDao = dreamDao;
        }

        @Override
        protected Void doInBackground(DreamEntry... dreamEntries) {
            dreamDao.insert(dreamEntries[0]);
            return null;
        }
    }

    private static class UpdateDreamAsyncTask extends AsyncTask<DreamEntry, Void, Void>{
        private DreamDao dreamDao;

        private UpdateDreamAsyncTask(DreamDao dreamDao){
            this.dreamDao = dreamDao;
        }

        @Override
        protected Void doInBackground(DreamEntry... dreamEntries) {
            dreamDao.update(dreamEntries[0]);
            return null;
        }
    }


    private static class DeleteDreamAsyncTask extends AsyncTask<DreamEntry, Void, Void>{
        private DreamDao dreamDao;

        private DeleteDreamAsyncTask(DreamDao dreamDao){
            this.dreamDao = dreamDao;
        }

        @Override
        protected Void doInBackground(DreamEntry... dreamEntries) {
            dreamDao.delete(dreamEntries[0]);
            return null;
        }
    }


    private static class DeleteAllDreamsAsyncTask extends AsyncTask<Void, Void, Void>{
        private DreamDao dreamDao;

        private DeleteAllDreamsAsyncTask(DreamDao dreamDao){
            this.dreamDao = dreamDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            dreamDao.deleteAllDreams();
            return null;
        }
    }
    //endregion


    //database operations Reminder table
    //region
    public void insertReminder(ReminderEntry reminderEntry){

    }
    public void updateReminder(ReminderEntry reminderEntry){

    }
    public void deleteReminder(ReminderEntry reminderEntry){

    }
    public void deleteAllReminders(){

    }
    public LiveData<List<ReminderEntry>> getAllReminders() {
        return allReminders;
    }

    private static class InsertReminderAsyncTask extends AsyncTask<ReminderEntry, Void, Void>{
        private ReminderDao reminderDao;

        private InsertReminderAsyncTask(ReminderDao reminderDao){
            this.reminderDao = reminderDao;
        }

        @Override
        protected Void doInBackground(ReminderEntry... reminderEntries) {
            reminderDao.insert(reminderEntries[0]);
            return null;
        }
    }

    private static class UpdateReminderAsyncTask extends AsyncTask<ReminderEntry, Void, Void>{
        private ReminderDao reminderDao;

        private UpdateReminderAsyncTask(ReminderDao reminderDao){
            this.reminderDao = reminderDao;
        }

        @Override
        protected Void doInBackground(ReminderEntry... reminderEntries) {
            reminderDao.update(reminderEntries[0]);
            return null;
        }
    }

    private static class DeleteReminderAsyncTask extends AsyncTask<ReminderEntry, Void, Void>{
        private ReminderDao reminderDao;

        private DeleteReminderAsyncTask(ReminderDao reminderDao){
            this.reminderDao = reminderDao;
        }

        @Override
        protected Void doInBackground(ReminderEntry... reminderEntries) {
            reminderDao.delete(reminderEntries[0]);
            return null;
        }
    }

    private static class DeleteAllReminderAsyncTask extends AsyncTask<Void, Void, Void>{
        private ReminderDao reminderDao;

        private DeleteAllReminderAsyncTask(ReminderDao reminderDao){
            this.reminderDao = reminderDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            reminderDao.deleteAllReminders();
            return null;
        }
    }
    //endregion


}
