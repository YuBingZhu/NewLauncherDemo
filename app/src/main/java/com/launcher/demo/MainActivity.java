package com.launcher.demo;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private static final String TAG = Constants.LOG_TAG + MainActivity.class.getSimpleName() + ":";
    private final int REQ_PERMISSION_CODE = 1;

    private static final int VISIBIE_NUMS = 24;

    private List<HashMap<String, Object>> dataSourceList = new ArrayList<HashMap<String, Object>>();

    private GridAdapter mGridAdapter;

    private Toast mToast;
    private PopupWindow mPopupWindow;
    private WindowManager mWindowManager;

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        //hide navigation bar
//        getWindow().getDecorView().setSystemUiVisibility(
//                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
//                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//        );

        MyGridView mDragGridView = findViewById(R.id.dragGridView);
        mDragGridView.setOnItemClickListener(this);

        for (int i = 0; i < VISIBIE_NUMS; i++) {
            HashMap<String, Object> itemHashMap = new HashMap<String, Object>();
            Random random = new Random();


            if (random.nextInt(3) == 1) {
                itemHashMap.put("item_image", R.mipmap.ic_launcher);
            }

            if (random.nextInt(3) == 0) {
                itemHashMap.put("item_image", R.mipmap.icon);
            } else {
                itemHashMap.put("item_image", R.mipmap.ic_launcher_round);
            }
            itemHashMap.put("item_text", "icon" + Integer.toString(i));
            dataSourceList.add(itemHashMap);
        }

        mGridAdapter = new GridAdapter(this, dataSourceList);

        mDragGridView.setAdapter(mGridAdapter);
        //设置需要抖动
        mDragGridView.setNeedShake(true);

        if (!Settings.canDrawOverlays(MainActivity.this)) {
            showToast("can not DrawOverlays");
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION
                    , Uri.parse("package:" + MainActivity.this.getPackageName()));
            startActivityForResult(intent, REQ_PERMISSION_CODE);
        }

        mWindowManager = (WindowManager)

                getSystemService(WINDOW_SERVICE);

        Button addWindow = findViewById(R.id.btn_add_window);
        addWindow.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View view) {
                //create and add the popupWindow
                addViewToWindow();
            }
        });
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQ_PERMISSION_CODE) {
            if (!Settings.canDrawOverlays(this)) {
                showToast("Permission Denied by user.Please Check it in Settings");
            } else {
                showToast("Permission Allowed");
            }
        }
    }

    private void addPopupWindow() {
        showToast(getString(R.string.btn_add_window));
        View background = View.inflate(this, R.layout.popup_window_layout, null);
        if (mPopupWindow == null) {
            mPopupWindow = new PopupWindow(background);
        }

        View view = findViewById(R.id.btn_add_window);
        int screenHeight = getResources().getDisplayMetrics().heightPixels;
        int screenWidth = getResources().getDisplayMetrics().widthPixels;
        mPopupWindow.setHeight(screenHeight);
        mPopupWindow.setWidth(screenWidth);
        mPopupWindow.setClippingEnabled(false);
        mPopupWindow.showAtLocation(view, Gravity.TOP | Gravity.START, 100, 100);

    }


    public void addViewToWindow() {
//        Log.d(TAG, "addViewToWindow");
        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                        | WindowManager.LayoutParams.FLAG_FULLSCREEN
                        | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
                        | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN,
                PixelFormat.TRANSLUCENT);

        DisplayMetrics metrics = new DisplayMetrics();
        mWindowManager.getDefaultDisplay().getRealMetrics(metrics);

        int screenWidth = metrics.widthPixels;
        int screenHeight = metrics.heightPixels;
        Log.d(TAG, " screenWidth= " + screenWidth + " --- screenHeight= " + screenHeight);

        params.gravity = Gravity.TOP | Gravity.LEFT;
        params.x = 0;
        params.y = getNavigationBarHeight();
        params.width = screenWidth;
        params.height = screenHeight;

        View view = new View(this);
        view.setLayoutParams(params);
        view.setBackgroundColor(Color.BLUE);

        mWindowManager.addView(view, params);
    }


    private void showToast(String text) {
        if (mToast == null) {
            mToast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(text);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        mToast.show();
    }

    private int getNavigationBarHeight() {
        return 200;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        showToast("onClick item : " + position);
    }
}
