package com.example.android.newsfy;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class SignInActivity extends AppCompatActivity {
    private static final String TAG = "SignInActivity";
    private EditText mUserEmail, mUserPassword;
    private FirebaseAuth mAuth;

    /*RequestQueue requestQueueVertical;
    public static ArrayList<News> mNewsVertical = new ArrayList<>();*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        mUserEmail = findViewById(R.id.email_sign_in_edit_text);
        mUserPassword = findViewById(R.id.password_sign_in_edit_text);
        mAuth = FirebaseAuth.getInstance();
        /*requestQueueVertical = Volley.newRequestQueue(this);
        requestNewsVertical();*/
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        //Check if user is still signed in
        if (currentUser != null) {
            Intent intent = new Intent(SignInActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }
    //Runs when "log in" button is clicked,signs in the user using the provided email and password
    public void loginClick(View view) {
        String email, password;
        email = mUserEmail.getText().toString();
        password = mUserPassword.getText().toString();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                            startActivity(intent);
                            Toast.makeText(getApplicationContext(), R.string.log_in_successful, Toast.LENGTH_SHORT).show();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.d(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(getApplicationContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    //This function opens the "Create account" view
    public void createAccountClick(View v) {
        Intent intent = new Intent(SignInActivity.this, CreateAccountActivity.class);
        startActivity(intent);
    }

    /*public void requestNewsVertical() {
        String url = "https://newsapi.org/v2/top-headlines?country=us&apiKey=c02b29741b1d4f46bb1246a1d4b0e5cf";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray articles = response.getJSONArray("articles");

                    for (int i = 0; i < articles.length(); i++) {
                        JSONObject main = articles.getJSONObject(i);
                        JSONObject source = main.getJSONObject("source");
                        String newsSource = source.getString("name");
                        String newsAuthor = main.getString("author");
                        String newsTitle = main.getString("title");
                        String newsImageURL = main.getString("urlToImage");
                        String newsUrl = main.getString("url");
                        String timeFromApi = main.getString("publishedAt");
                        String time = timeFromApi.substring(0, timeFromApi.length() - 1);

                        String[] newsTimeAndDate = time.split("T");
                        String formattedDate = "";

                        try {
                            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
                            SimpleDateFormat outputFormat = new SimpleDateFormat("MMM d, yyyy");
                            Date date = inputFormat.parse(newsTimeAndDate[0]);
                            formattedDate = outputFormat.format(date);
                            formattedDate += "  ";

                            SimpleDateFormat inputTime = new SimpleDateFormat("HH:mm:ss");
                            SimpleDateFormat outputTime = new SimpleDateFormat("h:mm a");
                            Date formattedTime = inputTime.parse(newsTimeAndDate[1]);
                            formattedDate += outputTime.format(formattedTime);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        String newsContent = main.getString("content");
                        int index = newsContent.indexOf("[");
                        String newsContentDisplay = newsContent.substring(0, index);
                        mNewsVertical.add(new News(newsImageURL, newsUrl, newsTitle, formattedDate, newsAuthor, newsContentDisplay, newsSource));
                        Log.d("hi", "Data:" + "\n" + mNewsVertical.get(i).getNewsImageURL() + "\n" + mNewsVertical.get(i).getNewsUrl());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        requestQueueVertical.add(jsonObjectRequest);
    }*/
}
