package com.example.quill.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.quill.database.entities.Quill;

import java.util.List;

@Dao
public interface QuillDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Quill quill);

    @Query("SELECT * FROM " + QuillDatabase.QUILL_TABLE)
    List<Quill> getAllRecords();

    @Query("SELECT * FROM " + QuillDatabase.QUILL_TABLE + " WHERE userId = :loggedInUserId")
    List<Quill> getRecordsbyUserId(int loggedInUserId);
}
