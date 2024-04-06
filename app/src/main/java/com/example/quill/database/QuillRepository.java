package com.example.quill.database;

import android.app.Application;
import android.util.Log;

import com.example.quill.MainActivity;
import com.example.quill.database.entities.Quill;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class QuillRepository {
    private QuillDAO quillDAO;
    private ArrayList<Quill> allQuills;

    private static QuillRepository repository;

    private QuillRepository (Application application) {
        QuillDatabase db = QuillDatabase.getDatabase(application);
        this.quillDAO = db.quillDAO();
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
        QuillDatabase.databaseWriteExecutor.execute(() -> {
            quillDAO.insert(quill);
        });
    }


}
