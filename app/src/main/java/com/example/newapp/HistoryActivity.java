package com.example.newapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    private UserRepository repository;
    private TransactionAdapter adapter;
    private RecyclerView recyclerView;
    private LinearLayout emptyState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
        setContentView(R.layout.activity_history);
        overridePendingTransition(0, 0);
        repository = new UserRepository(this);

        
        recyclerView = findViewById(R.id.recyclerView);
        emptyState = findViewById(R.id.emptyState);
        
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TransactionAdapter();
        recyclerView.setAdapter(adapter);

        int userId = SessionManager.getUserId(this);

        if (userId == -1) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }

        loadTransactions(userId);

        BottomNavigationView bottomNav = findViewById(R.id.bottomNav);
        bottomNav.setSelectedItemId(R.id.historyNav);
        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.homeNav) {
                startActivity(new Intent(this, DashboardActivity.class));
                return true;
            } else if (id == R.id.cardNav) {
                startActivity(new Intent(this, CardActivity.class));
                return true;
            } else if (id == R.id.historyNav) {
                return true;
            } else if (id == R.id.profileNav) {
                startActivity(new Intent(this, ProfileActivity.class));
                return true;
            }
            return false;
        });
    }

    private void loadTransactions(int userId) {
        new Thread(() -> {
            List<Transaction> txs = repository.getTransactions(userId);

            runOnUiThread(() -> {
                if (txs == null || txs.isEmpty()) {
                    emptyState.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                } else {
                    adapter.submitList(txs);
                    emptyState.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                }
            });
        }).start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        BottomNavigationView bottomNav = findViewById(R.id.bottomNav);
        bottomNav.setSelectedItemId(R.id.historyNav);
        
        int userId = SessionManager.getUserId(this);
        if (userId != -1) {
            loadTransactions(userId);
        }
    }
}