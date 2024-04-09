package com.example.quill;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.quill.databinding.ActivityExploreBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ExploreActivity extends AppCompatActivity {


    ActivityExploreBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityExploreBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

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