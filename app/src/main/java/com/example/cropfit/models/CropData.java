package com.example.cropfit.models;

import com.google.firebase.firestore.Exclude;

public class CropData {
    public Object n, p, k, ph, temp, humidity, rainfall;
    public String result, date;

    @Exclude
    private String id;

    public CropData() {}

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getN() { return String.valueOf(n != null ? n : "0"); }
    public String getP() { return String.valueOf(p != null ? p : "0"); }
    public String getK() { return String.valueOf(k != null ? k : "0"); }
    public String getResult() { return result != null ? result : ""; }
    public String getDate() { return date != null ? date : ""; }
}