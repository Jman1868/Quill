package com.example.quill;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.quill.databinding.ActivityAccountBinding;

public class AccountActivity extends AppCompatActivity {

   ActivityAccountBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = com.example.quill.databinding.ActivityAccountBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    static Intent accountIntentFactory(Context context){
        return new Intent(context, AccountActivity.class);
    }
}