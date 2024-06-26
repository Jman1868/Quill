package com.example.quill.database;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.quill.MainActivity;
import com.example.quill.database.entities.Liked;
import com.example.quill.database.entities.Quill;
import com.example.quill.database.entities.User;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Quill.class, User.class, Liked.class}, version = 1, exportSchema = false)
public abstract class QuillDatabase extends RoomDatabase {

    public static final String USER_TABLE = "usertable";
    private static final String DATABASE_NAME = "Quilldatabase";
    public static final String QUILL_TABLE = "quillTable";
    public static final String LIKED_TABLE = "likedTable";
    private static volatile QuillDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;

    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static QuillDatabase getDatabase(final Context context){

        if(INSTANCE == null){
            synchronized (QuillDatabase.class){
                if(INSTANCE == null){
                    INSTANCE= Room.databaseBuilder(
                             context.getApplicationContext(),
                             QuillDatabase.class,DATABASE_NAME)
                            .fallbackToDestructiveMigration()
                            .addCallback(addDefaultValues)
                            .build();

                }
            }
        }
        return INSTANCE;
    }

    private static final RoomDatabase.Callback addDefaultValues = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db){
            super.onCreate(db);
            Log.i(MainActivity.TAG,"DATABASE CREATED!");
            databaseWriteExecutor.execute(() -> {
                UserDAO dao = INSTANCE.userDAO();
                QuillDAO quillDAO = INSTANCE.quillDAO();
                LikedDAO likedDAO = INSTANCE.likedDAO();

                //Reset databases
                dao.deleteAll();
                quillDAO.deleteAll();
                likedDAO.deleteAll();

                // Create default users
                User admin = new User("admin2", "admin2");
                admin.setAdmin(true);
                dao.insert(admin);

                User testUser1 = new User("testuser1", "testuser1");
                dao.insert(testUser1);

                //Create Default Quill Item
                Quill defaultItem = new Quill("A generic Title", "Some generic text", "Health");
                quillDAO.insert(defaultItem);

                Quill defaultSecondItem = new Quill("Another generic Title", "Some more generic text", "Sports");
                quillDAO.insert(defaultSecondItem);

                //Create Default likedQuill Item
                Liked likedDefaultItem = new Liked("This is a liked item", "Some generic text", "Health", 1);
                likedDAO.insert(likedDefaultItem);

                Liked likedDefaultSecondItem = new Liked("Another liked Title", "Some more generic text", "Sports", 2);
                likedDAO.insert(likedDefaultSecondItem);

            });
        }
    };


    public abstract QuillDAO quillDAO();

    public abstract UserDAO userDAO();
    public abstract LikedDAO likedDAO();
}
