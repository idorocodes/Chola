package com.example.newapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class CardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_card);
        overridePendingTransition(0, 0);

        BottomNavigationView bottomNav = findViewById(R.id.bottomNav);

        bottomNav.setSelectedItemId(R.id.cardNav);

        bottomNav.setOnItemSelectedListener( item -> {

            if (item.getItemId() == R.id.homeNav){
               Intent my_intent =  new Intent(this, DashboardActivity.class);
               my_intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
               startActivity(my_intent);
               finish();
                return true;
            }

            if (item.getItemId() == R.id.cardNav) {
                return true;
            }

            if (item.getItemId() == R.id.historyNav){
                startActivity(new Intent(this, HistoryActivity.class));
               finish();
                return true;
            }

            if (item.getItemId() == R.id.profileNav) {
                startActivity(new Intent(this, ProfileActivity.class));
                finish();
                return true;
            }
            return true;
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        BottomNavigationView bottomNav = findViewById(R.id.bottomNav);
        bottomNav.setSelectedItemId(R.id.cardNav);
    }
}