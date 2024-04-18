package com.example.quill;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


import com.example.quill.database.QuillRepository;
import com.example.quill.database.entities.Quill;
import com.example.quill.databinding.ActivityItemViewBinding;

public class ItemViewActivity extends AppCompatActivity {


    ActivityItemViewBinding  binding;
    private QuillRepository repository;
    String quillTitle;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityItemViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = QuillRepository.getRepository(getApplication());

        quillTitle = getIntent().getStringExtra("QUILL_TITLE");
        String quillContent = getIntent().getStringExtra("QUILL_CONTENT");
        String quillCategory = getIntent().getStringExtra("QUILL_CATEGORY");
        boolean quillIsLiked = getIntent().getBooleanExtra("QUILL_ISLIKED",false);
        boolean isAdmin = getIntent().getBooleanExtra("QUILL_ISADMIN",false);

        binding.quillItemTitleTextView.setText(quillTitle);
        binding.quillContentTextView.setText(quillContent);


        displayBadges(quillCategory);
        hideAdminButtons(isAdmin);

        binding.backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.quillEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ItemViewActivity.this, EditItemActivity.class);
                intent.putExtra("QUILL_TITLE", quillTitle);
                intent.putExtra("QUILL_CONTENT", quillContent);
                intent.putExtra("QUILL_CATEGORY", quillCategory);
                intent.putExtra("QUILL_ISLIKED", quillIsLiked);
                intent.putExtra("QUILL_ISADMIN", isAdmin);

                startActivity(intent);
            }
        });

        binding.quillTrashButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteQuillDialog(quillTitle);
            }
        });

    }

    // Search repository for quill using title (Ideally want to use quill id)
    private void deleteQuill() {
        LiveData<Quill> userObserver = repository.getQuillByTitle(quillTitle);
        userObserver.observe(this, quill -> {
            if (quill != null) {
                repository.deleteQuill(quill);
                userObserver.removeObservers(this);
                Intent intent = ExploreActivity.exploreIntentFactory(getApplicationContext());
                startActivity(intent);
            }
        });
    }

    // Show the alert to confirm if admins wants to delete quill
    private void showDeleteQuillDialog(String quillTitle) {
        // Show dialog to check to see if user wants delete user
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(ItemViewActivity.this);
        final AlertDialog alertDialog = alertBuilder.create();
        alertBuilder.setMessage(String.format("Delete quill: %s", quillTitle));

        alertBuilder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(ItemViewActivity.this, String.format("Successfully deleted quill: %s", quillTitle), Toast.LENGTH_SHORT).show();
                deleteQuill();

            }
        });
        alertBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertBuilder.create().show();
    }

    private void displayBadges(String quillCategory) {
        if ("Health".equalsIgnoreCase(quillCategory)) {
            binding.quillItemBadgeImgView.setImageResource(R.drawable.healthbadge);
        } else if ("Sports".equalsIgnoreCase(quillCategory)) {
            binding.quillItemBadgeImgView.setImageResource(R.drawable.sportsbadge);
        } else if ("Science".equalsIgnoreCase(quillCategory)) {
            binding.quillItemBadgeImgView.setImageResource(R.drawable.sciencebadge);
        }
    }

    void hideAdminButtons(boolean isAdmin){
        if (isAdmin) {
            binding.quillEditButton.setVisibility(View.VISIBLE);
            binding.quillTrashButton.setVisibility(View.VISIBLE);
        } else {
            binding.quillEditButton.setVisibility(View.INVISIBLE);
            binding.quillTrashButton.setVisibility(View.INVISIBLE);
        }
    }


}