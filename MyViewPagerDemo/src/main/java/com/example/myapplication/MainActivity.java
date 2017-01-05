package com.example.myapplication;

import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.fragment.OneFragment;
import com.example.myapplication.fragment.TwoFragmnet;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    /**
     * 页卡总数
     **/
    private static final int pageSize = 3;

    private int selectedColor;
    private int unSelectedColor;
    private ImageView mIm_cursor;
    private int mBmpW;
    private TextView tv_voice;
    private TextView tv_health;
    private TextView tv_baby;
    private ViewPager mViewPager;
    private List<Fragment> mFragments;
    private int mOffset = 0;
    private int currIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {

        selectedColor = getResources()
                .getColor(R.color.tab_title_pressed_color);
        unSelectedColor = getResources().getColor(
                R.color.tab_title_normal_color);

        initImageView();
        initTextView();
        initViewPager();
    }

    private void initViewPager() {

        mViewPager = (ViewPager) findViewById(R.id.vPger_center);
        mFragments = new ArrayList<Fragment>();
        mFragments.add(new OneFragment());
        mFragments.add(new TwoFragmnet());
        mFragments.add(new TwoFragmnet());
        mViewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager(),
                mFragments));
        mViewPager.setCurrentItem(0);
        mViewPager.setOnPageChangeListener(new MyOnPageChangeListener());

    }

    private void initTextView() {
        tv_voice = (TextView) findViewById(R.id.text_tab1);
        tv_health = (TextView) findViewById(R.id.text_tab2);
        tv_baby = (TextView) findViewById(R.id.text_tab3);

        tv_voice.setTextColor(selectedColor);
        tv_health.setTextColor(unSelectedColor);
        tv_baby.setTextColor(unSelectedColor);

        tv_voice.setText("语音回答");
        tv_health.setText("健康百科");
        tv_baby.setText("育儿中心");

        tv_voice.setOnClickListener(new MyOnclickListener(0));
        tv_health.setOnClickListener(new MyOnclickListener(1));
        tv_baby.setOnClickListener(new MyOnclickListener(2));
    }

    /**
     * 初始化动画，这个就是页卡滑动时，下面的横线也滑动的效果，在这里需要计算一些数据
     */

    private void initImageView() {

        mIm_cursor = (ImageView) findViewById(R.id.image_cursor);
        //获取图片宽度
        mBmpW = BitmapFactory.decodeResource(getResources(),
                R.drawable.tab_selected_bg).getWidth();
        //展示属性
        DisplayMetrics metrics = new DisplayMetrics();

        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        // 获取分辨率宽度
        int screenw = metrics.widthPixels;
        // 计算偏移量 (屏幕宽度/页卡总数-图片实际宽度)/2 = 偏移量
        mOffset = (screenw / pageSize - mBmpW) / 2;

        Matrix matrix = new Matrix();
        matrix.postTranslate(mOffset, 0);
        // 设置动画初始位置
        mIm_cursor.setImageMatrix(matrix);

    }

    //定义点击事件

    class MyOnclickListener implements View.OnClickListener {

        private int index = 0;

        public MyOnclickListener(int index) {
            this.index = index;
        }


        @Override
        public void onClick(View v) {
            switch (index) {
                case 0:
                    tv_voice.setTextColor(selectedColor);
                    tv_baby.setTextColor(unSelectedColor);
                    tv_health.setTextColor(unSelectedColor);
                    break;
                case 1:
                    tv_voice.setTextColor(unSelectedColor);
                    tv_baby.setTextColor(unSelectedColor);
                    tv_health.setTextColor(selectedColor);
                    break;
                case 2:
                    tv_voice.setTextColor(unSelectedColor);
                    tv_baby.setTextColor(selectedColor);
                    tv_health.setTextColor(unSelectedColor);
                    break;
            }

            mViewPager.setCurrentItem(index);
        }
    }


    //定义适配器
    class MyPagerAdapter extends FragmentPagerAdapter {
        private List<Fragment> fragmentList;

        public MyPagerAdapter(FragmentManager fm, List<Fragment> fragmentArrayList) {
            super(fm);
            this.fragmentList = fragmentArrayList;
        }

        @Override
        public int getCount() {
            return mFragments == null ? 0 : mFragments.size();
        }

        //得到每个页面
        @Override
        public Fragment getItem(int position) {
            return (fragmentList == null || fragmentList.size() == 0) ? null
                    : fragmentList.get(position);
        }


        /**
         * 每个页面的title
         */
        @Override
        public CharSequence getPageTitle(int position) {
            return null;
        }

    }

    private class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        int one = mOffset * 2 + mBmpW;// 页卡1 -> 页卡2 偏移量
        int two = one * 2;// 页卡1 -> 页卡3 偏移量


        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            Animation animation = new TranslateAnimation(one * currIndex, one
                    * position, 0, 0);// 显然这个比较简洁，只有一行代码。
            currIndex = position;
            animation.setFillAfter(true);// True:图片停在动画结束位置
            animation.setDuration(300);
            mIm_cursor.startAnimation(animation);

            switch (position) {
                case 0:
                    tv_voice.setTextColor(selectedColor);
                    tv_health.setTextColor(unSelectedColor);
                    tv_baby.setTextColor(unSelectedColor);
                    break;
                case 1:
                    tv_health.setTextColor(selectedColor);
                    tv_voice.setTextColor(unSelectedColor);
                    tv_baby.setTextColor(unSelectedColor);
                    break;
                case 2:
                    tv_baby.setTextColor(selectedColor);
                    tv_voice.setTextColor(unSelectedColor);
                    tv_health.setTextColor(unSelectedColor);
                    break;
            }


        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
}

