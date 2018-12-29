package com.glhd.tb.app.http;

import android.util.Log;

import com.glhd.tb.app.base.BaseRes;
import com.google.gson.Gson;
import com.squareup.okhttp.Request;


import java.io.File;
import java.util.HashMap;

/**
 * Created by chenliangj2ee on 2017/8/3.
 * Object：返回数据对应的javabean
 */
/*
        MyHttp<ArrayList<Object>> http = new MyHttp<ArrayList<Object>>();
        http.put("name", "ren");
        http.ResultCallback(new MyHttp.ResultCallback<ArrayList<Object>>() {
            @Override
            public void onSuccess(ArrayList<Object> object) {

            }

            @Override
            public void onError(String message) {

            }
        });
        http.post(url);
 */

public class MyHttp<T> {
    private T result;
    private HashMap<String, String> values = new HashMap<>();
    private StringBuffer valuesBuffer = new StringBuffer();
    private ResultCallback resultListener;
    private Class<?> responseClass;
    private int requestCode;


    public MyHttp(Class<?> responseClass) {
        this.responseClass = responseClass;
        requestCode=(int)(Math.random()*1000);
    }

    public void put(String key, String value) {
        if (key == null) key = "";
        if (value == null) value = "";
        values.put(key, value);
        valuesBuffer.append("&" + key + "=" + value);
    }

    public void get(String url) {
        url += "?" + valuesBuffer.toString();
        url = url.replace("?&", "?");
        valuesBuffer = null;
        getAsyn(url);


    }

    public void post(String url) {
        getPost(url);
    }

    /**
     * 文件上传
     *
     * @param url：上传路径
     * @param responseClass：返回数据
     * @param file：文件
     * @param fileKey：文件key
     * @param params：附带参数
     */
    public void upload(String url, final Class<?> responseClass, File file, String fileKey, MyHttpManager.Param... params) {

        try {
            MyHttpManager.postAsyn(url, responseClass, new MyHttpManager.ResultCallback<T>() {
                @Override
                public void onError(Request request, Exception e) {
                    Log.i("MyHttp", requestCode+":"+e.getMessage());
                    if (resultListener != null) {
                        resultListener.onError(e.getMessage());
                    }
                    e.printStackTrace();
                }

                @Override
                public void onResponse(T object) {
                    Log.i("MyHttp", requestCode+":返回数据："+new Gson().toJson(object));
                    if (resultListener != null) {
                        if (object == null) {
                            Object o = new Gson().fromJson(new Gson().toJson(new BaseRes()), responseClass);
                            resultListener.onSuccess(o);
                        }
                        else {
                            resultListener.onSuccess(object);
                        }
                    }
                }
            }, file, fileKey, params);
        } catch (Exception e) {
            Log.i("MyHttp", requestCode+":"+e.getMessage());
            if (resultListener != null) {
                resultListener.onError(e.getMessage());
            }
            e.printStackTrace();
        }
    }


    private void getAsyn(String url) {
        Log.i("MyHttp", requestCode+":"+url);
        try {
            MyHttpManager.getAsyn(url, this.responseClass,
                    new MyHttpManager.ResultCallback<T>() {
                        @Override
                        public void onError(Request request, Exception e) {
                            Log.i("MyHttp", requestCode+":"+e.getMessage());
                            if (resultListener != null) {
                                resultListener.onError(e.getMessage());
                            }
                        }

                        @Override
                        public void onResponse(T object) {
                            Log.i("MyHttp", requestCode+":返回数据:"+new Gson().toJson(object));
                            if (resultListener != null) {
                                resultListener.onSuccess(object);
                            }

                        }
                    });
        } catch (Exception e) {
            Log.i("MyHttp", requestCode+":"+e.getMessage());
            if (resultListener != null) {
                resultListener.onError(e.getMessage());
            }
        }

    }

    private void getPost(String url) {
        Log.i("MyHttp", requestCode+":请求地址：" + url);
        Log.i("MyHttp", requestCode+":请求参数：" + new Gson().toJson(values));
        try {
            MyHttpManager.postAsyn(url, responseClass, new MyHttpManager.ResultCallback<T>() {
                @Override
                public void onError(Request request, Exception e) {

                    Log.i("MyHttp", requestCode+":onError:"+e.getMessage());
                    if (resultListener != null) {
                        resultListener.onError(e.getMessage());
                    }
                }

                @Override
                public void onResponse(T object) {
                    Log.i("MyHttp", requestCode+":返回数据:"+new Gson().toJson(object));
                    if (resultListener != null) {
                        resultListener.onSuccess(object);
                    }
                }
            }, values);
        } catch (Exception e) {
            Log.i("MyHttp", requestCode+":Exception:"+e.getMessage());
            if (resultListener != null) {
                resultListener.onError(e.getMessage());
            }
            e.printStackTrace();
        }

    }

    public void ResultCallback(ResultCallback<T> resultListener) {
        this.resultListener = resultListener;
    }

    public interface ResultCallback<T> {
        public abstract void onSuccess(T res);

        public abstract void onError(String message);
    }


}
