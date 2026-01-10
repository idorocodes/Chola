package com.example.newapp;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "users")
public class User {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String fullname;

    public String email;

    public double balance;

    public String matricno;
    public String password;

    public User() {};

    @Ignore
    public User(String email, String fullname, String matricno, String password) {
        this.email = email;
        this.fullname = fullname;
        this.matricno = matricno;
        this.password = password;
        this.balance = 300000.00;
    }




}
