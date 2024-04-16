package com.example.quill;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import com.example.quill.database.QuillRepository;
import com.example.quill.database.entities.Quill;
import com.example.quill.databinding.ActivityEditItemBinding;

import java.util.Locale;

public class EditItemActivity extends AppCompatActivity {

    ActivityEditItemBinding binding;
    private QuillRepository repository;
    String quillTitle;
    String originalQuillTitle;
    String quillContent;
    String originalQuillContent;
    String quillCategory = "";
    String originalQuillCategory;
    boolean quillIsLiked;
    boolean isAdmin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditItemBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = QuillRepository.getRepository(getApplication());

        originalQuillTitle = getIntent().getStringExtra("QUILL_TITLE");
        originalQuillContent = getIntent().getStringExtra("QUILL_CONTENT");
        originalQuillCategory = getIntent().getStringExtra("QUILL_CATEGORY");
        quillIsLiked = getIntent().getBooleanExtra("QUILL_ISLIKED", false);
        isAdmin = getIntent().getBooleanExtra("QUILL_ISADMIN", false);

        // Set the title, content, and category
        binding.editItemPageTitleEditText.setText(originalQuillTitle);
        binding.editItemPageContentEditText.setText(originalQuillContent);
        setCategory(originalQuillCategory);

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

        binding.editItemPageCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditItemActivity.this, ItemViewActivity.class);
                intent.putExtra("QUILL_TITLE", originalQuillTitle);
                intent.putExtra("QUILL_CONTENT", originalQuillContent);
                intent.putExtra("QUILL_CATEGORY", originalQuillCategory);
                intent.putExtra("QUILL_ISLIKED", quillIsLiked);
                intent.putExtra("QUILL_ISADMIN", isAdmin);

                startActivity(intent);
            }
        });
    }

    private void getInformationFromDisplay() {
        quillTitle = binding.editItemPageTitleEditText.getText().toString();
        quillContent = binding.editItemPageContentEditText.getText().toString();
    }

    private void insertQuillRecord() {
        if (quillTitle.isEmpty()) {
            Toast.makeText(this, "Title is required", Toast.LENGTH_SHORT).show();
            return;
        }

        if (quillContent.isEmpty()) {
            Toast.makeText(this, "Content is required", Toast.LENGTH_SHORT).show();
            return;
        }

        if (quillCategory.isEmpty()) {
            quillCategory = originalQuillCategory;
        }

        LiveData<Quill> userObserver = repository.getQuillByTitle(originalQuillTitle);
        userObserver.observe(this, quill -> {
            if (quill != null) {
                quill.setTitle(quillTitle);
                quill.setContent(quillContent);
                quill.setCategory(quillCategory);
                repository.insertQuill(quill);
            }
        });

        Toast.makeText(this, "Item was successfully edited", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(EditItemActivity.this, ItemViewActivity.class);
        intent.putExtra("QUILL_TITLE", quillTitle);
        intent.putExtra("QUILL_CONTENT", quillContent);
        intent.putExtra("QUILL_CATEGORY", quillCategory);
        intent.putExtra("QUILL_ISLIKED", quillIsLiked);
        intent.putExtra("QUILL_ISADMIN", isAdmin);

        startActivity(intent);
    }

    private void setCategory(String originalQuillCategory) {
        switch (originalQuillCategory.toLowerCase(Locale.ROOT)) {
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