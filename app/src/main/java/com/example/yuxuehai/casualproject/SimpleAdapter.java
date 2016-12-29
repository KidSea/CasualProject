package com.example.yuxuehai.casualproject;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by yuxuehai on 16-12-29.
 */

public class SimpleAdapter extends RecyclerView.Adapter<SimpleAdapter.MyViewHolder> {


    private ArrayList<String> data;
    private Context mContext;

    public SimpleAdapter(Context context,ArrayList<String> data){
        this.data = data;
        this.mContext = context;
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView mTextView;
        public ImageView mImageView;

        public MyViewHolder(View itemView) {
            super(itemView);
        }
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {



        View view = View.inflate(parent.getContext(), R.layout.rec_item_layout, null);

        MyViewHolder mHolder = new MyViewHolder(view);

        mHolder.mTextView = (TextView) view.findViewById(R.id.tv_res);
        mHolder.mImageView = (ImageView) view.findViewById(R.id.im_icon);


        return mHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.mImageView.setImageResource(R.drawable.wifi);
        holder.mTextView.setText(data.get(position));
    }



    @Override
    public int getItemCount() {
        return data.size();
    }
}
