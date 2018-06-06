package com.launcher.demo;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

public class MyScrollActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_scroll_layout);
    }
}
