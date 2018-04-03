package com.launcher.demo;

/**
 * Created by pan on 18-4-2.
 */

public interface GridDragListener {
    /**
     * reorder the items
     *
     * @param oldPosition old
     * @param newPosition new
     */
    public void reorderItems(int oldPosition, int newPosition);


    /**
     * set the item hide
     *
     * @param position pos
     */
    public void setHideItem(int position);


    /**
     * delete the item
     *
     * @param position pos
     */
    public void deleteItem(int position);
}
