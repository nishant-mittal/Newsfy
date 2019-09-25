package com.example.android.newsfy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class SignInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
    }
    //This function opens
    public void createAccountClick(View v) {
        Intent intent = new Intent(SignInActivity.this,CreateAccountActivity.class);
        startActivity(intent);
    }
}
