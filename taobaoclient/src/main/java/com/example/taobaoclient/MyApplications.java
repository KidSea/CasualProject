package com.example.taobaoclient;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

/**
 * Created by yuxuehai on 17-2-4.
 */

public class MyApplications extends Application {


    private static Context context;
    public static Context getContext() {
        return context;
    }

    public static Handler getHandler() {
        return handler;
    }

    public static int getMyTid() {
        return mainThreadId;
    }

    private static Handler handler;
    private static int mainThreadId;

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();

        context = getApplicationContext();
        handler = new Handler();

        mainThreadId = android.os.Process.myTid();//
    }

}
