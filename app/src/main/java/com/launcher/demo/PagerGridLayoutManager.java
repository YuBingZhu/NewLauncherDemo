package com.launcher.demo;

import android.support.annotation.IntDef;
import android.support.annotation.IntRange;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

public class PagerGridLayoutManager extends RecyclerView.LayoutManager {

    private static final String TAG = "pb:PagerLayoutManager:";

    public static final int VERTICAL = 0;
    public static final int HORIZONTAL = 1;

    @IntDef({VERTICAL, HORIZONTAL})
    public @interface OrientationType {
    }

    @OrientationType
    private int mOrientation = HORIZONTAL;

    private int mRows;
    private int mColumns;
    private int mOnePageSize;

    /**
     * @param rows        行数
     * @param columns     列数
     * @param orientation 方向
     */
    public PagerGridLayoutManager(@IntRange(from = 1, to = 100) int rows,
                                  @IntRange(from = 1, to = 100) int columns,
                                  @OrientationType int orientation) {
        mOrientation = orientation;
        mRows = rows;
        mColumns = columns;
        mOnePageSize = mRows * mColumns;
    }

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
    }
}
