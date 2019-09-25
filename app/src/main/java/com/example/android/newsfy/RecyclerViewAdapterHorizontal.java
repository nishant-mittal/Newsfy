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

public class RecyclerViewAdapterHorizontal extends RecyclerView.Adapter<RecyclerViewAdapterHorizontal.ViewHolder> {
    private ArrayList<News> newsList = new ArrayList<>();
    private Context mContext;
    private Listener mListener;
    public RecyclerViewAdapterHorizontal(Context context,ArrayList<News> newsList) {
        this.newsList = newsList;
        mContext = context;
    }

    interface Listener {
        void onClick(int position);
    }

    public void setListener(Listener listener) {
        mListener = listener;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.news_card_horizontal,viewGroup,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        News news = newsList.get(i);
    viewHolder.newsTitle.setText(news.getNewsTitle());
    //viewHolder.newsSource.setText(news.getNewsSource());
    viewHolder.newsTime.setText(news.getNewsTime());
    Picasso.get().load(news.getNewsImageURL()).fit().centerInside().into(viewHolder.newsImageURL);
    viewHolder.newsCard.setOnClickListener(new View.OnClickListener() {
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
        return newsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView newsImageURL;
        //TextView newsSource;
        TextView newsTitle;
        TextView newsTime;
        CardView newsCard;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            newsImageURL = itemView.findViewById(R.id.news_image_view);
            //newsSource = itemView.findViewById(R.id.news_source_text_view);
            newsTitle = itemView.findViewById(R.id.news_text_view);
            newsTime = itemView.findViewById(R.id.news_time_text_view);
            newsCard = itemView.findViewById(R.id.news_card_horizontal);
        }
    }
}
