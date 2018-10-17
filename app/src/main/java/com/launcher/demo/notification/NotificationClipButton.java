package com.launcher.demo.notification;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;

public class NotificationClipButton extends android.support.v7.widget.AppCompatButton {
    public NotificationClipButton(Context context) {
        super(context);
    }

    public NotificationClipButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void getDrawingRect(Rect outRect) {
        super.getDrawingRect(outRect);
        Log.i("ClipButton", outRect.toString());
    }
}
