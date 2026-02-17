package com.example.a2026scoutingapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;

public class CreateAccountActivity extends AppCompatActivity {
    private EditText createAccountEmail, createAccountPassword, createAccountFullName;
    private MaterialButton createAccountButton;
    private ImageView returnButton;
    private FirebaseHelper firebaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        setContentView(R.layout.create_account);

        createAccountFullName = findViewById(R.id.createAccountFullName);
        createAccountEmail = findViewById(R.id.createAccountEmail);
        createAccountPassword = findViewById(R.id.createAccountPassword);
        createAccountButton = findViewById(R.id.createAccountButton);
        returnButton = findViewById(R.id.returnButton);
        firebaseHelper = new FirebaseHelper();


        createAccountButton.setOnClickListener(v -> {
            String username = createAccountFullName.getText().toString();
            String email = createAccountEmail.getText().toString();
            String password = createAccountPassword.getText().toString();

            if (email.isEmpty() || password.isEmpty() || username.isEmpty()) {
                Toast.makeText(this, "Fill in all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            firebaseHelper.registerUser(email, password, new FirebaseHelper.FirebaseCallback() {
                @Override
                public void onSuccess() {
                    Toast.makeText(CreateAccountActivity.this, "Account created!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(CreateAccountActivity.this, SignInActivity.class));
                    finish();
                }

                @Override
                public void onFailure(String error) {
                    Toast.makeText(CreateAccountActivity.this, "Error: " + error, Toast.LENGTH_SHORT).show();
                }
            });
        });

        returnButton.setOnClickListener(v -> startActivity(new Intent(CreateAccountActivity.this, SignInActivity.class)));



    }
}