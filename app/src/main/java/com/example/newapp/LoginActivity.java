package com.example.newapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;


public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_login);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        );

        EditText email = findViewById(R.id.inputEmail);
        EditText password = findViewById(R.id.inputPassword);
        Button login = findViewById(R.id.btnLogin);

        
        login.setOnClickListener( v -> {

            String emailInput = email.getText().toString().trim();
            String passwordInput = password.getText().toString().trim();

            if (emailInput.isEmpty()){
                email.setError("Email is required");
                email.requestFocus();
                return;
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()){
                email.setError("Enter a valid email");
                email.requestFocus();
                return;
            }

            if (passwordInput.isEmpty()){
                password.setError("Password is required!");
                password.requestFocus();
                return;
            }

            if (passwordInput.length() < 6) {
                password.setError("Password should be more than 6 digits");
                password.requestFocus();
                return;
            }

            Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
            startActivity(intent);
            finish();
        });


        TextView signupText = findViewById(R.id.txtSignup);
        signupText.setOnClickListener( v -> {
            Intent signup_intent  = new Intent(this, SignupActivity.class);
            signup_intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(signup_intent);
            finish();
        });
    }


}