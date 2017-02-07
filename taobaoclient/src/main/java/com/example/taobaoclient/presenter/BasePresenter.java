package com.example.taobaoclient.presenter;

import android.content.Context;

/**
 * Created by yuxuehai on 17-2-7.
 */

public abstract class BasePresenter {
    private final Context mContext;

    public BasePresenter(Context context) {
        mContext = context.getApplicationContext();
    }

    public void destroy() {
    }

    public Context getContext() {
        return mContext;
    }
}
