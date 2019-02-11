package com.example.abhi.signup;

import android.content.Intent;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;

public class LogOut extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString(getString(R.string.email_key),"abc").apply();
        PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("com.abhi.login.password","abc").apply();

        FirebaseAuth.getInstance().signOut();
        finish();

    }
}
