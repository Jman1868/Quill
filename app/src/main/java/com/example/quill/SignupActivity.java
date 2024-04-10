package com.example.quill;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import com.example.quill.database.QuillDatabase;
import com.example.quill.database.QuillRepository;
import com.example.quill.database.UserDAO;
import com.example.quill.database.entities.User;
import com.example.quill.databinding.ActivitySignupBinding;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SignupActivity extends AppCompatActivity {

    private ActivitySignupBinding binding;
    private QuillRepository repository;
    private static volatile QuillDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize database before asking user to log in/sign up
        repository = QuillRepository.getRepository(getApplication());

        binding.createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createUser();
            }
        });

        binding.bottomImageTextViewClickableLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = LoginActivity.loginIntentFactory(getApplicationContext());
                startActivity(intent);
            }
        });
    }

    private void createUser() {
        String username = binding.userNameSignupEditText.getText().toString();
        String password = binding.passwordSignupEditText.getText().toString();

        if (username.isEmpty()) {
            toastMaker("Username may not be blank");
            return;
        }
        if (password.isEmpty()) {
            toastMaker("Password may not be blank");
            return;
        }

        // TODO: Display message if username is taken and update username on MainActivity
        LiveData<User> userObserver = repository.getUserByUserName(username);
        userObserver.observe(this, user -> {
            if (user == null) {
                User newUser = new User(username, password);
                repository.insertUser(newUser);
                Intent intent = LoginActivity.loginIntentFactory(getApplicationContext());
                startActivity(intent);
                toastMaker("Account was successfully created");
            } else {
                toastMaker("User name is already taken");
            }
        });


    }

    private void toastMaker(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    static Intent signupIntentFactory (Context context) {
        return new Intent(context, SignupActivity.class);
    }
}