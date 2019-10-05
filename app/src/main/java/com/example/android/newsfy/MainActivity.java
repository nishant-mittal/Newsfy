package com.example.android.newsfy;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private TextView signOutTextView;
    public static ArrayList<News> mNews = new ArrayList<>();
    public static ArrayList<News> mNewsVertical = new ArrayList<>();
    RequestQueue requestQueue;
    RecyclerView recyclerViewHorizontal;
    RecyclerView recyclerViewVertical;

    private RecyclerViewAdapterHorizontal mRecyclerViewAdapterHorizontal;
    RecyclerViewAdapterVertical mRecyclerViewAdapterVertical;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Set current date
        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance(DateFormat.MEDIUM).format(calendar.getTime());
        TextView dateTextView = findViewById(R.id.date_text_view);
        dateTextView.setText(currentDate);

        //request news
        requestQueue = Volley.newRequestQueue(this);
        requestNews();
        requestNewsVertical();

        recyclerViewHorizontal = findViewById(R.id.news_recycler_view_horizontal);
        //recyclerViewHorizontal.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewHorizontal.setLayoutManager(manager);
        signOutTextView = findViewById(R.id.sign_out);

        recyclerViewVertical = findViewById(R.id.news_recycler_view_vertical);
        recyclerViewVertical.setHasFixedSize(true);
        recyclerViewVertical.setNestedScrollingEnabled(false);
        LinearLayoutManager manager1 = new LinearLayoutManager(this);
        recyclerViewVertical.setLayoutManager(manager1);

        //When sign out gets clicked
        signOutTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(MainActivity.this, SignInActivity.class);
                startActivity(intent);
            }
        });
    }

    //Performs an http request for world news
    public void requestNews() {
        String url = "https://newsapi.org/v2/top-headlines?sources=the-wall-street-journal&apiKey=c02b29741b1d4f46bb1246a1d4b0e5cf";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray articles = response.getJSONArray("articles");

                            for (int i = 0; i < articles.length(); i++) {
                                JSONObject main = articles.getJSONObject(i);
                                JSONObject source = main.getJSONObject("source");
                                String newsSource = source.getString("name");
                                String newsDescription = main.getString("description");
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
                                int index = newsContent.indexOf('[');
                                String actualNews = "";
                                if (index != -1) {
                                    actualNews = newsContent.substring(0, index);
                                }
                                mNews.add(new News(newsImageURL, newsUrl, newsTitle, formattedDate, newsAuthor, actualNews, newsSource, newsDescription));
                            }
                            mRecyclerViewAdapterHorizontal = new RecyclerViewAdapterHorizontal(MainActivity.this, mNews);
                            HorizontalSpaceItemDecorator decorator = new HorizontalSpaceItemDecorator(22);
                            mRecyclerViewAdapterHorizontal.setListener(new RecyclerViewAdapterHorizontal.Listener() {
                                @Override
                                public void onClick(int position) {
                                    Intent intent = new Intent(MainActivity.this, NewsActivity.class);
                                    intent.putExtra("position", position);
                                    intent.putExtra("news", 0);
                                    startActivity(intent);
                                }
                            });
                            recyclerViewHorizontal.addItemDecoration(decorator);
                            recyclerViewHorizontal.setAdapter(mRecyclerViewAdapterHorizontal);

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

        requestQueue.add(request);
    }

    //Performs an http request for local news
    public void requestNewsVertical() {
        String url = "https://newsapi.org/v2/top-headlines?country=in&apiKey=9582fd2f638d4fad9c6b03df774b39ae";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
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
                                String newsDescription = main.getString("description");
                                String newsImageURL = main.getString("urlToImage");
                                String newsUrl = main.getString("url");
                                String timeFromApi = main.getString("publishedAt");
                                String time = timeFromApi.substring(0, timeFromApi.length() - 1);

                                if (newsAuthor.equals("null")) {
                                    newsAuthor = "NA";
                                }

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
                                int index = newsContent.indexOf('[');
                                String actualNews = "";
                                if (index != -1) {
                                    actualNews = newsContent.substring(0, index);
                                }

                                mNewsVertical.add(new News(newsImageURL, newsUrl, newsTitle, formattedDate, newsAuthor, actualNews, newsSource, newsDescription));
                            }
                            mRecyclerViewAdapterVertical = new RecyclerViewAdapterVertical(MainActivity.this, mNewsVertical);
                            mRecyclerViewAdapterVertical.setListener(new RecyclerViewAdapterVertical.Listener() {
                                @Override
                                public void onClick(int position) {
                                    Intent intent = new Intent(MainActivity.this, NewsActivity.class);
                                    intent.putExtra("position", position);
                                    intent.putExtra("news", 1);
                                    startActivity(intent);
                                }
                            });

                            recyclerViewVertical.setAdapter(mRecyclerViewAdapterVertical);

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

        requestQueue.add(request);
    }

    //Exits app when back button is clicked
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}
