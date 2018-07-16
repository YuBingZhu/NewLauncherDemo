package com.launcher.demo.recycler;

import android.view.View;
import android.widget.TextView;

import com.launcher.demo.R;


public class ArtistViewHolder extends ChildViewHolder {

    private TextView childTextView;

    public ArtistViewHolder(View itemView) {
        super(itemView);
        childTextView = (TextView) itemView.findViewById(R.id.list_item_artist_name);
    }

    public void setArtistName(String name) {
        childTextView.setText(name);
    }
}
