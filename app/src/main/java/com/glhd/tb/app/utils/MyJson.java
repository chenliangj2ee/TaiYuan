package com.glhd.tb.app.utils;

import com.google.gson.Gson;

/**
 * Created by chenliangj2ee on 2017/3/23.
 */

public class MyJson {


    public static String toJson(Object o){
        if(o==null)
            return "";
        Gson gson=new Gson();
        return gson.toJson(o);
    }

    public static <T> T  fromJson(String json, Class<T> classOfT){
        if(json==null)
            return null;
        Gson gson=new Gson();
        try {
            return gson.fromJson(json,classOfT);
        }catch (Exception JsonSyntaxException){
            JsonSyntaxException.printStackTrace();
            return null;
        }finally {

        }

    }
}
