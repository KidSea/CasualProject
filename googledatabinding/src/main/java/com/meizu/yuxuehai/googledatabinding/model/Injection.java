package com.meizu.yuxuehai.googledatabinding.model;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;

import com.meizu.yuxuehai.googledatabinding.model.local.LocalDataModule;
import com.meizu.yuxuehai.googledatabinding.model.local.ToDoDatabase;
import com.meizu.yuxuehai.googledatabinding.model.net.NetModule;
import com.meizu.yuxuehai.googledatabinding.model.remote.RemoteDataModule;
import com.meizu.yuxuehai.googledatabinding.utils.AppExecutors;

import static android.support.v4.util.Preconditions.checkNotNull;

/**
 * Created by yuxuehai on 18-1-27.
 */

public class Injection {

    @SuppressLint("RestrictedApi")
    public static DataRespository provideTasksRepository(@NonNull Context context) {
        checkNotNull(context);
        ToDoDatabase database = ToDoDatabase.getInstance(context);
        return DataRespository.getInstance(RemoteDataModule.getInstance(NetModule.getInstance()),
                LocalDataModule.getInstance(new AppExecutors(),
                        database.taskDao()));
    }
}
