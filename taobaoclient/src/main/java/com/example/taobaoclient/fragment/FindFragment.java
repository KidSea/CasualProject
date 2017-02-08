package com.example.taobaoclient.fragment;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.taobaoclient.R;

/**
 * Created by yuxuehai on 17-2-5.
 */

public class FindFragment extends BaseFragment {
    @Override
    protected View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_find, null);
    }

    @Override
    protected void initView(View rootView) {
        TextView textView = (TextView) rootView.findViewById(R.id.tv_show);
        textView.setGravity(Gravity.CENTER);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void load() {

    }
}
