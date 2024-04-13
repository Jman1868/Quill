package com.example.quill;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.quill.database.QuillRepository;
import com.example.quill.database.entities.Quill;
import com.example.quill.databinding.ActivityExploreBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class ExploreActivity extends AppCompatActivity {


    ActivityExploreBinding binding;
    private Quill_Item_RecyclerViewAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityExploreBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        RecyclerView recyclerView = findViewById(R.id.quillRecyclerView);

        QuillRepository repository = QuillRepository.getRepository(getApplication());
        LiveData<List<Quill>> quillsLiveData = repository.getAllQuillsLiveData();

        quillsLiveData.observe(this, quills -> {
            adapter = new Quill_Item_RecyclerViewAdapter(quills);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        });


        handleNav();

    }

    static Intent exploreIntentFactory(Context context){
        return new Intent(context, ExploreActivity.class);
    }


    void handleNav() {

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.navigation_explore);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.navigation_home) {

                Intent intent = new Intent(ExploreActivity.this,MainActivity.class);
                startActivity(intent);
            }

            if (item.getItemId() == R.id.navigation_account) {

                Intent intent = AccountActivity.accountIntentFactory(getApplicationContext());
                startActivity(intent);
            }
            return false;
        });
    }
}