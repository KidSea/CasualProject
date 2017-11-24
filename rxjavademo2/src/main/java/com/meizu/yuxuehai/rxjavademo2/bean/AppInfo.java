package com.meizu.yuxuehai.rxjavademo2.bean;


import android.support.annotation.NonNull;

public class AppInfo implements Comparable<Object>{


    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getIcon() {
        return mIcon;
    }

    public void setIcon(String icon) {
        mIcon = icon;
    }

    String mName;

    String mIcon;


    public AppInfo(String name, String icon) {
        mName = name;
        mIcon = icon;
    }

    @Override
    public int compareTo(@NonNull Object o) {
        AppInfo f = (AppInfo) o;
        return getName().compareTo(f.getName());
    }
}
