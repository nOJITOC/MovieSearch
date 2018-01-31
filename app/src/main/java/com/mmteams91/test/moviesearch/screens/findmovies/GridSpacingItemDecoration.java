package com.mmteams91.test.moviesearch.screens.findmovies;


import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

    private int spanCount;
    private int spacing;

    public GridSpacingItemDecoration(int spanCount, int spacing) {
        this.spanCount = spanCount;
        this.spacing = spacing;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view);
        int column = position % spanCount;
        if (column == 0) {
            outRect.left = spacing;
            outRect.right = spacing / 2;
        } else if (column == spanCount - 1) {
            outRect.left = spacing / 2;
            outRect.right = spacing;
        } else {
            outRect.left = spacing / 2;
            outRect.right = spacing / 2;
        }
        if (position >= spanCount) {
            outRect.top = spacing; // item top
        }
    }
}