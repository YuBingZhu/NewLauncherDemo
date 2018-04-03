package com.launcher.demo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Created by pan on 18-4-2.
 */

public class GridAdapter extends BaseAdapter implements GridDragListener {

    private static final String TAG = Constants.LOG_TAG + GridAdapter.class.getSimpleName() + ":";

    private List<HashMap<String, Object>> mList;
    private Context mContext;
    private int mHidePosition = -1;

    public GridAdapter(Context context, List<HashMap<String, Object>> list) {
        mList = list;
        mContext = context;
    }

    @Override
    public int getCount() {
        if (mList != null) {
            return mList.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(R.layout.grid_item, null);

        ImageView mImageView = convertView.findViewById(R.id.item_image);
        TextView mTextView = convertView.findViewById(R.id.item_text);

        mImageView.setImageResource((Integer) mList.get(position).get("item_image"));
        mTextView.setText((CharSequence) mList.get(position).get("item_text"));

        if (position == mHidePosition) {
            convertView.setVisibility(View.INVISIBLE);
        }
        return convertView;
    }

    @Override
    public void reorderItems(int oldPosition, int newPosition) {
        HashMap<String, Object> temp = mList.get(oldPosition);
        if (oldPosition < newPosition) {
            for (int i = oldPosition; i < newPosition; i++) {
                Collections.swap(mList, i, i + 1);
            }
        } else if (oldPosition > newPosition) {
            for (int i = oldPosition; i > newPosition; i--) {
                Collections.swap(mList, i, i - 1);
            }
        }
        mList.set(newPosition, temp);
    }

    @Override
    public void setHideItem(int position) {
        mHidePosition = position;
        notifyDataSetChanged();
    }

    @Override
    public void deleteItem(int position) {
        mList.remove(position);
        notifyDataSetChanged();
    }
}
