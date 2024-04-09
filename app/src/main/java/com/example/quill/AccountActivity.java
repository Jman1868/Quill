package com.example.quill;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.quill.databinding.ActivityAccountBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AccountActivity extends AppCompatActivity {

   ActivityAccountBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = com.example.quill.databinding.ActivityAccountBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        handleNav();
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