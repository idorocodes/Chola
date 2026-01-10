package com.example.newapp;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TransactionDao {

    @Insert
    void insert(Transaction tx);

    @Query("SELECT * FROM transactions WHERE userId = :userId ORDER BY time DESC")
    List<Transaction> getUserTransactions(int userId);
}
