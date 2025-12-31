package com.example.newapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        overridePendingTransition(0, 0);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        );


        ImageView sendButton = findViewById(R.id.sendBtn);

        sendButton.setOnClickListener( v -> {
            startActivity(new Intent(this, SendActivity.class));
        });

        BottomNavigationView bottomNav = findViewById(R.id.bottomNav);

        bottomNav.setSelectedItemId(R.id.homeNav);

        bottomNav.setOnItemSelectedListener( item -> {

            if (item.getItemId() == R.id.homeNav){
                return true;
            }

            if (item.getItemId() == R.id.cardNav) {
                startActivity(new Intent(this, CardActivity.class));
                return true;
            }

            if (item.getItemId() == R.id.historyNav){
                startActivity(new Intent(this, HistoryActivity.class));
                return true;
            }

            if (item.getItemId() == R.id.profileNav) {
                startActivity(new Intent(this, ProfileActivity.class));
                return true;
            }
            return true;
        });

    }
    @Override
    protected void onResume() {
        super.onResume();
        BottomNavigationView bottomNav = findViewById(R.id.bottomNav);
        bottomNav.setSelectedItemId(R.id.homeNav);
    }


}
