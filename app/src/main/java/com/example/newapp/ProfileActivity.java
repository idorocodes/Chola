package com.example.newapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ProfileActivity extends AppCompatActivity {

    UserRepository repository;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);
        overridePendingTransition(0, 0);

        BottomNavigationView bottomNav = findViewById(R.id.bottomNav);

        bottomNav.setSelectedItemId(R.id.profileNav);


        repository  = new UserRepository(this);

        int userId = SessionManager.getUserId(this);

        if (userId == -1) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }

        new Thread(() -> {
            User user = repository.getUser(userId);

            runOnUiThread(() -> {
                if (user != null) {
                    TextView profileName = findViewById(R.id.profileName);
                    TextView profileMatNo = findViewById(R.id.profileMatric);
                    profileName.setText(user.fullname);
                    profileMatNo.setText(user.matricno);
                } else {
                    startActivity(new Intent(this, LoginActivity.class));
                    finish();
                }
            });
        }).start();


        bottomNav.setOnItemSelectedListener( item -> {

            if (item.getItemId() == R.id.homeNav){
                Intent my_intent =  new Intent(this, DashboardActivity.class);
                my_intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(my_intent);
                finish();
                return true;
            }

            if (item.getItemId() == R.id.cardNav) {
                Intent my_intent =  new Intent(this, CardActivity.class);
                my_intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(my_intent);
                finish();
                return true;
            }

            if (item.getItemId() == R.id.historyNav){
                Intent my_intent =  new Intent(this, HistoryActivity.class);
                my_intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(my_intent);
                finish();
                return true;
            }

            if (item.getItemId() == R.id.profileNav) {
                return true;
            }
            return true;
        });
        TextView logOutText = findViewById(R.id.logoutText);

        logOutText.setOnClickListener( v ->  {
            SessionManager.clear(this);
            Intent logOutIntent = new Intent(this, LoginActivity.class);
            startActivity(logOutIntent);
            finish();
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        BottomNavigationView bottomNav = findViewById(R.id.bottomNav);
        bottomNav.setSelectedItemId(R.id.profileNav);
    }
}