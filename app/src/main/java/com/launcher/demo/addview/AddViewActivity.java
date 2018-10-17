package com.launcher.demo.addview;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.launcher.demo.MainActivity;
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

        createNotificationChannel();
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setSmallIcon(R.mipmap.ic_launcher);
        mBuilder.setLargeIcon(BitmapFactory.decodeResource(this.getResources(), R.mipmap.ic_launcher));
        mBuilder.setContentTitle("这是标题");
        mBuilder.setContentText("这是正文，当前ID是：" + 1);
        mBuilder.setSubText("这是摘要");
        mBuilder.setAutoCancel(true);
        mBuilder.setContentInfo("Info");
        mBuilder.setNumber(2);
        mBuilder.setTicker("在状态栏上显示的文本");
        mBuilder.setPriority(NotificationCompat.PRIORITY_MAX);
        mBuilder.setWhen(System.currentTimeMillis() - 3600000);
        mBuilder.setOngoing(true);
        mBuilder.setDefaults(NotificationCompat.DEFAULT_SOUND);
        mBuilder.setVibrate(new long[]{0, 1000, 1000, 1000});
        mBuilder.setChannelId("newDemo");
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, 0);
        mBuilder.setContentIntent(pIntent);
        mBuilder.addAction(new NotificationCompat.Action(R.mipmap.icon, "play message", pIntent));
        mBuilder.addAction(new NotificationCompat.Action(R.mipmap.icon, "dismiss", pIntent));
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(1, mBuilder.build());

    }

    private int clickCount;

    public void add_view(View view) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("click add view button.");

                clickCount++;
                TextView textView = new TextView(AddViewActivity.this);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                params.setMargins(100, 10, 10, 10);
                textView.setLayoutParams(params);
                textView.setTextSize(30);
                textView.setTextColor(getResources().getColor(R.color.colorPrimary));
                textView.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                textView.setText(" new added view:" + clickCount);
                container.addView(textView, 3);
            }
        });
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

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "channel name";
            String description = "channel_description";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("newDemo", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public void removeView(View view) {
        showToast("click remove view button.");
        container.removeViewAt(3);
    }
}
