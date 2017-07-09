package com.example.lifecycledemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class LifeCycleDemo extends AppCompatActivity {

    private static final String TAG = "LifeCycleDemo";


    //Activity创建时被调用
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate is called.");
        setContentView(R.layout.activity_main);
    }


    //Activity创建或者从后台重新回到前台时被调用
    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart is called.");
    }


    //Activity创建或者从被覆盖、后台重新回到前台时被调用
    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume is called.");
    }

    //Activity窗口获得或失去焦点时被调用,在onResume之后或onPause之后
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            Log.i(TAG, "onWindowFocusChanged is called and" + " hasFocus is true");
        } else {
            Log.i(TAG, "onWindowFocusChanged is called and" + " hasFocus is false");
        }

    }

    //Activity被覆盖到下面或者锁屏时被调用
    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause is called.");
        //有可能在执行完onPause或onStop后,系统资源紧张将Activity杀死,所以有必要在此保存持久数据
    }

    //退出当前Activity或者跳转到新Activity时被调用
    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop is called.");
    }

    //退出当前Activity时被调用,调用之后Activity就结束了
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestory is called.");
    }


    //Activity从后台重新回到前台时被调用
    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "onRestart called.");
    }
}
