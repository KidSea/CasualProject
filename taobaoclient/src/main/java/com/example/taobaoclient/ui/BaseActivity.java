package com.example.taobaoclient.ui;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

/**
 * Created by yuxuehai on 17-2-4.
 */

public abstract class BaseActivity extends AppCompatActivity {


    protected Context getcontext() {
        return getApplicationContext();
    }

    protected Activity getActivityContext() {
        return this;
    }

    protected abstract int getContentLayoutId();

    protected abstract void initView();

    protected abstract void initData();



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(getContentLayoutId());

        setupActionBar();

        initView();
        initData();


    }

    protected void setupActionBar(){
        if(getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
    }

}
