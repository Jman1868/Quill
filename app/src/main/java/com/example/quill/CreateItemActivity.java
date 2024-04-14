package com.example.quill;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quill.database.QuillRepository;
import com.example.quill.database.entities.Quill;
import com.example.quill.databinding.ActivityCreateItemBinding;

public class CreateItemActivity extends AppCompatActivity {
    ActivityCreateItemBinding binding;
    private QuillRepository repository;
    String mTitle = "";
    String mContent = "";
    String mCategory = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateItemBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = QuillRepository.getRepository(getApplication());

        // Get information from display and insert into repository
        binding.createItemPageConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getInformationFromDisplay();
                insertQuillRecord();
            }
        });

        // Highlight health button if it is selected and set category to health
        binding.createItemHealthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCategory = "Health";
                binding.createItemPageHealthSelected.setVisibility(View.VISIBLE);
                binding.createItemPageSportsSelected.setVisibility(View.INVISIBLE);
                binding.createItemPageScienceSelected.setVisibility(View.INVISIBLE);
            }
        });

        // Highlight sports button if it is selected and set category to Sports
        binding.createItemSportsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCategory = "Sports";
                binding.createItemPageSportsSelected.setVisibility(View.VISIBLE);
                binding.createItemPageHealthSelected.setVisibility(View.INVISIBLE);
                binding.createItemPageScienceSelected.setVisibility(View.INVISIBLE);
            }
        });

        // Highlight science button if it is selected and set category to Science
        binding.createItemScienceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCategory = "Science";
                binding.createItemPageScienceSelected.setVisibility(View.VISIBLE);
                binding.createItemPageSportsSelected.setVisibility(View.INVISIBLE);
                binding.createItemPageHealthSelected.setVisibility(View.INVISIBLE);
            }
        });

        binding.createItemPageCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = ExploreActivity.exploreIntentFactory(getApplicationContext());
                startActivity(intent);
            }
        });

    }

    private void getInformationFromDisplay() {
        mTitle = binding.createItemPageTitleEditText.getText().toString();
        mContent = binding.createItemPageContentEditText.getText().toString();

    }

    private void insertQuillRecord() {
        if (mTitle.isEmpty()) {
            Toast.makeText(this, "Title is required", Toast.LENGTH_SHORT).show();
            return;
        }

        if (mContent.isEmpty()) {
            Toast.makeText(this, "Content is required", Toast.LENGTH_SHORT).show();
            return;
        }
        
        Quill quill = new Quill(mTitle, mContent, mCategory);
        repository.insertQuill(quill);
        Toast.makeText(this, "Item was successfully created", Toast.LENGTH_SHORT).show();
        Intent intent = ExploreActivity.exploreIntentFactory(getApplicationContext());
        startActivity(intent);
    }

    static Intent createItemActivityIntentFactory(Context context) {
        return new Intent(context, CreateItemActivity.class);
    }
}