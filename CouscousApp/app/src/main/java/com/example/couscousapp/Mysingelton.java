package com.example.couscousapp;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class Mysingelton {

    private static Mysingelton mInstance;
    private RequestQueue requestQueue;
    private static Context mCtx;

    private Mysingelton(Context context){
        mCtx = context;
        requestQueue = getRequestQueue();
    }

    public RequestQueue getRequestQueue(){
        if(requestQueue==null){
            requestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return requestQueue;
    }

    public static synchronized Mysingelton getInstance(Context context){
        if(mInstance==null){
            mInstance = new Mysingelton(context);
        }
        return mInstance;
    }

    public<T> void addToRequestque(Request<T> request) {

        requestQueue.add(request);

    }

}
