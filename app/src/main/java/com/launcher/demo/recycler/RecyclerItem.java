package com.launcher.demo.recycler;

import android.graphics.drawable.Drawable;

public class RecyclerItem {
    public String title;
    public String content;
    public Drawable icon;

    public RecyclerItem(String title, String content) {
        this.title = title;
        this.content = content;
    }

    @Override
    public String toString() {
        return "RecyclerItem{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", icon=" + icon +
                '}';
    }
}
