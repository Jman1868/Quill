package com.example.quill.database;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.quill.MainActivity;
import com.example.quill.database.entities.Quill;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Quill.class}, version = 1, exportSchema = false)
public abstract class QuillDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "Quill_database";
    public static final String QUILL_TABLE = "quillTable";

    private static volatile QuillDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;

    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static QuillDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (QuillDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            QuillDatabase.class,
                                    DATABASE_NAME
                            )
                            .fallbackToDestructiveMigration()
                            .addCallback(addDefaultValues)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static final RoomDatabase.Callback addDefaultValues = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            Log.i(MainActivity.TAG, "Database created!");
            // TODO: add databaseWriteExecutor.execute(() -> {...}
        }
    };

    public abstract QuillDAO quillDAO();
}
