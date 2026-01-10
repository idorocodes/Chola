package com.example.newapp;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;


@Entity(tableName = "transactions")
public class Transaction {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public int userId;
    public String type;
    public double amount;
    public String counterParty;

    public String senderMatno;

    public String description;


    public long time;

    public Transaction(int userId, String senderMatno, String type, double amount, String counterParty, String description) {
        this.userId = userId;
        this.senderMatno = senderMatno;
        this.type = type;
        this.amount = amount;
        this.description = description;
        this.counterParty = counterParty;
        this.time = System.currentTimeMillis();
    }
}

