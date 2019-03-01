package com.glhd.tb.app.utils;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

import com.glhd.tb.app.base.bean.BeanSpinner;
import com.glhd.tb.app.base.bean.BeanUser;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author chenliang
 */
public class MySp {

    private static SharedPreferences preferences;
    private static String cacheName = "CacheBase_1.0";

    public static void putString(final Context context, final String key, final String value) {
        Log.i("putString", key);
        if (context == null)
            return;
        new Thread(new Runnable() {
            @Override
            public void run() {
                preferences = context.getSharedPreferences(cacheName,
                        Context.MODE_PRIVATE);
                Editor editor = preferences.edit();
                editor.putString(key, value);
                Log.i("AppSettingsUtil", "putString " + key + ":" + value);
                editor.commit();
            }
        }).start();

    }

    public static void putBoolean(Context context, String key, boolean value) {
        if (context == null)
            return;
        preferences = context.getSharedPreferences(cacheName,
                Context.MODE_PRIVATE);
        Editor editor = preferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static String getString(Context context, String key) {
        if (context == null)
            return null;
        preferences = context.getSharedPreferences(cacheName,
                Context.MODE_PRIVATE);
        String result = preferences.getString(key, "");
        return result;
    }

    public static void putInt(Context context, String key, int value) {
        if (context == null)
            return;
        preferences = context.getSharedPreferences(cacheName,
                Context.MODE_PRIVATE);
        Editor editor = preferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public static int getInt(Context context, String key) {
        if (context == null)
            return 0;
        preferences = context.getSharedPreferences(cacheName,
                Context.MODE_PRIVATE);
        return preferences.getInt(key, -1);
    }

    public static void putLong(Context context, String key, long value) {
        if (context == null)
            return;
        preferences = context.getSharedPreferences(cacheName,
                Context.MODE_PRIVATE);
        Editor editor = preferences.edit();
        editor.putLong(key, value);
        editor.commit();
    }

    public static long getLong(Context context, String key) {
        if (context == null)
            return 0;
        preferences = context.getSharedPreferences(cacheName,
                Context.MODE_PRIVATE);
        return preferences.getLong(key, 0);
    }

    public static boolean getBoolean(Context context, String key) {
        if (context == null)
            return true;
        preferences = context.getSharedPreferences(cacheName,
                Context.MODE_PRIVATE);
        return preferences.getBoolean(key, false);
    }

    public static void clearCache(Context context) {
        if (context == null)
            return;
        preferences = context.getSharedPreferences(cacheName,
                Context.MODE_PRIVATE);
        Editor e = preferences.edit();
        e.clear();
        e.commit();
    }


    public static void setLogin(Context context, boolean boo) {
        putBoolean(context, "setLogin", boo);
    }

    public static boolean isLogin(Context context) {
        return getBoolean(context, "setLogin");

    }

    public static void setUser(Context con, BeanUser data) {
        if(data==null){
            putString(con, "setUser",null);
            return ;
        }
        putString(con, "setUser", new Gson().toJson(data));
    }

    public static BeanUser getUser(Context con) {
        String userJson = getString(con, "setUser");
        return new Gson().fromJson(userJson, BeanUser.class);
    }

    public static String getUserId(Context con){
        return getUser(con).getAccountId();
    }
    public static void setUsers(Context con, ArrayList<BeanUser> data) {
        putString(con, "setUsers", new Gson().toJson(data));
    }

    public static ArrayList<BeanUser> getUses(Context con) {
        Type type = new TypeToken<ArrayList<BeanUser>>() {
        }.getType();
        return new Gson().fromJson(getString(con, "setUsers"), type);
    }

    public static void setSearchHistory(Context con, ArrayList<ArrayList<BeanSpinner>> data) {
        putString(con, "setSearchHistory", new Gson().toJson(data));
    }

    public static ArrayList<ArrayList<BeanSpinner>> getSearchHistory(Context con) {
        Type type = new TypeToken<ArrayList<ArrayList<BeanSpinner>>>() {
        }.getType();
        return new Gson().fromJson(getString(con, "setSearchHistory"), type);
    }

}
