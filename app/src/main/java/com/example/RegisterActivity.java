package com.example;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.webkit.internal.ApiFeature;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    private EditText emailEditText, passwordEditText, retypePasswodEditText;
    private Button registerButton, loginLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        emailEditText = findViewById(R.id.etEmail);
        passwordEditText = findViewById(R.id.etPassword);
        retypePasswodEditText = findViewById(R.id.etRetypePassword);
        registerButton = findViewById(R.id.registerBtn);
        loginLink = findViewById(R.id.loginBtn);

        registerButton.setOnClickListener(e -> {
            String email = emailEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            String retypePassword = retypePasswodEditText.getText().toString();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(RegisterActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            } else if (password != retypePassword) {
                Toast.makeText(RegisterActivity.this, "Passwords do not match.", Toast.LENGTH_SHORT).show();
            } else {
                registerUser(email, password);
            }
        });

        loginLink.setOnClickListener(e -> {
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
        });
    }

    private void registerUser(String email, String password) {
        RegisterRequest registerRequest = new RegisterRequest(email, password); // Changed from username to email
        Call<RegisterResponse> call = RetrofitClient.getApiService().registerUser(registerRequest);

        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    RegisterResponse registerResponse = response.body();
                    Toast.makeText(RegisterActivity.this, registerResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    finish(); // Close the register activity and return to login
                } else {
                    Toast.makeText(RegisterActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
                    Log.e("Registration Failed", "");
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("Registration Failed", Objects.requireNonNull(t.getMessage()));
            }
        });
    }
}