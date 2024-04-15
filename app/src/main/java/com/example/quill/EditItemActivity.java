package com.example.quill;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import com.example.quill.databinding.ActivityEditItemBinding;

import java.util.Locale;

public class EditItemActivity extends AppCompatActivity {

    ActivityEditItemBinding binding;
    String quillTitle;
    String quillContent;
    String quillCategory;
    boolean quillIsLiked;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditItemBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        quillTitle = getIntent().getStringExtra("QUILL_TITLE");
        quillContent = getIntent().getStringExtra("QUILL_CONTENT");
        quillCategory = getIntent().getStringExtra("QUILL_CATEGORY");
        quillIsLiked = getIntent().getBooleanExtra("QUILL_ISLIKED", false);

        // Set the title, content, and category
        binding.editItemPageTitleEditText.setText(quillTitle);
        binding.editItemPageContentEditText.setText(quillContent);
        setCategory(quillCategory);

        // Get information from display and insert into repository
        binding.editItemPageConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getInformationFromDisplay();
                insertQuillRecord();
            }
        });

        // Highlight health button if it is selected and set category to health
        binding.editItemHealthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quillCategory = "Health";
                binding.editItemPageHealthSelected.setVisibility(View.VISIBLE);
                binding.editItemPageSportsSelected.setVisibility(View.INVISIBLE);
                binding.editItemPageScienceSelected.setVisibility(View.INVISIBLE);
            }
        });

        // Highlight sports button if it is selected and set category to Sports
        binding.editItemSportsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quillCategory = "Sports";
                binding.editItemPageSportsSelected.setVisibility(View.VISIBLE);
                binding.editItemPageHealthSelected.setVisibility(View.INVISIBLE);
                binding.editItemPageScienceSelected.setVisibility(View.INVISIBLE);
            }
        });

        // Highlight science button if it is selected and set category to Science
        binding.editItemScienceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quillCategory = "Science";
                binding.editItemPageScienceSelected.setVisibility(View.VISIBLE);
                binding.editItemPageSportsSelected.setVisibility(View.INVISIBLE);
                binding.editItemPageHealthSelected.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void setCategory(String quillCategory) {
        switch (quillCategory.toLowerCase(Locale.ROOT)) {
            case "health":
                binding.editItemPageHealthSelected.setVisibility(View.VISIBLE);
                binding.editItemPageSportsSelected.setVisibility(View.INVISIBLE);
                binding.editItemPageScienceSelected.setVisibility(View.INVISIBLE);
                break;
            case "sports":
                binding.editItemPageSportsSelected.setVisibility(View.VISIBLE);
                binding.editItemPageHealthSelected.setVisibility(View.INVISIBLE);
                binding.editItemPageScienceSelected.setVisibility(View.INVISIBLE);
                break;
            case "science":
                binding.editItemPageScienceSelected.setVisibility(View.VISIBLE);
                binding.editItemPageHealthSelected.setVisibility(View.INVISIBLE);
                binding.editItemPageSportsSelected.setVisibility(View.INVISIBLE);
                break;
        }
    }
}