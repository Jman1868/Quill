package com.example.quill.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.quill.database.entities.Liked;
import com.example.quill.database.entities.User;

import java.util.List;

@Dao
public interface LikedDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Liked liked);

    @Delete
    void delete(Liked liked);

    @Query("DELETE from " + QuillDatabase.LIKED_TABLE)
    void deleteAll();

    @Query("SELECT * from " + QuillDatabase.LIKED_TABLE + " WHERE title == :title" )
    LiveData<Liked> getLikedQuillByTitle(String title);

    @Query("SELECT * FROM " + QuillDatabase.LIKED_TABLE + " WHERE userId = :loggedInUserId")
    LiveData<List<Liked>> getLikedQuillsByUserId(int loggedInUserId);


}
