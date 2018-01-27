package com.meizu.yuxuehai.googlemvp.data.net;

import com.meizu.yuxuehai.googlemvp.data.bean.User;
import com.meizu.yuxuehai.googlemvp.data.local.LocalDataModule;
import com.meizu.yuxuehai.googlemvp.utils.AppExecutors;

/**
 * Created by yuxuehai on 18-1-27.
 */

public class NetModule {

    private static volatile NetModule INSTANCE;

    private AppExecutors mAppExecutors;

    private NetModule(AppExecutors appExecutors) {
        mAppExecutors = appExecutors;
    }

    public static NetModule getInstance() {
        if (INSTANCE == null) {
            synchronized (LocalDataModule.class) {
                if (INSTANCE == null) {
                    INSTANCE = new NetModule(new AppExecutors());
                }
            }
        }
        return INSTANCE;
    }

    public boolean checkUser(User user) {
        if (user.getName().equals("yuxuehai") && user.getPasswd().equals("123456")){
            return true;
        }else {
            return false;
        }
    }
}
