package com.example.quill;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.quill.database.QuillRepository;
import com.example.quill.database.entities.Liked;
import com.example.quill.database.entities.Quill;
import com.example.quill.database.entities.User;
import com.example.quill.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements QuillRecyclerViewInterface {
    static final String MAIN_ACTIVITY_USER_ID = "com.example.quill.MAIN_ACTIVITY_USER_ID";
    static final String SHARED_PREFERENCE_USERID_KEY = "com.example.quill.SHARED_PREFERENCE_USERID_KEY";
    static final String SAVED_INSTANCE_STATE_USERID_KEY = "com.example.quill.SAVED_INSTANCE_STATE_USERID_KEY";
    private static final int LOGGED_OUT = -1;
    ActivityMainBinding binding;
    private QuillRepository repository;

    private Liked_Item_Recycler_ViewAdapter adapter;

    public static final String TAG = "PROJECT02_QUILL";

    private int loggedInUserId = -1;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize database before asking user to log in/sign up
        repository = QuillRepository.getRepository(getApplication());

        loginUser(savedInstanceState);

        handleNav();

        if (loggedInUserId == -1) {
            Intent intent = LandingPageActivity.landingpageIntentFactory(getApplicationContext());
            startActivity(intent);
        }

        updateSharedPreference();

        // Set username to current user name
        LiveData<User> userObserver = repository.getUserByUserId(loggedInUserId);
        userObserver.observe(this, user -> {
            this.user=user;
            if (this.user != null) {
                binding.homePageUsernameTextView.setText(user.getUsername());
                fetchLikedItems(this.user.getId());
            }
        });


    }

    private void fetchLikedItems(int userId) {
        RecyclerView recyclerView = findViewById(R.id.quillRecyclerView);

        LiveData<List<Liked>> likedLiveData = repository.getLikedQuillsByUserId(userId);
        likedLiveData.observe(this, likedItems -> {
            if (likedItems != null) {
                adapter = new Liked_Item_Recycler_ViewAdapter(likedItems, this, userId, repository);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
            }
        });
    }

    private void loginUser(Bundle savedInstanceState) {
        // Check shared preference for logged in user
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(getString(R.string.preference_file_key),
                Context.MODE_PRIVATE);

        loggedInUserId = sharedPreferences.getInt(getString(R.string.preference_userId_key),LOGGED_OUT);

        if(loggedInUserId == LOGGED_OUT & savedInstanceState != null && savedInstanceState.containsKey(SAVED_INSTANCE_STATE_USERID_KEY)){
            loggedInUserId = savedInstanceState.getInt(SAVED_INSTANCE_STATE_USERID_KEY,LOGGED_OUT);
        }

        if(loggedInUserId == LOGGED_OUT){
            loggedInUserId = getIntent().getIntExtra(MAIN_ACTIVITY_USER_ID,LOGGED_OUT);
        }
        if(loggedInUserId == LOGGED_OUT){
            return;
        }

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putInt(SAVED_INSTANCE_STATE_USERID_KEY,loggedInUserId);
        updateSharedPreference();

    }

    private void updateSharedPreference(){
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(getString(R.string.preference_file_key),
                Context.MODE_PRIVATE);

        SharedPreferences.Editor sharedPrefEditor = sharedPreferences.edit();
        sharedPrefEditor.putInt(getString(R.string.preference_userId_key),loggedInUserId);
        sharedPrefEditor.apply();
    }

    static Intent mainActivityIntentFactory(Context context, int userId) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(MAIN_ACTIVITY_USER_ID, userId);
        return intent;
    }

    void handleNav() {

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.navigation_home);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.navigation_explore) {

                Intent intent = ExploreActivity.exploreIntentFactory(getApplicationContext());
                finish();
                startActivity(intent);
            }

            if (item.getItemId() == R.id.navigation_account) {

                Intent intent = AccountActivity.accountIntentFactory(getApplicationContext());
                finish();
                startActivity(intent);
            }
            return false;
        });
    }

    @Override
    public void onItemClick(int position) {
        Liked selectedLikedItem = adapter.likedList.get(position);
        Intent intent = new Intent(MainActivity.this, ItemViewActivity.class);
        intent.putExtra("QUILL_TITLE", selectedLikedItem.getTitle());
        intent.putExtra("QUILL_CONTENT", selectedLikedItem.getContent());
        intent.putExtra("QUILL_CATEGORY", selectedLikedItem.getCategory());
        intent.putExtra("QUILL_ISADMIN", user.isAdmin());

        startActivity(intent);
    }
}