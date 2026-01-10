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

public class SignupActivity extends AppCompatActivity {

    private UserRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);

        repository = new UserRepository(this);

        EditText email = findViewById(R.id.inputEmail);
        EditText fullname = findViewById(R.id.inputFullName);
        EditText matric = findViewById(R.id.inputMatricNo);
        EditText password = findViewById(R.id.inputPassword);
        EditText confirmPassword = findViewById(R.id.inputConfirmPassword);
        Button signup = findViewById(R.id.btnsignUp);

        signup.setOnClickListener(v -> {

            String emailInput = email.getText().toString().trim();
            String fullnameInput = fullname.getText().toString().trim();
            String matricInput = matric.getText().toString().trim();
            String passwordInput = password.getText().toString().trim();
            String confirm = confirmPassword.getText().toString().trim();

            // VALIDATION
            if (emailInput.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
                email.setError("Valid email required");
                email.requestFocus();
                return;
            }

            if (fullnameInput.isEmpty()) {
                fullname.setError("Full name required");
                fullname.requestFocus();
                return;
            }

            if (matricInput.isEmpty()) {
                matric.setError("Matric number required");
                matric.requestFocus();
                return;
            }

            if (passwordInput.length() < 6) {
                password.setError("Password must be at least 6 characters");
                password.requestFocus();
                return;
            }


            if (!passwordInput.equals(confirm)){
                confirmPassword.setError("Passwords do not match");
                confirmPassword.requestFocus();
                return;
            }

            User user = new User(emailInput, fullnameInput, matricInput, passwordInput);


            Executors.newSingleThreadExecutor().execute(() -> {

                long id = repository.signUp(user);

                runOnUiThread(() -> {
                    if (id > 0) {
                        Toast.makeText(SignupActivity.this, "Signup successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(SignupActivity.this, "Signup failed", Toast.LENGTH_SHORT).show();
                    }
                });
            });
        });


        TextView login = findViewById(R.id.txtLogin);
        login.setOnClickListener(v -> {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });
    }
}
