package com.example.ruben.drealitym.Data;


import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface DreamDao {

    @Insert
    void insert (DreamEntry dreamEntry);

    @Update
    void update (DreamEntry dreamEntry);

    @Delete
    void delete (DreamEntry dreamEntry);

    @Query("DELETE  FROM dream_table")
    void deleteAllDreams();

    //from here the whole data is accessed, specifies the criterion by which the list is ordered (@param date)
    @Query("SELECT * FROM dream_table ORDER BY date DESC")
    LiveData<List<DreamEntry>> getAllDreams(); // LiveData observes this object, as soon as there is any change in the note Table, this value will be updated and our Activity gets notified
}
