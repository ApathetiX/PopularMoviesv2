package com.sameet.popularmoviesv2.networkutils;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;



public class NetworkRequest {
    private static NetworkRequest sInstance;
    private RequestQueue mRequestQueue;
    private static Context sContext;

    private NetworkRequest(Context context) {
        sContext = context;
        mRequestQueue = getRequestQueue();
    }


    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(sContext.getApplicationContext());
        }
        return mRequestQueue;
    }

    public static synchronized NetworkRequest getInstance(Context context) {
        if (sContext == null) {
            sInstance = new NetworkRequest(context);
        }
        return sInstance;
    }

    public <T> void addToRequestQueue(Request<T> request) {
        mRequestQueue.add(request);
    }

}
