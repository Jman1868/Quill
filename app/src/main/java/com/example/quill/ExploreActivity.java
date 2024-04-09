package com.example.quill;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.quill.databinding.ActivityExploreBinding;

public class ExploreActivity extends AppCompatActivity {


    ActivityExploreBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityExploreBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}