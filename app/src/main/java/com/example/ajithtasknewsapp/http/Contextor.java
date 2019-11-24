package com.example.ajithtasknewsapp.http;

import android.content.Context;

/**
 * Created by dw158 on 10/17/2016 AD.
 */

public class Contextor {

    private static Contextor instance;

    public static Contextor getInstance() {
        if (instance == null)
            instance = new Contextor();
        return instance;
    }

    private Context mContext;

    private Contextor() {

    }

    public void init(Context context) {
        mContext = context;
    }

    public Context getContext() {
        return mContext;
    }

}

