package com.example.android.newsfy;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
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
        FloatingActionButton floatingActionButton;
        final int position = getIntent().getExtras().getInt("position");
        int news = getIntent().getExtras().getInt("news");

        newsImageView = findViewById(R.id.news_view_image);
        newsTitle = findViewById(R.id.news_view_title);
        newsAuthor = findViewById(R.id.news_view_author_name);
        newsDate = findViewById(R.id.news_view_date);
        newsContent = findViewById(R.id.news_view_content);
        newsWebLink = findViewById(R.id.news_view_website_link);
        floatingActionButton = findViewById(R.id.floating_action_button);

        switch (news) {
            case 0:
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
                        intent.putExtra("news", 0);
                        startActivity(intent);
                    }
                });

                floatingActionButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent sendIntent = new Intent();
                        sendIntent.setAction(Intent.ACTION_SEND);
                        sendIntent.putExtra(Intent.EXTRA_SUBJECT, R.string.check_this_out);
                        sendIntent.putExtra(Intent.EXTRA_TEXT, MainActivity.mNews.get(position).getNewsUrl());
                        sendIntent.setType("text/plain");
                        String shareText = getString(R.string.share_via);
                        Intent chooser = Intent.createChooser(sendIntent, shareText);
                        if (sendIntent.resolveActivity(getPackageManager()) != null) {
                            startActivity(chooser);
                        }
                    }
                });
                break;
            case 1:
                Picasso.get().load(MainActivity.mNewsVertical.get(position).getNewsImageURL()).fit().centerInside().into(newsImageView);
                newsTitle.setText(MainActivity.mNewsVertical.get(position).getNewsTitle());
                newsAuthor.setText(MainActivity.mNewsVertical.get(position).getNewsAuthor());
                newsDate.setText(MainActivity.mNewsVertical.get(position).getNewsTime());
                newsContent.setText(MainActivity.mNewsVertical.get(position).getNewsContent());

                newsWebLink.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(NewsActivity.this, NewsDisplayActivity.class);
                        intent.putExtra("position", position);
                        intent.putExtra("news", 1);
                        startActivity(intent);
                    }
                });

                floatingActionButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent sendIntent = new Intent();
                        sendIntent.setAction(Intent.ACTION_SEND);
                        sendIntent.putExtra(Intent.EXTRA_SUBJECT, R.string.check_this_out);
                        sendIntent.putExtra(Intent.EXTRA_TEXT, MainActivity.mNewsVertical.get(position).getNewsUrl());
                        sendIntent.setType("text/plain");
                        String shareText = getString(R.string.share_via);
                        Intent chooser = Intent.createChooser(sendIntent, shareText);
                        if (sendIntent.resolveActivity(getPackageManager()) != null) {
                            startActivity(chooser);
                        }
                    }
                });
                break;
        }





    }
}
