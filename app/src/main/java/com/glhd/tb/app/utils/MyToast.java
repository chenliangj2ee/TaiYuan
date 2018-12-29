package com.glhd.tb.app.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * @author chenliang
 */
public class MyToast {
    public static void showMessage(Context context, String message) {
        if (context == null || message == null || message.trim().equals(""))
            return;
        Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        toast.show();
    }

    public static void showMessage(Context context, int id) {
        if (context == null)
            return;
        Toast toast = Toast.makeText(context, id, Toast.LENGTH_SHORT);
        toast.show();
    }
}
