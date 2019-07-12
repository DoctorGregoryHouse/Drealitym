package com.example.ruben.drealitym.Data;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface RealityCheckDao {

    @Insert
    void insert (RealityCheckEntry realityCheckEntry);

    @Update
    void update (RealityCheckEntry realityCheckEntry);

    @Delete
    void delete (RealityCheckEntry realityCheckEntry);

    @Query("DELETE FROM reality_check_table")
    void deleteAllRealityChecks();

    @Query("SELECT * FROM reality_check_table")
    LiveData<List<RealityCheckEntry>> getAllRealityChecks();
}
