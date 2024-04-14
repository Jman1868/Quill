package com.example.quill;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.example.quill.database.QuillRepository;
import com.example.quill.database.entities.Quill;
import com.example.quill.database.entities.User;
import com.example.quill.databinding.ActivityExploreBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class ExploreActivity extends AppCompatActivity {


    ActivityExploreBinding binding;
    private Quill_Item_RecyclerViewAdapter adapter;

    //Todo: See if we can just use the logged out value from main instead
    private static final int LOGGED_OUT = -1;
    private int loggedInUserId = -1;
    private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityExploreBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        RecyclerView recyclerView = findViewById(R.id.quillRecyclerView);

        QuillRepository repository = QuillRepository.getRepository(getApplication());
        LiveData<List<Quill>> quillsLiveData = repository.getAllQuillsLiveData();

        quillsLiveData.observe(this, quills -> {
            adapter = new Quill_Item_RecyclerViewAdapter(quills);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        });




        //Button visibility
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(getString(R.string.preference_file_key),
                Context.MODE_PRIVATE);
        loggedInUserId = sharedPreferences.getInt(getString(R.string.preference_userId_key),LOGGED_OUT);

        LiveData<User> userObserver = repository.getUserByUserId(loggedInUserId);
        userObserver.observe(this, user -> {
            this.user=user;
            if (this.user != null) {
                if (user.isAdmin()) {
                    binding.addItemButton.setVisibility(View.VISIBLE);
                } else {
                    binding.addItemButton.setVisibility(View.INVISIBLE); // Reset if not admin
                }
            }
        });

        binding.addItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = CreateItemActivity.createItemActivityIntentFactory(getApplicationContext());
                startActivity(intent);
            }
        });

        handleNav();

    }

    static Intent exploreIntentFactory(Context context){
        return new Intent(context, ExploreActivity.class);
    }


    void handleNav() {

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.navigation_explore);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.navigation_home) {

                Intent intent = new Intent(ExploreActivity.this,MainActivity.class);
                startActivity(intent);
            }

            if (item.getItemId() == R.id.navigation_account) {

                Intent intent = AccountActivity.accountIntentFactory(getApplicationContext());
                startActivity(intent);
            }
            return false;
        });
    }
}