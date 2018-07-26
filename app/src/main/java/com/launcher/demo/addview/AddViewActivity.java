package com.launcher.demo.addview;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.launcher.demo.R;

public class AddViewActivity extends Activity {

    private static final String TAG = AddViewActivity.class.getSimpleName() + ":";
    private Toast toast;
    LinearLayout container;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, " onCreate.");

        setContentView(R.layout.add_view_container);
        container = findViewById(R.id.view_container);

        TextView textView;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(10, 10, 10, 10);
        for (int i = 0; i < 10; i++) {
            textView = new TextView(this);
            textView.setLayoutParams(params);
            textView.setTextSize(30);
            textView.setTextColor(getResources().getColor(R.color.colorPrimary));
            textView.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            textView.setText("new launcher demo : " + i);
            container.addView(textView);
        }

    }

    private boolean hasAddView = false;

    public void addView(View view) {
        showToast("click add view button.");
        if (hasAddView) {
            container.removeViews(3, 5);
            hasAddView = false;
            return;
        }
        TextView textView;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(100, 10, 10, 10);
        for (int i = 0; i < 5; i++) {
            textView = new TextView(this);
            textView.setLayoutParams(params);
            textView.setTextSize(30);
            textView.setTextColor(getResources().getColor(R.color.colorPrimary));
            textView.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            textView.setText(" new added view " + i);
            textView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    switch (motionEvent.getAction()) {
                        case MotionEvent.ACTION_MOVE:
                            showToast(" touch to move item");
                            container.removeView(view);
                            break;
                    }
                    return true;
                }
            });
            container.addView(textView, 3);
        }
        hasAddView = true;

    }

    private void showToast(String text) {
        if (toast != null) {
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.setText(text);
        } else {
            toast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
        }
        toast.show();
    }

    public void removeView(View view) {
        showToast("click remove view button.");
        container.removeViewAt(3);
    }
}
