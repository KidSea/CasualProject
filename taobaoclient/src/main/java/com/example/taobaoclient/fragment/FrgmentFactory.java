package com.example.taobaoclient.fragment;

import java.util.HashMap;

/**
 * Created by yuxuehai on 17-2-5.
 */

public class FrgmentFactory {

    private static HashMap<Integer, BaseFragment> mFragmentMap = new HashMap<Integer, BaseFragment>();


    public static BaseFragment getFragment(int position){
        BaseFragment fragment = mFragmentMap.get(position);


        //先从集合中取，如果没有才创建对象，可以提高性能
        if (fragment == null) {

            switch (position) {
                case 0:
                    fragment = new HomeFragment();
                    break;
                case 1:
                    fragment = new AmoyFragment();
                    break;
                case 2:
                    fragment = new FindFragment();
                    break;
                case 3:
                    fragment = new BuyCarFragment();
                    break;
                case 4:
                    fragment = new OwnerFragment();
                default:
                    break;
            }

            mFragmentMap.put(position, fragment);
        }


        return fragment;
    }
}
