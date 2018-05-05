package com.example.abhi.database;

import android.app.Application;

import com.firebase.client.Firebase;


public class Database extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
    }
}
