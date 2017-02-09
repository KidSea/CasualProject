package com.example.taobaoclient.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by yuxuehai on 17-2-5.
 */

public abstract class BaseFragment extends Fragment implements View.OnClickListener{

    protected Activity mActivity;
    private View mMainView = null;

    protected abstract View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    protected abstract void initView(View rootView);

    protected abstract void initData();

    protected abstract void load();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (mMainView == null || mMainView.getParent() != null) {
            mMainView = createView(inflater, container, savedInstanceState);
            initView(mMainView);
        }
        initData();
        load();

        return mMainView;
    }

    protected Intent getIntent(){
        return mActivity.getIntent();
    }
}
