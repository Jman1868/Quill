package com.example.quill;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.quill.databinding.ActivityLandingPageBinding;
import com.example.quill.databinding.ActivityLoginBinding;

public class LandingPageActivity extends AppCompatActivity {

    private ActivityLandingPageBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLandingPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.landingpageLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = LoginActivity.loginIntentFactory(getApplicationContext());
                startActivity(intent);
            }
        });
        
        binding.landingpageSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = SignupActivity.signupIntentFactory(getApplicationContext());
                startActivity(intent);
            }
        });

    }


    static Intent landingpageIntentFactory(Context context){
        return new Intent(context, LandingPageActivity.class);
    }
}