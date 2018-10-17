package com.launcher.demo.notification;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.launcher.demo.R;
import com.launcher.demo.addview.AddViewActivity;

import java.util.ArrayList;
import java.util.Iterator;

public class NotificationDemoActivity extends Activity implements View.OnClickListener {

    private static final String TAG = NotificationDemoActivity.class.getSimpleName() + ":";

    private NotificationCompat.Builder builder;
    private NotificationManager mNotificationManager;
    private static AlertDialog dialogM;
    int flagNum = 10;
    Rect mRect;
    private Button sendNotification;
    int uiOptions = 5894;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_main);
        sendNotification = (Button) findViewById(R.id.send_notification);
        Button sendMorLineNotification = (Button) findViewById(R.id.send_more_line_notification);
        Button sendBigPicNotification = (Button) findViewById(R.id.send_big_pic_notification);
        Button sendListNotification = (Button) findViewById(R.id.send_list_notification);
        Button sendActionNotification = (Button) findViewById(R.id.send_action_notification);
        Button sendProgressNotification = (Button) findViewById(R.id.send_progress_notification);
        Button sendMediaNotification = (Button) findViewById(R.id.send_media_notification);
        Button sendCustomerNotification = (Button) findViewById(R.id.send_customer_notification);
        Button cancelNotification = (Button) findViewById(R.id.cancle_notification);
        Button cancel_normal_notification = (Button) findViewById(R.id.cancle_normal_notification);
        Button send_customer_chromeNotification = (Button) findViewById(R.id.send_customer_chronoNotification);
        sendNotification.setOnClickListener(this);
        sendMorLineNotification.setOnClickListener(this);
        sendBigPicNotification.setOnClickListener(this);
        sendListNotification.setOnClickListener(this);
        sendActionNotification.setOnClickListener(this);
        sendProgressNotification.setOnClickListener(this);
        sendMediaNotification.setOnClickListener(this);
        sendCustomerNotification.setOnClickListener(this);
        cancelNotification.setOnClickListener(this);
        cancel_normal_notification.setOnClickListener(this);
        send_customer_chromeNotification.setOnClickListener(this);
        findViewById(R.id.test_clip_bounds).setOnClickListener(this);
        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        ImageView ovaliv = (ImageView) findViewById(R.id.ovaliv);
        ovaliv.setImageBitmap(createGoogleBrowser());
        findViewById(R.id.send_headup_notification).setOnClickListener(this);
        findViewById(R.id.send_headup_notification3).setOnClickListener(this);

    }

    public void createBuilder(String ticker, String title, String content, int smallIcon,
                              PendingIntent intent, boolean sound, boolean vibrate, boolean lights) {
        builder = new NotificationCompat.Builder(this);
        builder.setTicker(ticker);
        builder.setContentTitle(title);
        builder.setContentText(content);
        builder.setColor(-12303292);
        builder.setSmallIcon(smallIcon);
        builder.setContentIntent(intent);
        builder.setLargeIcon(createGoogleBrowser());
        builder.setWhen(System.currentTimeMillis());
        builder.setAutoCancel(true);
        int defaults = 0 | 1;
        if (vibrate) {
            defaults |= 2;
        }
        if (lights) {
            defaults |= 4;
        }
        builder.setDefaults(defaults);
    }

    private void showCustomerNotification(int flag) {
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                (int) SystemClock.uptimeMillis(),
                new Intent(this, AddViewActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);
        createBuilder("Customer", "自定义通知", "showCustomerNotification",
                R.mipmap.help_ic_car_guide, pendingIntent, false, false, false);
        RemoteViews contentView = new RemoteViews(getPackageName(), R.layout.remoteview);
        contentView.setTextViewText(R.id.share_content, "自定义的view");
        contentView.setOnClickPendingIntent(R.id.share_facebook, pendingIntent);
        contentView.setOnClickPendingIntent(R.id.share_twitter, pendingIntent);
        RemoteViews bigContentView = new RemoteViews(getPackageName(), R.layout.bigcontentview);
        bigContentView.setTextViewText(R.id.share_content, "自定义的bigView");
        bigContentView.setOnClickPendingIntent(R.id.share_facebook_big, pendingIntent);
        bigContentView.setOnClickPendingIntent(R.id.share_twitter_big, pendingIntent);
        builder.setPriority(1);
        Notification notification = builder.build();
        notification.contentView = contentView;
        notification.bigContentView = bigContentView;
        RemoteViews headUpContentView;
        if (flag == 19) {
            headUpContentView = new RemoteViews(getPackageName(), R.layout.headupcontentview);
            headUpContentView.setTextViewText(R.id.share_content, "自定义的headUp");
            headUpContentView.setOnClickPendingIntent(R.id.share_facebook_big, pendingIntent);
            headUpContentView.setOnClickPendingIntent(R.id.share_twitter_big, pendingIntent);
            notification.headsUpContentView = headUpContentView;
            notification.flags |= 32;
        } else if (flag == 3) {
            headUpContentView = new RemoteViews(getPackageName(), R.layout.headupcontentview2);
            headUpContentView.setTextViewText(R.id.share_content, "666773653653333");
            headUpContentView.setOnClickPendingIntent(R.id.share_facebook_big, pendingIntent);
            headUpContentView.setOnClickPendingIntent(R.id.share_twitter_big, pendingIntent);
            notification.headsUpContentView = headUpContentView;
        }
        send(flag, notification);
    }

    private void showDefaultNotification() {
        createBuilder("DefaultNotification", "普通通知", "DefaultNotification",
                R.mipmap.help_ic_car_guide,
                PendingIntent.getActivity(this, (int) SystemClock.uptimeMillis(),
                        new Intent(this, AddViewActivity.class), PendingIntent.FLAG_UPDATE_CURRENT),
                false, false, false);
        builder.setPriority(2);
        builder.setAutoCancel(false);
        send(1, builder.build());
    }

    private void showMoreLineNotification() {
        createBuilder("MoreLine", "多行通知", "showMoreLineNotification",
                R.mipmap.help_ic_car_guide, PendingIntent.getActivity(this,
                        (int) SystemClock.uptimeMillis(),
                        new Intent(this, AddViewActivity.class),
                        PendingIntent.FLAG_UPDATE_CURRENT), false, false, false);
        Notification notification = new NotificationCompat.BigTextStyle(builder)
                .bigText("观察者网综合：韩国被弹劾总统朴槿惠近日可谓麻烦不断，韩媒17日曝出她在2005年曾向时任朝鲜" +
                        "国防委员会委员长金正日写信，希望能够促进一些朝韩交流事项。虽然信中内容在当年的情形下并不无妥，" +
                        "但由于在信中语气谦卑，这在当前朝韩半岛局势紧张的背景下又一次拉低了民众对她的印象. 韩国" +
                        "《京乡新闻》12月17日报道称，时任大国家党（现新世界党）党首、欧洲韩国财团理事的朴槿惠，" +
                        "在2005年7月13日向当时的朝鲜国防委员会委员长金正日发送信函。在信中，朴槿惠希望可以尽快落实" +
                        "自己在2002年访朝时敲定的一些朝韩交流事项。为此朴槿惠希望朝鲜方面可以尽快完成欧洲韩国财团在" +
                        "朝办事处的建设，并允许欧洲韩国财团相关人员可以自由访朝。").build();
        notification.flags |= 32;
        send(2, notification);
    }

    private void showBigPicNotification() {
        createBuilder("BigPic", "大图通知", "showBigPicNotification",
                R.mipmap.help_ic_car_guide, PendingIntent.getActivity(this,
                        (int) SystemClock.uptimeMillis(),
                        new Intent(this, AddViewActivity.class),
                        PendingIntent.FLAG_UPDATE_CURRENT),
                false, false, false);
        Notification notification = new NotificationCompat.BigPictureStyle(builder)
                .bigLargeIcon(BitmapFactory.decodeResource(getResources(),
                        R.mipmap.weather_ic_notice_blue))
                .bigPicture(BitmapFactory.decodeResource(getResources(), R.mipmap.big_bitmap)).build();
        notification.flags |= 32;
        send(3, notification);
    }

    private void showListNotification() {
        createBuilder("ListNotification", "列表型通知", "showListNotification",
                R.mipmap.help_ic_car_guide, PendingIntent.getActivity(this,
                        (int) SystemClock.uptimeMillis(),
                        new Intent(this, AddViewActivity.class),
                        PendingIntent.FLAG_UPDATE_CURRENT),
                false, false, false);
        NotificationCompat.InboxStyle style = new NotificationCompat.InboxStyle(builder);
        ArrayList<String> contents = new ArrayList();
        contents.add("第一条消息");
        contents.add("第二条消息");
        contents.add("第三条消息");
        contents.add("第四条消息");
        contents.add("第五条消息");
        Iterator it = contents.iterator();
        while (it.hasNext()) {
            style.addLine((String) it.next());
        }
        style.setSummaryText(contents.size() + "条消息");
        style.setBigContentTitle("bigContentTitle");
        Notification notification = builder.build();
        notification.flags |= 32;
        send(4, notification);
    }

    private void showActionNotification() {
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                (int) SystemClock.uptimeMillis(),
                new Intent(this, AddViewActivity.class),
                PendingIntent.FLAG_UPDATE_CURRENT);
        createBuilder("ActionNotification", "带按钮的通知", "ActionNotification",
                R.mipmap.help_ic_car_guide, pendingIntent, false, false, false);
        builder.addAction(R.mipmap.help_ic_delete_p, "第一个按钮", pendingIntent);
        send(5, builder.build());
    }

    private void showProgressNotification() {
        createBuilder("Progress", "带进度条的通知", "showProgressNotification",
                R.mipmap.help_ic_car_guide, PendingIntent.getActivity(this,
                        (int) SystemClock.uptimeMillis(),
                        new Intent(this, AddViewActivity.class),
                        PendingIntent.FLAG_UPDATE_CURRENT), false, false, false);
        final NotificationCompat.Builder progressBuilder = builder;
        new Thread(new Runnable() {
            public void run() {
                for (int i = 0; i <= 100; i++) {
                    progressBuilder.setProgress(100, i, false);
//                    NotificationDemoActivity.send(6, progressBuilder.build());
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                progressBuilder.setContentText("下载完成").setProgress(0, 0, false);
//                NotificationDemoActivity.send(6, progressBuilder.build());
            }
        }).start();
    }

    private void showMediaNotification() {
        createBuilder("Media", "媒体通知", "showMediaNotification",
                R.mipmap.help_ic_car_guide, PendingIntent.getActivity(this,
                        (int) SystemClock.uptimeMillis(),
                        new Intent(this, AddViewActivity.class),
                        PendingIntent.FLAG_UPDATE_CURRENT), false, false, false);
        builder.addAction(generateAction(17301541, "Previous", "action_previous"));
        builder.addAction(generateAction(17301542, "Rewind", "action_rewind"));
        builder.addAction(generateAction(17301540, "Play", "action_play"));
        builder.addAction(generateAction(17301537, "Fast Foward", "action_fast_foward"));
        builder.addAction(generateAction(17301538, "Next", "action_next"));
        send(7, builder.build());
    }

    private NotificationCompat.Action generateAction(int icon, String title, String intentAction) {
        Intent intent = new Intent(this, AddViewActivity.class);
        intent.setAction(intentAction);
        return new NotificationCompat.Action(icon, title, PendingIntent.getActivity(this,
                (int) SystemClock.uptimeMillis(), intent, PendingIntent.FLAG_UPDATE_CURRENT));
    }

    private void showChronometerNotification() {
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                (int) SystemClock.uptimeMillis(),
                new Intent(this, AddViewActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);
        createBuilder("Action", "Chronometer", "showChronometerNotification",
                R.mipmap.help_ic_car_guide, pendingIntent, false, false, false);
        builder.addAction(R.mipmap.ic_launcher, "菜单1", pendingIntent);
        send(9, builder.build());
        Toast.makeText(this, "myClick", Toast.LENGTH_LONG).show();
    }

    private void send(int id, Notification notification) {
        mNotificationManager.notify(id, notification);
    }

    private void cancelNotification() {
        mNotificationManager.cancelAll();
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.send_notification:
                showDefaultNotification();
                return;
            case R.id.send_more_line_notification:
                showMoreLineNotification();
                return;
            case R.id.send_big_pic_notification:
                showBigPicNotification();
                return;
            case R.id.send_list_notification:
                showListNotification();
                return;
            case R.id.send_action_notification:
                showActionNotification();
                return;
            case R.id.send_progress_notification:
                showProgressNotification();
                return;
            case R.id.send_media_notification:
                showMediaNotification();
                return;
            case R.id.send_customer_notification:
                showCustomerNotification(8);
                return;
            case R.id.send_headup_notification:
                showCustomerNotification(19);
                return;
            case R.id.send_headup_notification3:
                showCustomerNotification(3);
                return;
            case R.id.send_customer_chronoNotification:
                showChronometerNotification();
                return;
            case R.id.cancle_notification:
                cancelNotification();
                return;
            case R.id.cancle_normal_notification:
                mNotificationManager.cancel(1);
                return;
            case R.id.test_clip_bounds:
                test_clip_bounds(v);
                return;
            default:
                return;
        }
    }

    public void test_clip_bounds(View view) {
        view.getDrawingRect(new Rect());
        flagNum += 10;
        if (mRect == null) {
            mRect = new Rect();
        }
        if (flagNum > 50) {
            flagNum = 0;
        }
        mRect.set(flagNum, flagNum, view.getWidth() - flagNum, view.getHeight() - flagNum);
        view.setClipBounds(mRect);
        Log.i(TAG, "" + view.getWidth() + ":" + view.getHeight());
        dialogM = new AlertDialog.Builder(this).setTitle("title").setCancelable(true)
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        NotificationDemoActivity.dialogM.dismiss();
                    }
                }).create();
        WindowManager.LayoutParams layoutParams = dialogM.getWindow().getAttributes();
        layoutParams.flags |= 8;
        dialogM.show();
        dialogM.getWindow().getDecorView().setSystemUiVisibility(uiOptions);
        sendNotification.postDelayed(new Runnable() {
            public void run() {
                try {
                    WindowManager.LayoutParams layoutParams = NotificationDemoActivity
                            .dialogM.getWindow().getAttributes();
                    layoutParams.flags &= -9;
//                    NotificationDemoActivity.getWindowManager().updateViewLayout(
//                            NotificationDemoActivity.dialogM.getWindow().getDecorView(), layoutParams);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 3000);
    }

    private Bitmap createGoogleBrowser() {
        OvalShape ovalShape = new OvalShape();
        int value = (int) getResources().getDimension(R.dimen.notification_large_icon_width);
        ovalShape.resize((float) value, (float) value);
        Paint paint = new Paint();
        paint.setColor(-10453621);
        Bitmap localBitmap = Bitmap.createBitmap(value, value, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(localBitmap);
        ovalShape.draw(canvas, paint);
        canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.offline_pin),
                (float) (value / 2), (float) (value / 2), null);
        return localBitmap;
    }
}
