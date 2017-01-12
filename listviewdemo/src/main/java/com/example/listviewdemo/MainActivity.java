package com.example.listviewdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.listviewdemo.adapter.MyAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ListView mListView;
    private Button mButton;

    private ArrayList<String> mDate;
    private MyAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();
        initView();

    }

    private void initData() {
        mDate = new ArrayList<String>();

        for (int i = 0; i < 30; i++) {
            mDate.add("这是第" + i + "个item");
        }
    }

    private void initView() {
        mListView = (ListView) findViewById(R.id.lv_list);
        mButton = (Button) findViewById(R.id.top_btn);

        mAdapter = new MyAdapter(this, mDate);

        mListView.setAdapter(mAdapter);
        mButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id){
            case R.id.top_btn:

                break;
        }
    }
}
