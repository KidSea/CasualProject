package com.meizu.yuxuehai.rxjavademo2.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.meizu.yuxuehai.rxjavademo2.R;
import com.meizu.yuxuehai.rxjavademo2.adapter.ApplicationAdapter;
import com.meizu.yuxuehai.rxjavademo2.bean.AppInfo;
import com.meizu.yuxuehai.rxjavademo2.bean.ApplicationsList;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by yuxuehai on 17-9-29.
 */

public class SecondDemoFragment extends Fragment {

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private ApplicationAdapter mAdapter;

    private File mFile;

    private ArrayList<AppInfo> mAddedApps = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.content_view, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mSwipeRefreshLayout = view.findViewById(R.id.fragment_first_example_swipe_container);
        mRecyclerView = view.findViewById(R.id.fragment_first_example_list);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        mAdapter = new ApplicationAdapter(new ArrayList<AppInfo>());
        mRecyclerView.setAdapter(mAdapter);

        mSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        mSwipeRefreshLayout.setProgressViewOffset(false, 0,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,24,getResources()
                        .getDisplayMetrics()));

        mSwipeRefreshLayout.setEnabled(false);
        mSwipeRefreshLayout.setRefreshing(true);
        mRecyclerView.setVisibility(View.GONE);

        List<AppInfo> apps = ApplicationsList.getInstance().getList();

        loadList(apps);
    }

    private void loadList(List<AppInfo> apps) {
        mRecyclerView.setVisibility(View.VISIBLE);

        Observable.from(apps)
                .filter(new Func1<AppInfo, Boolean>() {
                    @Override
                    public Boolean call(AppInfo appInfo) {
                        return appInfo.getName().startsWith("C");
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<AppInfo>() {
                    @Override
                    public void onCompleted() {
                        mSwipeRefreshLayout.setRefreshing(false);
                        Toast.makeText(getActivity(), "Got the list!", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(getActivity(), "Something went wrong!", Toast.LENGTH_SHORT).show();
                        mSwipeRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onNext(AppInfo appInfo) {
                        mAddedApps.add(appInfo);
                        mAdapter.addApplication(mAddedApps.size() -1 , appInfo);
                    }
                });
    }



}
