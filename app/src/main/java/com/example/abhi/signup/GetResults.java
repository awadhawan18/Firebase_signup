package com.example.abhi.signup;

import android.content.Intent;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class GetResults extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String email = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString(getString(R.string.email_key),"abc");
        Intent intent = new Intent();
        intent.setAction("com.example.abhi.login.RESPONSE");
        intent.putExtra("email",email);
        startActivity(intent);
        finish();

    }
}
