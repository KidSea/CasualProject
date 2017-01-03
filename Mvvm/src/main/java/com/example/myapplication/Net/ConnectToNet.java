package com.example.myapplication.Net;

import com.example.myapplication.bean.User;

/**
 * Created by yuxuehai on 16-12-30.
 */

public class ConnectToNet {


    public static boolean checkIn(User user){


        if("yuxuehai".equals(user.getName()) && "a5786776".equals(user.getPasswd())){
            return  true;
        }
        return false;
    }
}
