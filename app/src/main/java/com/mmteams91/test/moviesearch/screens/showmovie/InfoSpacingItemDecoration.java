package com.mmteams91.test.moviesearch.screens.showmovie;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by User New on 31.01.2018.
 */

public class InfoSpacingItemDecoration extends RecyclerView.ItemDecoration {
    int spacing;

    public InfoSpacingItemDecoration(int spacing) {
        this.spacing = spacing;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.left = spacing;
        outRect.right = spacing;
        outRect.top = spacing;
        outRect.bottom = spacing;
    }
}
