package com.example.quill;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import com.example.quill.databinding.ActivityEditItemBinding;

public class EditItemActivity extends AppCompatActivity {

    ActivityEditItemBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditItemBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}