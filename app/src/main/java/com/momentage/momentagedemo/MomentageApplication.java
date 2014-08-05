package com.momentage.momentagedemo;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;
import android.view.WindowManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.momentage.momentagedemo.utils.LruBitmapCache;

public class MomentageApplication extends Application {

    public static final String TAG = MomentageApplication.class.getSimpleName();

    private RequestQueue requestQueue;
    private ImageLoader imageLoader;
    LruBitmapCache bitmapCache;

    private static MomentageApplication appInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        appInstance = this;
    }

    public static synchronized MomentageApplication getAppInstance() {
        return appInstance;
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return requestQueue;
    }

    public ImageLoader getImageLoader() {
        getRequestQueue();

        if (imageLoader == null) {
            getBitmapCache();
            imageLoader = new ImageLoader(this.requestQueue, bitmapCache);
        }

        return this.imageLoader;
    }

    private LruBitmapCache getBitmapCache() {
        if (bitmapCache == null) {
            bitmapCache = new LruBitmapCache();
        }

        return this.bitmapCache;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (requestQueue != null) {
            requestQueue.cancelAll(tag);
        }
    }

}
