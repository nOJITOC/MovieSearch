package com.mmteams91.test.moviesearch.utils;

import android.content.res.Resources;
import android.util.DisplayMetrics;

/**
 * Created by User New on 31.01.2018.
 */

public final class UiUtils {
    public static int dpToPx(int dp) {
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        return (int) ((dp * metrics.density) + 0.5);
    }
}
