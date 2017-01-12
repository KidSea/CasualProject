package com.example.listviewdemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.listviewdemo.R;

import java.util.ArrayList;

/**
 * Created by yuxuehai on 17-1-12.
 */

public class MyAdapter extends BaseAdapter {


    private final LayoutInflater inflater;
    private ArrayList<String> mDate;

    public MyAdapter(Context context, ArrayList<String> date) {
        this.mDate = date;
        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return mDate.size();
    }

    @Override
    public String getItem(int position) {
        return mDate.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.listview_item, null);

            holder.mTextView = (TextView) convertView.findViewById(R.id.tv_content);

            convertView.setTag(holder);
        }else {

            holder = (ViewHolder) convertView.getTag();
        }

        holder.mTextView.setText(mDate.get(position));

        return convertView;
    }


    private static class ViewHolder {
        private TextView mTextView;

    }
}
