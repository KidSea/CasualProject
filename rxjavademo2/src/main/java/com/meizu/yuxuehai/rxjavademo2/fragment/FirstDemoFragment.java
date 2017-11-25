package com.meizu.yuxuehai.rxjavademo2.fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ResolveInfo;import android.graphics.Bitmap;
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

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.meizu.yuxuehai.rxjavademo2.MyApplication;
import com.meizu.yuxuehai.rxjavademo2.R;
import com.meizu.yuxuehai.rxjavademo2.adapter.ApplicationAdapter;
import com.meizu.yuxuehai.rxjavademo2.bean.AppInfo;
import com.meizu.yuxuehai.rxjavademo2.bean.AppInfoRich;
import com.meizu.yuxuehai.rxjavademo2.bean.ApplicationsList;
import com.meizu.yuxuehai.rxjavademo2.utils.Utils;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by yuxuehai on 17-9-29.
 */

public class FirstDemoFragment extends Fragment {

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private ApplicationAdapter mAdapter;

    private File mFile;

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

        getFileDir()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<File>() {
                    @Override
                    public void call(File file) {
                        mFile = file;
                        FirstDemoFragment.this.refreshList();
                    }
                });
    }

    private void refreshList() {
        getApps()
                .toSortedList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<AppInfo>>() {
                    @Override
                    public void onCompleted() {
                        Toast.makeText(getActivity(), "Got the list!", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(getActivity(), "Something went wrong!", Toast.LENGTH_SHORT).show();
                        mSwipeRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onNext(List<AppInfo> appInfos) {
                        mRecyclerView.setVisibility(View.VISIBLE);
                        mAdapter.addApplications(appInfos);
                        mSwipeRefreshLayout.setRefreshing(false);
                        storeList(appInfos);
                    }
                });
    }

    private void storeList(final List<AppInfo> appInfos) {
        ApplicationsList.getInstance().setList(appInfos);

        Schedulers.io().createWorker().schedule(new Action0() {
            @Override
            public void call() {
                SharedPreferences sharedPref = FirstDemoFragment.this.getActivity().getPreferences(Context.MODE_PRIVATE);
                Type appInfoType = new TypeToken<List<AppInfo>>() {
                }.getType();
                sharedPref.edit().putString("APPS", new Gson().toJson(appInfos, appInfoType)).apply();
            }
        });

    }

    private Observable<AppInfo> getApps(){
        return Observable.create(new Observable.OnSubscribe<AppInfo>() {
            @Override
            public void call(Subscriber<? super AppInfo> subscriber) {
                List<AppInfoRich> appInfoRichList = new ArrayList<AppInfoRich>();

                final Intent intent = new Intent(Intent.ACTION_MAIN, null);
                intent.addCategory(Intent.CATEGORY_LAUNCHER);

                List<ResolveInfo> infos = getActivity().getPackageManager().queryIntentActivities(intent, 0);
                for (ResolveInfo info : infos) {
                    appInfoRichList.add(new AppInfoRich(getActivity(), info));
                }

                for (AppInfoRich appInfo : appInfoRichList) {
                    Bitmap icon = Utils.drawableToBitmap(appInfo.getIcon());
                    String name = appInfo.getName();
                    String iconPath = mFile + "/" + name;
                    Utils.storeBitmap(MyApplication.instance, icon, name);

                    if (subscriber.isUnsubscribed()) {
                        return;
                    }
                    subscriber.onNext(new AppInfo(name, iconPath));
                }
                if (!subscriber.isUnsubscribed()) {
                    subscriber.onCompleted();
                }
            }
        });
    }

    private Observable<File> getFileDir(){

        return Observable.create(new Observable.OnSubscribe<File>() {
            @Override
            public void call(Subscriber<? super File> subscriber) {
                subscriber.onNext(MyApplication.instance.getFilesDir());
                subscriber.onCompleted();
            }
        });
    }
}
