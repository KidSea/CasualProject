package com.example.taobaoclient.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.taobaoclient.R;

/**
 * Created by yuxuehai on 17-2-9.
 */

public class MyGridViewAdapter extends BaseAdapter {

    private Context mContext;
    private int[] data;

    public MyGridViewAdapter(Context mContexts, int[] data) {
        this.data = data;
        this.mContext = mContexts;
    }

    @Override
    public int getCount() {
        return data.length;
    }

    @Override
    public Object getItem(int position) {
        return data[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHoler holer = null;
        if (convertView == null) {
            holer = new ViewHoler();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_grid_home, null);
            holer.mImageView = (ImageView) convertView.findViewById(R.id.iv_adapter_grid_pic);
            convertView.setTag(holer);
        } else {
            holer = (ViewHoler) convertView.getTag();
        }

        holer.mImageView.setImageResource(data[position]);

        return convertView;
    }

    public static class ViewHoler {

        private ImageView mImageView;
    }
}
