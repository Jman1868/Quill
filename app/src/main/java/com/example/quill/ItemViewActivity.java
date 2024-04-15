package com.example.quill;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.quill.databinding.ActivityExploreBinding;
import com.example.quill.databinding.ActivityItemViewBinding;

public class ItemViewActivity extends AppCompatActivity {


    ActivityItemViewBinding  binding;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityItemViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String quillTitle = getIntent().getStringExtra("QUILL_TITLE");
        String quillContent = getIntent().getStringExtra("QUILL_CONTENT");
        String quillCategory = getIntent().getStringExtra("QUILL_CATEGORY");
        String quillIsliked = getIntent().getStringExtra("QUILL_ISLIKED");

        binding.quillItemTitleTextView.setText(quillTitle);
        binding.quillContentTextView.setText(quillContent);

    }


}