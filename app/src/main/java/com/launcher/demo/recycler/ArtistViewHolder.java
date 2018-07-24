package com.launcher.demo.recycler;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.launcher.demo.R;


public class ArtistViewHolder extends ChildViewHolder {

    private TextView title;
    private ImageView icon;
    private TextView content;

    public ArtistViewHolder(View itemView) {
        super(itemView);
        title = (TextView) itemView.findViewById(R.id.title);
        content = (TextView) itemView.findViewById(R.id.content);
        icon = (ImageView) itemView.findViewById(R.id.icon);
    }

    public void setArtistName(String name) {
        title.setText(name);
        content.setText(" content: " + name);
    }
}
