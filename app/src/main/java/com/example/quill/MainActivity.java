package com.example.quill;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.quill.database.QuillRepository;
import com.example.quill.database.entities.User;
import com.example.quill.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private static final String MAIN_ACTIVITY_USER_ID = "com.example.quill.MAIN_ACTIVITY_USER_ID";
    private static final int LOGGED_OUT = -1;
    ActivityMainBinding binding;
    private QuillRepository repository;

    public static final String TAG = "PROJECT02_QUILL";

    private int loggedInUserId = -1;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        loginUser();

        if (loggedInUserId == -1) {
            Intent intent = LoginActivity.loginIntentFactory(getApplicationContext());
            startActivity(intent);
        }

        // Create repository for databases
        repository = QuillRepository.getRepository(getApplication());

    }

    private void loginUser() {
        // Check shared preference for logged in user

        // Check intent for logged in user
        loggedInUserId = getIntent().getIntExtra(MAIN_ACTIVITY_USER_ID, LOGGED_OUT);
        if (loggedInUserId == LOGGED_OUT) {
            return;
        }


    }

    static Intent mainActivityIntentFactory(Context context, int userId) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(MAIN_ACTIVITY_USER_ID, userId);
        return intent;
    }
}