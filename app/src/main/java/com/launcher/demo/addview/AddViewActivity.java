package com.launcher.demo.addview;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
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

    public void add_view(View view) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("click add view button.");

                TextView textView = new TextView(AddViewActivity.this);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                params.setMargins(100, 10, 10, 10);
                textView.setLayoutParams(params);
                textView.setTextSize(30);
                textView.setTextColor(getResources().getColor(R.color.colorPrimary));
                textView.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                textView.setText(" new added view !!!");
                container.addView(textView, 3);
            }
        });
    }

    private void showToast(String text) {
        if (toast != null) {
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.setText(text);
        }
        toast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
        toast.show();
    }
}
