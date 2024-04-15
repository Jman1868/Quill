package com.example.quill.database;

import androidx.lifecycle.LiveData;
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

    @Query("DELETE from " + QuillDatabase.QUILL_TABLE)
    void deleteAll();

    @Query("SELECT * FROM " + QuillDatabase.QUILL_TABLE)
    List<Quill> getAllRecords();

    @Query("SELECT * FROM " + QuillDatabase.QUILL_TABLE)
    LiveData<List<Quill>> getAllQuill();

//    @Query("SELECT * FROM " + QuillDatabase.QUILL_TABLE + " WHERE userId = :loggedInUserId")
//    List<Quill> getRecordsbyUserId(int loggedInUserId);
}
