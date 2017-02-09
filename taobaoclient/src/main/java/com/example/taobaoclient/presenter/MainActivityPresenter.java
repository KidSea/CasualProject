package com.example.taobaoclient.presenter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.example.taobaoclient.R;
import com.example.taobaoclient.fragment.BaseFragment;
import com.example.taobaoclient.fragment.FrgmentFactory;
import com.example.taobaoclient.ui.MainActivity;

/**
 * Created by yuxuehai on 17-2-7.
 */

public class MainActivityPresenter extends BasePresenter {


    private MainActivity mMainActivity;

    public MainActivityPresenter(MainActivity context) {
        super(context);
        mMainActivity = context;
    }

    /** 添加Fragment **/
    public void addFragment(Fragment fragment) {
        FragmentTransaction ft = mMainActivity.getSupportFragmentManager().beginTransaction();
        ft.add(R.id.layout_show, fragment);
        ft.commit();
    }

    /** 删除Fragment **/
    public void removeFragment(Fragment fragment) {
        FragmentTransaction ft = mMainActivity.getSupportFragmentManager().beginTransaction();
        ft.remove(fragment);
        ft.commit();
    }

    /** 显示Fragment **/
    public void showFragment(Fragment fragment) {
        FragmentTransaction ft = mMainActivity.getSupportFragmentManager().beginTransaction();
        // 设置Fragment的切换动画
        ft.setCustomAnimations(R.anim.cu_push_right_in, R.anim.cu_push_left_out);
        // 判断页面是否已经创建，如果已经创建，那么就隐藏掉
        if (FrgmentFactory.getFragment(0) != null) {
            ft.hide(FrgmentFactory.getFragment(0));
        }
        if (FrgmentFactory.getFragment(1) != null) {
            ft.hide(FrgmentFactory.getFragment(1));
        }
        if (FrgmentFactory.getFragment(2) != null) {
            ft.hide(FrgmentFactory.getFragment(2));
        }
        if (FrgmentFactory.getFragment(3) != null) {
            ft.hide(FrgmentFactory.getFragment(3));
        }
        if (FrgmentFactory.getFragment(4) != null) {
            ft.hide(FrgmentFactory.getFragment(4));
        }
        ft.show(fragment);
        ft.commitAllowingStateLoss();

    }

    public void judgeFragment(BaseFragment fragment){

        if(fragment.isHidden()){
            showFragment(fragment);
        }
    }
}
