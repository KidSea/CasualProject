package com.example.listviewdemo.net;

import java.util.ArrayList;

/**
 * Created by yuxuehai on 17-1-12.
 */

public class DateService {

    private static ArrayList<String> mDate;



    public static ArrayList<String> getDateFromNet(){
        mDate = new ArrayList<String>();

        for (int i = 0; i < 30; i++) {
            mDate.add("这是第" + i + "个item");
        }

        return mDate;
    }



}
