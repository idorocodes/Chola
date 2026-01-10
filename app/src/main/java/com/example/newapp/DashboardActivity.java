package com.example.newapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.DecimalFormat;
import java.util.List;

public class DashboardActivity extends AppCompatActivity {

    private UserRepository repository;
    private TransactionAdapter adapter;
    private RecyclerView recyclerView;
    private LinearLayout emptyState;
    private TextView dashboardBalance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
        setContentView(R.layout.activity_dashboard);
        overridePendingTransition(0, 0);

        repository = new UserRepository(this);

        dashboardBalance = findViewById(R.id.txtBalance);
        ImageView sendButton = findViewById(R.id.sendBtn);
        BottomNavigationView bottomNav = findViewById(R.id.bottomNav);


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

        loadBalance(userId);
        loadTransactions(userId);

        bottomNav.setSelectedItemId(R.id.homeNav);
        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.homeNav) return true;

            if (id == R.id.cardNav) {
                startActivity(new Intent(this, CardActivity.class));
                return true;
            }

            if (id == R.id.historyNav) {
                startActivity(new Intent(this, HistoryActivity.class));
                return true;
            }

            if (id == R.id.profileNav) {
                startActivity(new Intent(this, ProfileActivity.class));
                return true;
            }
            return false;
        });

        sendButton.setOnClickListener(v ->
                startActivity(new Intent(this, SendActivity.class))
        );
    }

    @Override
    protected void onResume() {
        super.onResume();

        BottomNavigationView bottomNav = findViewById(R.id.bottomNav);
        bottomNav.setSelectedItemId(R.id.homeNav);

        int userId = SessionManager.getUserId(this);
        if (userId != -1) {
            loadBalance(userId);
            loadTransactions(userId);
        }
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


    private void loadBalance(int userId) {
        new Thread(() -> {
            User user = repository.getUser(userId);

            runOnUiThread(() -> {
                if (user == null) {
                    startActivity(new Intent(this, LoginActivity.class));
                    finish();
                    return;
                }

                DecimalFormat df = new DecimalFormat("#,###.00");
                dashboardBalance.setText( "₦" + df.format(user.balance));
            });
        }).start();
    }
}
