package com.example.ruben.drealitym.Data;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.RawQuery;
import androidx.room.Update;

@Dao
public interface ReminderDao {

    @Insert
    void insert (ReminderEntry reminderEntry);

    @Update
    void update (ReminderEntry reminderEntry);

    @Delete
    void delete (ReminderEntry reminderEntry);

    @Query("DELETE FROM reminder_table")
    void deleteAllReminders();

    @Query("SELECT * FROM reminder_table")
    LiveData<List<ReminderEntry>> getAllReminders();
}
