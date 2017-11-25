package com.meizu.yuxuehai.rxjavademo2.bean;

import java.util.List;


public class ApplicationsList {

    private static ApplicationsList ourInstance = new ApplicationsList();


    public List<AppInfo> getList() {
        return mList;
    }

    public void setList(List<AppInfo> list) {
        mList = list;
    }

    private List<AppInfo> mList;

    private ApplicationsList() {
    }

    public static ApplicationsList getInstance() {
        return ourInstance;
    }
}
