package com.example.newapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.Executors;

public class LoginActivity extends AppCompatActivity {

    UserRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (SessionManager.isLoggedIn(this)) {
            startActivity(new Intent(this, DashboardActivity.class));
            finish();
            return;
        }

        repository = new UserRepository(this);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        // Setup inputs
        EditText email = findViewById(R.id.inputEmail);
        EditText password = findViewById(R.id.inputPassword);
        Button login = findViewById(R.id.btnLogin);

        login.setOnClickListener(v -> {
            String emailInput = email.getText().toString().trim();
            String passwordInput = password.getText().toString().trim();

            // Validation logic
            if (emailInput.isEmpty()) {
                email.setError("Email is required");
                email.requestFocus();
                return;
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
                email.setError("Enter a valid email");
                email.requestFocus();
                return;
            }

            if (passwordInput.isEmpty()) {
                password.setError("Password is required!");
                password.requestFocus();
                return;
            }

            // 2. RUN DATABASE CHECK IN BACKGROUND
            Executors.newSingleThreadExecutor().execute(() -> {
                User user = repository.login(emailInput, passwordInput);

                // 3. RETURN TO MAIN THREAD TO UPDATE UI
                runOnUiThread(() -> {
                    if (user != null) {
                        SessionManager.saveUser(this, user.id);
                        startActivity(new Intent(this, DashboardActivity.class));
                        finish();
                    } else {
                        password.setError("Invalid email or password");
                        password.requestFocus();
                        Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT).show();
                    }
                });
            });
        });

        TextView signupText = findViewById(R.id.txtSignup);
        signupText.setOnClickListener(v -> {
            Intent signup_intent = new Intent(this, SignupActivity.class);
            signup_intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(signup_intent);

        });
    }
}
