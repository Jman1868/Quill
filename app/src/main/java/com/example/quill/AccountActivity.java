package com.example.quill;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.quill.database.QuillRepository;
import com.example.quill.database.entities.User;
import com.example.quill.databinding.ActivityAccountBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AccountActivity extends AppCompatActivity {

    private QuillRepository repository;
    private User user;
    private static final int LOGGED_OUT = -1;

    private int loggedInUserId = -1;

   ActivityAccountBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = com.example.quill.databinding.ActivityAccountBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize database
        repository = QuillRepository.getRepository(getApplication());

        // Get logged in userId
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(getString(R.string.preference_file_key),
                Context.MODE_PRIVATE);
        loggedInUserId = sharedPreferences.getInt(getString(R.string.preference_userId_key),LOGGED_OUT);

        // Check to see if user is an admin to show delete user button
        LiveData<User> userObserver = repository.getUserByUserId(loggedInUserId);
        userObserver.observe(this, user -> {
            this.user=user;
            if (this.user != null && user.isAdmin()) {
                binding.accountPageDeleteUserButton.setVisibility(View.VISIBLE);
            }
        });

        // Log out button
        binding.accountPageLogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLogoutDialog();
            }
        });

        // Delete user button
        binding.accountPageDeleteUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteUserButton();
            }
        });


        binding.accountPageUserIdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(AccountActivity.this, "User Id is: "+ user.getId(), Toast.LENGTH_SHORT).show();
            }
        });

        binding.accountPageSettingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AccountActivity.this, SettingsActivity.class);
                intent.putExtra("CURRENT_USERID", user.getId());
                startActivity(intent);
            }
        });

        handleNav();
    }

    private void updateSharedPreference(){
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(getString(R.string.preference_file_key),
                Context.MODE_PRIVATE);

        SharedPreferences.Editor sharedPrefEditor = sharedPreferences.edit();
        sharedPrefEditor.putInt(getString(R.string.preference_userId_key),loggedInUserId);
        sharedPrefEditor.apply();
    }

    private void logout() {
        // Set loggedInUserId to logged out and update shared preferences
        loggedInUserId = LOGGED_OUT;
        updateSharedPreference();
        getIntent().putExtra(MainActivity.MAIN_ACTIVITY_USER_ID, LOGGED_OUT);

        startActivity(LandingPageActivity.landingpageIntentFactory(getApplicationContext()));
    }

    private void showLogoutDialog() {
        // Show dialog to check to see if user wants to log out
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(AccountActivity.this);
        final AlertDialog alertDialog = alertBuilder.create();
        alertBuilder.setMessage("Logout?");

        alertBuilder.setPositiveButton("Logout", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                logout();
            }
        });
        alertBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertBuilder.create().show();
    }

    private void deleteUserButton() {
        String deleteUsername = binding.accountPageSearchUsernameEditText.getText().toString();
        // If username edit text is empty display a message to enter user name
        if (deleteUsername.isEmpty()) {
            Toast.makeText(this, "Please enter a user name", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check database for username to delete and show the delete user dialog
        LiveData<User> userObserver = repository.getUserByUserName(deleteUsername);
        userObserver.observe(this, user -> {
            if (user != null) {
                showDeleteUserDialog(deleteUsername);

                // If user exists, we're done here, so remove the observer
                userObserver.removeObservers(this);
            } else {
                Toast.makeText(this, String.format("User %s does not exist", deleteUsername), Toast.LENGTH_SHORT).show();
                userObserver.removeObservers(this);
            }
        });
    }

    private void deleteUser() {
        // Check database for username and delete user
        String deleteUsername = binding.accountPageSearchUsernameEditText.getText().toString();
        LiveData<User> userObserver = repository.getUserByUserName(deleteUsername);
        userObserver.observe(this, user -> {
            if (user != null) {
                repository.deleteUser(user);
            }

        });
    }

    private void showDeleteUserDialog(String deleteUsername) {
        // Show dialog to check to see if user wants delete user
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(AccountActivity.this);
        final AlertDialog alertDialog = alertBuilder.create();
        alertBuilder.setMessage(String.format("Delete user %s?", deleteUsername));

        alertBuilder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(AccountActivity.this, String.format("Successfully deleted user %s", deleteUsername), Toast.LENGTH_SHORT).show();
                deleteUser();

            }
        });
        alertBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertBuilder.create().show();
    }

    static Intent accountIntentFactory(Context context){
        return new Intent(context, AccountActivity.class);
    }


    void handleNav() {

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.navigation_account);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.navigation_home) {

                Intent intent = new Intent(AccountActivity.this,MainActivity.class);
                startActivity(intent);
            }

            if (item.getItemId() == R.id.navigation_explore) {

                Intent intent = ExploreActivity.exploreIntentFactory(getApplicationContext());
                startActivity(intent);
            }
            return false;
        });
    }
}