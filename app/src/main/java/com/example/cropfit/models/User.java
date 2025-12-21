package com.example.cropfit.models;

public class User {
    public String name;
    public String email;
    public String uid;

    public User() {} // Required for Firestore

    public User(String name, String email, String uid) {
        this.name = name;
        this.email = email;
        this.uid = uid;
    }
}
