package com.example.android.newsfy;

import android.content.Intent;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private TextView signOutTextView;
    public static ArrayList<News> mNews = new ArrayList<>();
    RequestQueue requestQueue;
    RecyclerView recyclerViewHorizontal;
    private RecyclerViewAdapterHorizontal mRecyclerViewAdapterHorizontal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance(DateFormat.MEDIUM).format(calendar.getTime());
        TextView dateTextView = findViewById(R.id.date_text_view);
        dateTextView.setText(currentDate);

        requestQueue = Volley.newRequestQueue(this);
        requestNews();

        recyclerViewHorizontal = findViewById(R.id.news_recycler_view_horizontal);
        //requestNews();
        /*for(int i = 0; i < mNews.size(); i++) {
            Log.d("lol", "News " + mNews.get(i).getNewsAuthor() + "\n" + mNews.get(i).getNewsImageURL());
        }*/
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewHorizontal.setLayoutManager(manager);
        signOutTextView = findViewById(R.id.sign_out);

        signOutTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(MainActivity.this,SignInActivity.class);
                startActivity(intent);
            }
        });
    }



    public void requestNews() {
        String url = "https://newsapi.org/v2/top-headlines?sources=google-news&apiKey=c02b29741b1d4f46bb1246a1d4b0e5cf";


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
                                String title = main.getString("title");
                                String[] newsTitle = title.split("-");
                                String newsImageURL = main.getString("urlToImage");
                                String newsUrl = main.getString("url");
                                String timeFromApi = main.getString("publishedAt");
                                String time = timeFromApi.substring(0, timeFromApi.length() - 1);
                                String[] newsTimeAndDate = time.split("T");
                                Log.d("lol", "onResponse: " + newsTimeAndDate[0] + "\n" + newsTimeAndDate[1]);
                                String newsDate = "";
                                //String[] newsTime = newsTimeAndDate[1].split("+");
                                try {
                                    Date date = new SimpleDateFormat("yyyy-MM-dd").parse(newsTimeAndDate[0]);
                                    newsDate = newsDate + date;
                                    Log.d("lol", "on: " + newsTimeAndDate[0]);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                String newsContent = main.getString("content");

                                mNews.add(new News(newsImageURL, newsUrl, newsTitle[0], newsTimeAndDate[0], newsAuthor, newsContent, newsSource));
                            }
                            mRecyclerViewAdapterHorizontal = new RecyclerViewAdapterHorizontal(MainActivity.this, mNews);
                            HorizontalSpaceItemDecorator decorator = new HorizontalSpaceItemDecorator(22);
                            mRecyclerViewAdapterHorizontal.setListener(new RecyclerViewAdapterHorizontal.Listener() {
                                @Override
                                public void onClick(int position) {
                                    Intent intent = new Intent(MainActivity.this, NewsActivity.class);
                                    intent.putExtra("position",position);
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
}
