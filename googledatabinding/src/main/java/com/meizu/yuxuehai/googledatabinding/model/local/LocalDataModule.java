package com.meizu.yuxuehai.googledatabinding.model.local;


import com.meizu.yuxuehai.googledatabinding.model.bean.User;
import com.meizu.yuxuehai.googledatabinding.utils.AppExecutors;

/**
 * Created by yuxuehai on 18-1-27.
 */

public class LocalDataModule {

    private static volatile LocalDataModule INSTANCE;

    private TableDao mTableDaol;
    private AppExecutors mAppExecutors;

    private LocalDataModule(AppExecutors appExecutors, TableDao tableDao) {
        mTableDaol = tableDao;
        mAppExecutors = appExecutors;
    }

    public static LocalDataModule getInstance(AppExecutors appExecutors, TableDao tableDao) {
        if (INSTANCE == null) {
            synchronized (LocalDataModule.class) {
                if (INSTANCE == null) {
                    INSTANCE = new LocalDataModule(appExecutors, tableDao);
                }
            }
        }
        return INSTANCE;
    }


    public User getUser(String name) {
        return null;
    }
}

