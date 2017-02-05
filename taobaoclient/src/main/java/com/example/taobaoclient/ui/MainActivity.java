package com.example.taobaoclient.ui;

import android.view.View;
import android.widget.ImageView;

import com.example.taobaoclient.R;

/**
 * Created by yuxuehai on 17-2-4.
 */

public class MainActivity extends BaseActivity implements View.OnClickListener {

    // 界面底部的菜单按钮
    private ImageView[] bt_menu = new ImageView[5];
    // 界面底部的菜单按钮id
    private int[] bt_menu_id = {R.id.iv_menu_0, R.id.iv_menu_1, R.id.iv_menu_2, R.id.iv_menu_3, R.id.iv_menu_4};

    // 界面底部的选中菜单按钮资源
    private int[] select_on = {R.drawable.guide_home_on, R.drawable.guide_tfaccount_on, R.drawable.guide_discover_on, R.drawable.guide_cart_on, R.drawable.guide_account_on};
    // 界面底部的未选中菜单按钮资源
    private int[] select_off = {R.drawable.bt_menu_0_select, R.drawable.bt_menu_1_select, R.drawable.bt_menu_2_select, R.drawable.bt_menu_3_select, R.drawable.bt_menu_4_select};


    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        for (int i = 0; i < bt_menu.length; i++) {
            bt_menu[i] = (ImageView) findViewById(bt_menu_id[i]);
            bt_menu[i].setOnClickListener(this);
        }

        //设置默认选中按钮
        bt_menu[0].setImageResource(select_on[0]);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {

        int id = v.getId();

        switch (id) {
            case R.id.iv_menu_0:
                break;
            case R.id.iv_menu_1:
                break;
            case R.id.iv_menu_2:
                break;
            case R.id.iv_menu_3:
                break;
            case R.id.iv_menu_4:
                break;
        }

        // 设置按钮的选中和未选中资源
        for (int i = 0; i < bt_menu.length; i++) {
            bt_menu[i].setImageResource(select_off[i]);
            if (id == bt_menu_id[i]) {
                bt_menu[i].setImageResource(select_on[i]);
            }
        }
    }
}
