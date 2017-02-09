package com.example.taobaoclient.fragment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.taobaoclient.R;
import com.example.taobaoclient.adapter.MyGridViewAdapter;
import com.example.taobaoclient.widge.AbSlidingPlayView;
import com.example.taobaoclient.widge.MyGridView;

import java.util.ArrayList;

/**
 * Created by yuxuehai on 17-2-5.
 */

public class HomeFragment extends BaseFragment {

    //分类九宫格资源图片
    private int[] pic_path_classify = { R.drawable.menu_guide_1, R.drawable.menu_guide_2, R.drawable.menu_guide_3, R.drawable.menu_guide_4, R.drawable.menu_guide_5, R.drawable.menu_guide_6, R.drawable.menu_guide_7, R.drawable.menu_guide_8 };
    // 热门市场的资源文件
    private int[] pic_path_hot = { R.drawable.menu_1, R.drawable.menu_2, R.drawable.menu_3, R.drawable.menu_4, R.drawable.menu_5, R.drawable.menu_6 };
    /**首页轮播的界面的资源*/
    private int[] resId = { R.drawable.show_m1, R.drawable.menu_viewpager_1, R.drawable.menu_viewpager_2, R.drawable.menu_viewpager_3, R.drawable.menu_viewpager_4, R.drawable.menu_viewpager_5 };

    private ImageView mScanner;
    private ImageView mMessage;
    private TextView mSearch;
    private AbSlidingPlayView mPagerMenu;
    private MyGridView mMyGridView;
    private GridView mMyHotGridView;
    private MyGridViewAdapter mMyGridViewAdapter;
    private MyGridViewAdapter mMyHotGridViewAdapter;
    private ArrayList<View> mImageViews;

    @Override
    protected View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, null);
    }

    @Override
    protected void initView(View rootView) {
        mScanner = (ImageView) rootView.findViewById(R.id.im_scanner);
        mScanner.setOnClickListener(this);
        mMessage = (ImageView) rootView.findViewById(R.id.im_message);
        mScanner.setOnClickListener(this);
        mSearch = (TextView) rootView.findViewById(R.id.tv_search);
        mSearch.setOnClickListener(this);

        mMyGridView = (MyGridView) rootView.findViewById(R.id.my_gridview);
        mMyHotGridView = (GridView) rootView.findViewById(R.id.my_gridview_hot);
        mMyGridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
        mMyHotGridView.setSelector(new ColorDrawable(Color.TRANSPARENT));

        mMyGridViewAdapter = new MyGridViewAdapter(getActivity(),pic_path_classify);
        mMyHotGridViewAdapter = new MyGridViewAdapter(getActivity(),pic_path_hot);

        mMyGridView.setAdapter(mMyGridViewAdapter);
        mMyHotGridView.setAdapter(mMyHotGridViewAdapter);


        mPagerMenu = (AbSlidingPlayView) rootView.findViewById(R.id.viewPager_menu);
        //设置播放顺序
        mPagerMenu.setPlayType(1);
        //设置播放时间间隔
        mPagerMenu.setSleepTime(3000);

        initAd();
    }

    private void initAd() {
        if(mImageViews != null){
            mImageViews.clear();
            mImageViews = null;
        }

        mImageViews = new ArrayList<View>();

        for (int i = 0; i < resId.length; i++) {
            //导入ViewPager的布局
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.pic_item, null);
            ImageView imageView = (ImageView) view.findViewById(R.id.pic_item);
            imageView.setImageResource(resId[i]);
            mImageViews.add(view);
        }

        mPagerMenu.addViews(mImageViews);
        //开始轮播
        mPagerMenu.startPlay();
    }


    @Override
    protected void initData() {

    }

    @Override
    protected void load() {

    }

    @Override
    public void onClick(View v) {

    }
}
