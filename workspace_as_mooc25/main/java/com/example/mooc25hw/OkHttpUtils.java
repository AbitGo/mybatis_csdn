package com.example.mooc25hw;

import android.util.Log;

import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * create by zy on 08/22/2019
 * consult zhy OkHttpUtils
 */
public class OkHttpUtils {

    private volatile static OkHttpUtils mInstance;

    private OkHttpClient mokHttpClient;

    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    public OkHttpUtils(OkHttpClient okHttpClient) {
        if (okHttpClient == null) {
            mokHttpClient = new OkHttpClient();
        } else {
            mokHttpClient = okHttpClient;
        }
    }

    public static OkHttpUtils initClient(OkHttpClient okHttpClient) {
        if (mInstance == null) {
            synchronized (OkHttpUtils.class) {
                if (mInstance == null) {
                    mInstance = new OkHttpUtils(okHttpClient);
                }
            }
        }
        return mInstance;
    }

    public static OkHttpUtils getInstance() {
        return initClient(null);
    }

    /**
     * Get Request
     *
     * @param getUrl   request url(get)
     * @param callback callback interface by okHttp
     */
    public void getRequest(String getUrl, final Callback callback) {
        new Thread(() -> {
            Request request = new Request.Builder()
                    .url(getUrl)
                    .build();
            mokHttpClient.newCall(request).enqueue(callback);
        }).start();
    }

    /**
     * POST Request
     *
     * @param data     afferent json data we can be afferent Map List pojo...
     * @param postUrl  request url(post)
     * @param callback callback interface by okHttp
     */
    public void postRequest(final Object data, String postUrl, final Callback callback) {
        new Thread(() -> {
            String json = FastJsonUtil.ToJson(data);
            Log.d("Send Json -->",json);
            RequestBody body = RequestBody.create(JSON, json);
            Request request = new Request.Builder()
                    .url(postUrl)
                    .post(body)
                    .build();
            mokHttpClient.newCall(request).enqueue(callback);
        }).start();
    }
}
