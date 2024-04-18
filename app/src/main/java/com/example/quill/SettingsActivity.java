package com.example.quill;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import com.example.quill.database.QuillRepository;
import com.example.quill.database.entities.User;
import com.example.quill.databinding.ActivitySettingsBinding;


public class SettingsActivity extends AppCompatActivity {

    ActivitySettingsBinding binding;
    private QuillRepository repository;
    int currentUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = QuillRepository.getRepository(getApplication());

        currentUserId = getIntent().getIntExtra("CURRENT_USERID", -1);

        binding.settingsPageCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.settingsPageConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editUser();
            }
        });
    }

    private void editUser() {
        String newUsername = binding.userNameSettingsEditText.getText().toString();
        String newPassword = binding.passwordSettingsEditText.getText().toString();

        if (!newUsername.isEmpty()) {
            changeUsername(newUsername);
        }

        if (!newPassword.isEmpty()) {
            changePassword(newPassword);
        }

        Intent intent = new Intent(SettingsActivity.this, AccountActivity.class);
        startActivity(intent);
    }

    private void changeUsername(String newUsername) {
        LiveData<User> userObserver = repository.getUserByUserName(newUsername);
        userObserver.observe(this, user -> {
            if (user == null) {
                LiveData<User> userObserver2 = repository.getUserByUserId(currentUserId);
                userObserver2.observe(this, user2 -> {
                    user2.setUsername(newUsername);
                    repository.insertUser(user2);
                    Toast.makeText(this, "Username was successfully changed", Toast.LENGTH_SHORT).show();
                    userObserver2.removeObservers(this);
                });
                userObserver.removeObservers(this);
            } else {
                userObserver.removeObservers(this);
                Toast.makeText(this, "User name is already taken", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void changePassword(String newPassword) {
        LiveData<User> userObserver = repository.getUserByUserId(currentUserId);
        userObserver.observe(this, user -> {
            user.setPassword(newPassword);
            repository.insertUser(user);
            Toast.makeText(this, "Password was successfully changed", Toast.LENGTH_SHORT).show();
            userObserver.removeObservers(this);
        });
    }

    static Intent settingsActivityIntentFactory(Context context) {
        return new Intent(context, SettingsActivity.class);
    }
}