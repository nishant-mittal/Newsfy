package com.example.android.newsfy;

import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class VerticalSpaceItemDecorator extends RecyclerView.ItemDecoration {

    int bottomSpacing;

    public VerticalSpaceItemDecorator(int bottomSpacing) {
        this.bottomSpacing = bottomSpacing;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        outRect.bottom = bottomSpacing;
    }
}
