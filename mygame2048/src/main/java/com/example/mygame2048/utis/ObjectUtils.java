package com.example.mygame2048.utis;

import com.example.mygame2048.MainActivity;

/**
 * Created by yuxuehai on 17-1-21.
 */

public class ObjectUtils {

    public static MainActivity sMainActivity= null;

    public static void setMainActivity(MainActivity activity){
        sMainActivity = activity;
    }

    public static MainActivity getMainActivity(){
        return sMainActivity;
    }

}
