package com.example.quill;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.quill.database.QuillRepository;
import com.example.quill.databinding.ActivityCreateItemBinding;

public class CreateItem extends AppCompatActivity {
    ActivityCreateItemBinding binding;
    private QuillRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateItemBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = QuillRepository.getRepository(getApplication());

    }
}