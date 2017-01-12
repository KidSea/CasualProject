package com.example.listviewdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ListView;

import com.example.listviewdemo.adapter.MyAdapter;
import com.example.listviewdemo.net.DateService;
import com.example.listviewdemo.utils.ScreenUtil;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ListView mListView;
    private Button mButton;

    private ArrayList<String> mDate;
    private MyAdapter mAdapter;
    private boolean isScorll;

    private int lastVisibleItemPosition = 0;// 标记上次滑动位置

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();
        initView();

    }

    private void initData() {
        mDate = DateService.getDateFromNet();
    }

    private void initView() {
        mListView = (ListView) findViewById(R.id.lv_list);
        mButton = (Button) findViewById(R.id.top_btn);

        mAdapter = new MyAdapter(this, mDate);

        mListView.setAdapter(mAdapter);
        mButton.setOnClickListener(this);


        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

                switch (scrollState) {
                    //不滚动时
                    case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                        isScorll = false;

                        //判断是否滚到底
                        if (mListView.getLastVisiblePosition() == mListView.getCount() - 1) {
                            mButton.setVisibility(View.VISIBLE);
                        }

                        //当滚到顶部
                        if (mListView.getFirstVisiblePosition() == 0) {
                            mButton.setVisibility(View.INVISIBLE);
                        }

                        break;
                    case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:// 滚动时
                        isScorll = true;
                        break;
                    case AbsListView.OnScrollListener.SCROLL_STATE_FLING://当是当用户由于之前划动屏幕并抬起手指，屏幕产生惯性滑动时
                        isScorll = false;
                        break;

                }
            }


            /**
             * firstVisibleItem：当前能看见的第一个列表项ID（从0开始）
             * visibleItemCount：当前能看见的列表项个数（小半个也算） totalItemCount：列表项共数
             */

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                // 当开始滑动且ListView底部的Y轴点超出屏幕最大范围时，显示或隐藏顶部按钮
                if (isScorll &&
                        ScreenUtil.getScreenViewBottomHeight(mListView) >= ScreenUtil.
                                getScreenHeight(MainActivity.this)) {
                    if (firstVisibleItem > lastVisibleItemPosition) {//上滑
                        mButton.setVisibility(View.VISIBLE);
                    } else if (firstVisibleItem < lastVisibleItemPosition) {//下滑
                        mButton.setVisibility(View.GONE);
                    } else {
                        return;
                    }
                    lastVisibleItemPosition = firstVisibleItem;
                }

            }
        });
    }

    /**
     * 滚动ListView到指定位置
     *
     * @param pos
     */
    private void setListViewPos(int pos) {
        if (android.os.Build.VERSION.SDK_INT >= 8) {
            mListView.smoothScrollToPosition(pos);
        } else {
            mListView.setSelection(pos);
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.top_btn:
                setListViewPos(0);
                break;
        }
    }
}
