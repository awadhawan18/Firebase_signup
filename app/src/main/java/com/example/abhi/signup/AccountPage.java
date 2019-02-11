package com.example.abhi.signup;

import android.content.Intent;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class AccountPage extends AppCompatActivity {
    Button logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_page);

        String email = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString(getString(R.string.email_key),"abc");
        TextView hello = findViewById(R.id.hello);
        hello.setText("Hello, ".concat(email));

        logout = (Button)findViewById(R.id.logout);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString(getString(R.string.email_key),"abc").apply();
                PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("com.abhi.login.password","abc").apply();

                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        String email = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString(getString(R.string.email_key),"abc");
        if(email.equals("abc")){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
        }

    }
}
