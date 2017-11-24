package com.meizu.yuxuehai.rxjavademo2;

import android.app.Application;
import android.content.Context;

/**
 * Created by yuxuehai on 17-11-24.
 */

public class MyApplication extends Application {
    public static Context instance;

    public MyApplication(){
        instance = this;
    }
}
