package com.example.newapp;

import androidx.room.Entity;
import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Insert;
import androidx.room.PrimaryKey;

@Dao
public interface UserDao {

    @Insert
    long insert(User user);
    @Query("SELECT * FROM users WHERE email = :email AND password = :password LIMIT 1")
    User login(String email, String password);


    @Query("SELECT * FROM users WHERE id = :id LIMIT 1")
    User getUserById(int id);

    @Query("UPDATE users SET balance = :balance WHERE id = :id")
    void updateBalance(int id, double balance);


    @Query("SELECT * FROM users WHERE matricno = :matricno LIMIT 1")
    User getUserByAccount(String matricno);}
