package com.example.ruben.drealitym.Data;

import android.app.Application;
import android.os.AsyncTask;

import java.util.List;

import androidx.lifecycle.LiveData;

public class DrealitymRepository {

    private DreamDao dreamDao;
    private RealityCheckDao realityCheckDao;

    private LiveData<List<DreamEntry>> allDreams;
    private LiveData<List<RealityCheckEntry>> allRealityChecks;

    //
    public DrealitymRepository(Application application){ //application is a subclass of context
        DrealitymDatabase database = DrealitymDatabase.getInstance(application);

        //The statements below call the abstract method of the Database class, usual its not possible to call abstract classes, but room creates the necessary code to call the class
        dreamDao = database.dreamDao();
        allDreams =  dreamDao.getAllDreams();

        //Statements for the RealityChecks
        realityCheckDao = database.realityCheckDao();
        allRealityChecks = realityCheckDao.getAllRealityChecks();
    }

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

    //AsyncTasks for the DreamDatabase operations
    //region
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

    public void insertRealityCheck(RealityCheckEntry realityCheckEntry){
        new InsertRealityCheckTask(realityCheckDao).execute(realityCheckEntry);
    }
    public void updateRealtiyCheck(RealityCheckEntry realityCheckEntry){
        new UpdateRealityCheckTask(realityCheckDao).execute(realityCheckEntry);
    }
    public void deleteRealityCheck(RealityCheckEntry realityCheckEntry){
        new InsertRealityCheckTask(realityCheckDao).execute(realityCheckEntry);
    }
    public void deleteAllRealityChecks(){
        new DeleteAllRealityChecksTask(realityCheckDao).execute();
    }
    public LiveData<List<RealityCheckEntry>> getAllRealityChecks(){return  allRealityChecks;}

    //AsyncTasks for the RealityCheck database operations
    //region
    private static class InsertRealityCheckTask extends AsyncTask<RealityCheckEntry, Void, Void>{
        private RealityCheckDao realityCheckDao;

        private InsertRealityCheckTask(RealityCheckDao realityCheckDao){
            this.realityCheckDao = realityCheckDao;}

        @Override
        protected Void doInBackground(RealityCheckEntry... realityCheckEntries) {
            realityCheckDao.insert(realityCheckEntries[0]);
            return null;
        }
    }

    private static class UpdateRealityCheckTask extends AsyncTask<RealityCheckEntry, Void, Void>{
        private RealityCheckDao realityCheckDao;

        public UpdateRealityCheckTask(RealityCheckDao realityCheckDao) {
            this.realityCheckDao = realityCheckDao;
        }
        @Override
        protected Void doInBackground(RealityCheckEntry... realityCheckEntries) {
            realityCheckDao.update(realityCheckEntries[0]);
            return null;
        }
    }

    private static class DeleteRealityCheckAsyncTask extends AsyncTask<RealityCheckEntry, Void, Void>{
        private RealityCheckDao realityCheckDao;

        public DeleteRealityCheckAsyncTask(RealityCheckDao realityCheckDao) {
            this.realityCheckDao = realityCheckDao;
        }

        @Override
        protected Void doInBackground(RealityCheckEntry... realityCheckEntries) {
            realityCheckDao.delete(realityCheckEntries[0]);
            return null;
        }
    }

    private static class DeleteAllRealityChecksTask extends AsyncTask<Void, Void, Void>{
        private RealityCheckDao realityCheckDao;

        public DeleteAllRealityChecksTask(RealityCheckDao realityCheckDao) {
            this.realityCheckDao = realityCheckDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            realityCheckDao.deleteAllRealityChecks();
            return null;
        }
    }
    //endregion

}
