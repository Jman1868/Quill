package com.example.quill.database;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.quill.MainActivity;
import com.example.quill.database.entities.Liked;
import com.example.quill.database.entities.Quill;
import com.example.quill.database.entities.User;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class QuillRepository {
    private final QuillDAO quillDAO;
    private final UserDAO userDAO;
    private final LikedDAO likedDAO;
    private ArrayList<Quill> allQuills;

    private static QuillRepository repository;

    private QuillRepository (Application application) {
        QuillDatabase db = QuillDatabase.getDatabase(application);
        this.quillDAO = db.quillDAO();
        this.userDAO = db.userDAO();
        this.likedDAO = db.likedDAO();
        this.allQuills = (ArrayList<Quill>) this.quillDAO.getAllRecords();
    }

    public static QuillRepository getRepository(Application application) {
        if (repository != null) {
            return repository;
        }
        Future<QuillRepository> future = QuillDatabase.databaseWriteExecutor.submit(
                new Callable<QuillRepository>() {
                    @Override
                    public QuillRepository call() throws Exception {
                        return new QuillRepository(application);
                    }
                }
        );
        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            Log.i(MainActivity.TAG, "Problem getting QuillRepository, thread error.");
        }
        return null;
    }

    public LiveData<List<Quill>> getAllQuillsLiveData() {
        return quillDAO.getAllQuill();
    }


    public ArrayList<Quill> getAllQuills() {
        Future<ArrayList<Quill>> future = QuillDatabase.databaseWriteExecutor.submit(
                new Callable<ArrayList<Quill>>() {
                    @Override
                    public ArrayList<Quill> call() throws Exception {
                        return (ArrayList<Quill>) quillDAO.getAllRecords();
                    }
                }
        );
        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            Log.i(MainActivity.TAG, "Problem when getting all Quills in the repository");
        }
        return null;
    }

    public void insertQuill(Quill quill) {
        QuillDatabase.databaseWriteExecutor.execute(() ->
        {
            quillDAO.insert(quill);
        });
    }

    public void deleteQuill(Quill quill) {
        QuillDatabase.databaseWriteExecutor.execute(() ->
        {
            quillDAO.delete(quill);
        });
    }

    public LiveData<Quill> getQuillByTitle(String title) {
        return quillDAO.getQuillByTitle(title);
    }

    public void insertUser(User... user) {
        QuillDatabase.databaseWriteExecutor.execute(() ->
        {
            userDAO.insert(user);
        });
    }

    public void deleteUser(User user) {
        QuillDatabase.databaseWriteExecutor.execute(() ->
        {
            userDAO.delete(user);
        });
    }

    public LiveData<User> getUserByUserName(String username) {
        return userDAO.getUserByUserName(username);
    }

    public LiveData<User> getUserByUserId(int userId) {
        return userDAO.getUserByUserId(userId);
    }

    public void insertLikedQuill(Liked liked) {
        QuillDatabase.databaseWriteExecutor.execute(() ->
        {
            // Check if the liked item already exists
            Liked existingLiked = likedDAO.getLikedQuillByTitleAndUserId(liked.getTitle(), liked.getUserId());

            if (existingLiked == null) { // If item doesn't exist, insert it
                likedDAO.insert(liked);
            } else {
                Log.i(MainActivity.TAG, "Liked item already exists.");
            }
        });
    }

    public void deleteLikedQuill(Liked liked) {
        QuillDatabase.databaseWriteExecutor.execute(() ->
        {
            likedDAO.delete(liked);
        });
    }

    public LiveData<Liked> getLikedQuillByTitle(String title) {
        return likedDAO.getLikedQuillByTitle(title);
    }

    public LiveData<List<Liked>> getLikedQuillsByUserId(int loggedInUserId) {
        return likedDAO.getLikedQuillsByUserId(loggedInUserId);
    }

}
