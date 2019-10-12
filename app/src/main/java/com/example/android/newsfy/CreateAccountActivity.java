package com.example.android.newsfy;

import android.content.Intent;
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

public class CreateAccountActivity extends AppCompatActivity {
    private static final String TAG = "CreateAccountActivity";
    private FirebaseAuth mAuth;
    private String mUserName, mUserEmail, mUserPassword, mUserConfirmPassword;
    private EditText mName, mEmail, mPassword, mConfirmPassword;
    private Button mCreateAccountButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        mAuth = FirebaseAuth.getInstance();

        mName = findViewById(R.id.name_sign_up_edit_text);
        mEmail = findViewById(R.id.email_sign_up_edit_text);
        mPassword = findViewById(R.id.password_sign_up_edit_text);
        mConfirmPassword = findViewById(R.id.confirm_password_sign_up_edit_text);

        mCreateAccountButton = findViewById(R.id.create_account_button);

        mCreateAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                disableButton(mCreateAccountButton);
                mUserName = mName.getText().toString();
                mUserEmail = mEmail.getText().toString().trim();
                mUserPassword = mPassword.getText().toString().trim();
                mUserConfirmPassword = mConfirmPassword.getText().toString().trim();
                createAccount(mUserName, mUserEmail, mUserPassword, mUserConfirmPassword);
            }
        });
    }

    //Create an account for new users
    public void createAccount(String userName, String userEmail, String userPassword, String userConfirmPassword) {

        if (TextUtils.isEmpty(userName)) {
            Toast.makeText(getApplicationContext(), R.string.enter_your_name, Toast.LENGTH_SHORT).show();
            enableButton(mCreateAccountButton);
        } else if (TextUtils.isEmpty(userEmail)) {
            Toast.makeText(getApplicationContext(), R.string.enter_email, Toast.LENGTH_SHORT).show();
            enableButton(mCreateAccountButton);
        } else if (TextUtils.isEmpty(userPassword)) {
            Toast.makeText(getApplicationContext(), R.string.enter_your_password, Toast.LENGTH_SHORT).show();
            enableButton(mCreateAccountButton);
        } else if (TextUtils.isEmpty(userConfirmPassword)) {
            Toast.makeText(getApplicationContext(), R.string.confirm_password, Toast.LENGTH_SHORT).show();
            enableButton(mCreateAccountButton);
        }
        else {
            if (userEmail.contains("@")) {
                if (userPassword.equals(userConfirmPassword)) {
                    mAuth.createUserWithEmailAndPassword(userEmail, userPassword)
                            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d(TAG, "createUserWithEmail:success");
                                        Intent intent = new Intent(CreateAccountActivity.this, MainActivity.class);
                                        startActivity(intent);
                                        Toast.makeText(getApplicationContext(), R.string.account_created, Toast.LENGTH_SHORT).show();

                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.d(TAG, "createUserWithEmail:failure", task.getException());
                                        Toast.makeText(CreateAccountActivity.this, R.string.account_not_created, Toast.LENGTH_SHORT).show();
                                        enableButton(mCreateAccountButton);
                                    }

                                }
                            });
                } else {
                    Toast.makeText(getApplicationContext(), R.string.check_password, Toast.LENGTH_SHORT).show();
                    enableButton(mCreateAccountButton);
                }
            } else {
                Toast.makeText(getApplicationContext(), R.string.check_email, Toast.LENGTH_SHORT).show();
                enableButton(mCreateAccountButton);
            }
        }
    }

    public void enableButton(Button button) {
        button.setEnabled(true);
        button.setBackgroundResource(R.drawable.rounded_button);
    }

    public void disableButton(Button button) {
        button.setEnabled(false);
        button.setBackgroundResource(R.drawable.rounded_button_disabled);
    }

}
