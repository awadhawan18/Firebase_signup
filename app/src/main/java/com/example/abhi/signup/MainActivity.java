package com.example.abhi.signup;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private Button login_button,signup_button;
    private EditText editemail,editpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        login_button = findViewById(R.id.login);
        signup_button = findViewById(R.id.signup);

        editemail = findViewById(R.id.email);
        editpassword = findViewById(R.id.password);

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser()!=null){
                    startActivity(new Intent(getApplicationContext(),AccountPage.class));
                }
            }
        };

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

        signup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        String email = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString(getString(R.string.email_key),"abc");
        String password = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString(getString(R.string.password_key),"abc");
        if(!email.equals("abc")){
            Intent intent = new Intent(getApplicationContext(),AccountPage.class);
            intent.putExtra("email",email);
            startActivity(intent);
            finish();
        }



    }

    private void signIn(){
        final String email = editemail.getText().toString();
        final String password = editpassword.getText().toString();
        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
            Toast.makeText(getApplicationContext(),"Field(s) are empty",Toast.LENGTH_SHORT).show();
        }else if(!isEmailValid(email)){
            Toast.makeText(getApplicationContext(),"Please enter a valid email",Toast.LENGTH_SHORT).show();
        }else if(!isNetworkAvailable()){
            Toast.makeText(getApplicationContext(),"Network Unavailable",Toast.LENGTH_SHORT).show();
        }
        else{
            mAuth.signInWithEmailAndPassword(email,password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                user = mAuth.getCurrentUser();

                                PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("com.abhi.login.email",email).apply();
                                PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("com.abhi.login.password",password).apply();
                                Intent intent = new Intent(getApplicationContext(),AccountPage.class);
                                intent.putExtra("email",email);
                                startActivity(intent);

                            }
                            else{
                                Toast.makeText(getApplicationContext(),"Login Failed",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

        }


    }
    private void signUp(){
        final String email = editemail.getText().toString();
        final String password = editpassword.getText().toString();
        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
            Toast.makeText(getApplicationContext(),"Field(s) are empty",Toast.LENGTH_SHORT).show();
        }else if(!isEmailValid(email)){
            Toast.makeText(getApplicationContext(),"Please enter a valid email",Toast.LENGTH_SHORT).show();
        }else if(password.length()<6){
            Toast.makeText(getApplicationContext(),"Need password greater than 5 letters",Toast.LENGTH_SHORT).show();
        }else if(!isNetworkAvailable()){
            Toast.makeText(getApplicationContext(),"Network Unavailable",Toast.LENGTH_SHORT).show();
        }
        else{

            mAuth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                user = mAuth.getCurrentUser();

                                PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("com.abhi.login.email",email).apply();
                                PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("com.abhi.login.password",password).apply();
                                Intent intent = new Intent(getApplicationContext(),AccountPage.class);
                                intent.putExtra("email",email);
                                startActivity(intent);
                            } else {
                                Toast.makeText(getApplicationContext(),"Sign Up Failed",Toast.LENGTH_SHORT).show();

                            }
                        }
                    });

        }
    }

    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
