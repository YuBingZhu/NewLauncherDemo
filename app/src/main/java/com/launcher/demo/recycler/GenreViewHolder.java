package com.launcher.demo.recycler;

import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.launcher.demo.R;

import static android.view.animation.Animation.RELATIVE_TO_SELF;

public class GenreViewHolder extends GroupViewHolder {

    private TextView groupTitle;
    private ImageView icon;
    private TextView showAll;

    public GenreViewHolder(View itemView) {
        super(itemView);
        groupTitle = (TextView) itemView.findViewById(R.id.group_title);
        icon = (ImageView) itemView.findViewById(R.id.group_icon);
        showAll = (TextView) itemView.findViewById(R.id.show_all);

        showAll.setOnClickListener(this);
    }

    public void setGenreTitle(ExpandableGroup genre) {
        if (genre instanceof Genre) {
            groupTitle.setText(genre.getTitle());
            icon.setBackgroundResource(((Genre) genre).getIconResId());
        }
    }

    @Override
    public void expand() {
        animateExpand();
    }

    @Override
    public void collapse() {
        animateCollapse();
    }

    private void animateExpand() {
        RotateAnimation rotate =
                new RotateAnimation(360, 180, RELATIVE_TO_SELF, 0.5f, RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(300);
        rotate.setFillAfter(true);
//        arrow.setAnimation(rotate);
    }

    private void animateCollapse() {
        RotateAnimation rotate =
                new RotateAnimation(180, 360, RELATIVE_TO_SELF, 0.5f, RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(300);
        rotate.setFillAfter(true);
//        arrow.setAnimation(rotate);
    }
}
