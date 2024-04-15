package com.example.quill;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


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
        boolean quillIsLiked = getIntent().getBooleanExtra("QUILL_ISLIKED",false);
        boolean isAdmin = getIntent().getBooleanExtra("QUILL_ISADMIN",false);

        binding.quillItemTitleTextView.setText(quillTitle);
        binding.quillContentTextView.setText(quillContent);


        displayBadges(quillCategory);
        hideAdminButtons(isAdmin);

        binding.backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = ExploreActivity.exploreIntentFactory(getApplicationContext());
                startActivity(intent);
            }
        });

        //Todo: Make this start the edit activity
        binding.quillEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ItemViewActivity.this, "Edit Pressed", Toast.LENGTH_SHORT).show();
            }
        });

        //Todo: Make this delete a quill item
        binding.quillTrashButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ItemViewActivity.this, "Delete Pressed", Toast.LENGTH_SHORT).show();
            }
        });

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