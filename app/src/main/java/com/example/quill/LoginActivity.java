package com.example.quill;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import com.example.quill.database.QuillRepository;
import com.example.quill.database.entities.User;
import com.example.quill.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;

    private QuillRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = QuillRepository.getRepository(getApplication());

        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyUser();
            }
        });
        
        binding.bottomImageTextViewClickable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = SignupActivity.signupIntentFactory(getApplicationContext());
                startActivity(intent);
            }
        });
        
        
    }

    private void verifyUser() {

        String username = binding.userNameLoginEditText.getText().toString();

        if (username.isEmpty()){
            toastMaker("Username may not be blank");
            return;
        }
        LiveData<User> userObserver = repository.getUserByUserName(username);
        userObserver.observe(this, user -> {
            if (user != null) {
                String password = binding.passwordLoginEditText.getText().toString();
                if (password.equals(user.getPassword())) {
                    startActivity(MainActivity.mainActivityIntentFactory(getApplicationContext(), user.getId()));
                }
                else {
                    toastMaker("Invalid password");
                    binding.passwordLoginEditText.setSelectAllOnFocus(true);
                }
            }
            else {
                toastMaker(String.format("%s does not exist.", username));
                binding.userNameLoginEditText.setSelectAllOnFocus(true);
            }
        });
    }

    private void toastMaker(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    static Intent loginIntentFactory(Context context){
        return new Intent(context, LoginActivity.class);
    }
}