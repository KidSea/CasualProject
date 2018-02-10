package com.meizu.yuxuehai.googledatabinding.model.remote;


import com.meizu.yuxuehai.googledatabinding.model.net.NetModule;

/**
 * Created by yuxuehai on 18-1-27.
 */

public class RemoteDataModule {

    private static RemoteDataModule INSTANCE;
    private final NetModule mNetModule;

    private RemoteDataModule(NetModule netModule) {
        mNetModule = netModule;
    }

    public static RemoteDataModule getInstance(NetModule netModule) {
        if (INSTANCE == null) {
            synchronized (RemoteDataModule.class) {
                if (INSTANCE == null) {
                    INSTANCE = new RemoteDataModule(netModule);
                }
            }
        }
        return INSTANCE;
    }
}
