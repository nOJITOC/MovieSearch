package com.mmteams91.test.moviesearch.screens.findmovies;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by User New on 30.01.2018.
 */

public class LastMovieDecorator extends RecyclerView.ItemDecoration {
    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int itemSize = parent.getAdapter().getItemCount();
//        int itemPosition = parent.getChildViewHolder(view).getAdapterPosition();
//        if (itemPosition != itemSize - 1)
        super.onDraw(c, parent, state);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int itemSize = parent.getAdapter().getItemCount();
        int itemPosition = parent.getChildAdapterPosition(view);
        if(itemPosition == RecyclerView.NO_POSITION)
            return;
        if (itemPosition != itemSize - 1) super.getItemOffsets(outRect, view, parent, state);
    }
}
