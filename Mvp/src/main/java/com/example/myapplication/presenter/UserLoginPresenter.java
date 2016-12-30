package com.example.myapplication.presenter;

import android.os.SystemClock;
import android.text.TextUtils;

import com.example.myapplication.Net.ConnectToNet;
import com.example.myapplication.bean.User;

import com.example.myapplication.dao.UiLoginView;

/**
 * Created by yuxuehai on 16-12-30.
 */

public class UserLoginPresenter {

    //这样做不好，没有通用性，activity fragment
    //提高通用性，放置参数为通用（抽象类或接口，开发中多用接口）


    private UiLoginView view;


    public UserLoginPresenter(UiLoginView view){
        this.view = view;
    }


    public void login(final User user){

        new Thread(){
            @Override
            public void run() {
                super.run();
                SystemClock.sleep(2000);

                if(ConnectToNet.checkIn(user)){
                    view.success();

                }else {
                    view.fail();
                }
            }
        }.start();
    }


    public boolean checkUserInfo(User user){

        if(TextUtils.isEmpty(user.getName()) || TextUtils.isEmpty(user.getPasswd())){
            return false;
        }
        return true;
    }


}
