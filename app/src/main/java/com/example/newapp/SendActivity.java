package com.example.newapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.util.concurrent.Executors;

public class SendActivity extends AppCompatActivity {

    UserRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);

        setContentView(R.layout.activity_send);

        repository = new UserRepository(this);
        int userId = SessionManager.getUserId(this);

        EditText recipientInput = findViewById(R.id.Matric);
        EditText amountInput = findViewById(R.id.inputAmount);
        EditText inputNote = findViewById(R.id.inputNote);
        Button btnSend = findViewById(R.id.btnSend);



        btnSend.setOnClickListener(v -> {
            int senderId = SessionManager.getUserId(this);
            String receiver = recipientInput.getText().toString().trim();
            String note = inputNote.getText().toString().trim();

            double amt;
            try {
                amt = Double.parseDouble(amountInput.getText().toString());
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Enter a valid amount", Toast.LENGTH_SHORT).show();
                return;
            }

            if (receiver.isEmpty() || amt < 0 || amt == 0 || note.isEmpty() ) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }
            btnSend.setEnabled(false);

            Executors.newSingleThreadExecutor().execute(() -> {
                repository.sendMoney(senderId, receiver, amt, note,
                        new UserRepository.ResultCallback() {
                            @Override
                            public void success() {
                                runOnUiThread(() -> {
                                    Toast.makeText(SendActivity.this,
                                            "Transfer successful", Toast.LENGTH_SHORT).show();
                                    finish();
                                });

                            }
                            @Override
                            public void error(String msg) {
                                runOnUiThread(() ->
                                        Toast.makeText(SendActivity.this, msg, Toast.LENGTH_SHORT).show()
                                );
                            }
                        });
            });
        });

    }

}
