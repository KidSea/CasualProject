package com.meizu.yuxuehai.googlemvp.data;


import com.meizu.yuxuehai.googlemvp.data.bean.User;
import com.meizu.yuxuehai.googlemvp.data.local.LocalDataModule;
import com.meizu.yuxuehai.googlemvp.data.remote.RemoteDataModule;

/**
 * Created by yuxuehai on 18-1-27.
 */

public class DataRespository {

    private static DataRespository INSTANCE = null;
    private final LocalDataModule mLocalDataModule;
    private final RemoteDataModule mRemoteDataModule;


    private DataRespository(LocalDataModule localDataModule,
                            RemoteDataModule remoteDataModule) {
        mLocalDataModule = localDataModule;
        mRemoteDataModule = remoteDataModule;
    }

    public static DataRespository getInstance(RemoteDataModule tasksRemoteDataSource,
                                              LocalDataModule tasksLocalDataSource) {
        if (INSTANCE == null) {
            synchronized (DataRespository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new DataRespository(tasksLocalDataSource, tasksRemoteDataSource);
                }
            }
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    public User getUser(String name) {
        return mLocalDataModule.getUser(name);
    }
}
