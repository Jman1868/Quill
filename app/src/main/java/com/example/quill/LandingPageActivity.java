package com.example.quill;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.quill.databinding.ActivityLandingPageBinding;
import com.example.quill.databinding.ActivityLoginBinding;

public class LandingPageActivity extends AppCompatActivity {

    private ActivityLandingPageBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLandingPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}