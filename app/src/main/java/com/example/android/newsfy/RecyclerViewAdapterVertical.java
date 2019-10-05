package com.example.android.newsfy;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecyclerViewAdapterVertical extends RecyclerView.Adapter<RecyclerViewAdapterVertical.ViewHolder> {

    Listener mListener;
    private ArrayList<News> mNews;
    private Context mContext;

    public RecyclerViewAdapterVertical(Context context, ArrayList<News> news) {
        mContext = context;
        mNews = news;
    }

    public void setListener(Listener listener) {
        this.mListener = listener;
    }

    @NonNull
    @Override

    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.news_card_vertical, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        News news = mNews.get(i);
        CardView newsCard = viewHolder.newsCard;
        Picasso.get().load(news.getNewsImageURL()).fit().centerInside().into(viewHolder.newsImage);
        viewHolder.newsTitle.setText(mNews.get(i).getNewsTitle());
        viewHolder.newsSource.setText(mNews.get(i).getNewsSource());

        newsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    mListener.onClick(i);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mNews.size();
    }

    interface Listener {
        void onClick(int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView newsImage;
        private TextView newsTitle;
        private TextView newsSource;
        private CardView newsCard;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            newsImage = itemView.findViewById(R.id.news_card_vertical_news_image);
            newsTitle = itemView.findViewById(R.id.news_card_vertical_news_title);
            newsSource = itemView.findViewById(R.id.news_card_vertical_news_source);
            newsCard = itemView.findViewById(R.id.news_card_vertical);
        }
    }
}
