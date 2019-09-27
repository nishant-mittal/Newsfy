package com.example.android.newsfy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

public class NewsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        ImageView newsImageView;
        TextView newsTitle;
        final TextView newsAuthor;
        TextView newsDate;
        TextView newsContent;
        TextView newsWebLink;

        final int position = getIntent().getExtras().getInt("position");

        newsImageView = findViewById(R.id.news_view_image);
        newsTitle = findViewById(R.id.news_view_title);
        newsAuthor = findViewById(R.id.news_view_author_name);
        newsDate = findViewById(R.id.news_view_date);
        newsContent = findViewById(R.id.news_view_content);
        newsWebLink = findViewById(R.id.news_view_website_link);

        Picasso.get().load(MainActivity.mNews.get(position).getNewsImageURL()).fit().centerInside().into(newsImageView);
        newsTitle.setText(MainActivity.mNews.get(position).getNewsTitle());
        newsAuthor.setText(MainActivity.mNews.get(position).getNewsAuthor());
        newsDate.setText(MainActivity.mNews.get(position).getNewsTime());
        newsContent.setText(MainActivity.mNews.get(position).getNewsContent());

        newsWebLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NewsActivity.this, NewsDisplayActivity.class);
                intent.putExtra("position", position);
                startActivity(intent);
            }
        });

    }
}
