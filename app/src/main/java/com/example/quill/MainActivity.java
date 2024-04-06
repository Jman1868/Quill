package com.example.quill;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.quill.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;

    public static final String TAG = "PROJECT02_QUILL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}