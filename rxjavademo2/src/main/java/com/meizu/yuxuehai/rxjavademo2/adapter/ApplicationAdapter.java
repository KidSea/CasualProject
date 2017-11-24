package com.meizu.yuxuehai.rxjavademo2.adapter;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.meizu.yuxuehai.rxjavademo2.R;
import com.meizu.yuxuehai.rxjavademo2.bean.AppInfo;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by yuxuehai on 17-11-24.
 */

public class ApplicationAdapter extends RecyclerView.Adapter<ApplicationAdapter.AppViewHolder> {

    private List<AppInfo> mData;


    public ApplicationAdapter(List<AppInfo> data){
        mData = data;
    }

    public void addApplications(List<AppInfo> applications) {
        mData.clear();
        mData.addAll(applications);
        notifyDataSetChanged();
    }

    public void addApplication(int position, AppInfo appInfo) {
        if (position < 0) {
            position = 0;
        }
        mData.add(position, appInfo);
        notifyItemInserted(position);
    }


    @Override
    public AppViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.applications_list_item, parent, false);

        return new AppViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final AppViewHolder holder, int position) {
        final AppInfo appInfo = mData.get(position);
        holder.name.setText(appInfo.getName());
        getBitmap(appInfo.getIcon())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Bitmap>() {
                    @Override
                    public void call(Bitmap bitmap) {
                      holder.image.setImageBitmap(bitmap);
                    }
                });
    }

    private Observable<Bitmap> getBitmap(final String icon) {
        return Observable.create(new Observable.OnSubscribe<Bitmap>() {
            @Override
            public void call(Subscriber<? super Bitmap> subscriber) {
                subscriber.onNext(BitmapFactory.decodeFile(icon));
                subscriber.onCompleted();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public static class AppViewHolder extends RecyclerView.ViewHolder{

        public TextView name;

        public ImageView image;

        public AppViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            image = (ImageView) itemView.findViewById(R.id.image);

        }
    }

}
