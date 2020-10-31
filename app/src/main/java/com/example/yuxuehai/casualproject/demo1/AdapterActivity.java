package com.example.yuxuehai.casualproject.demo1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.yuxuehai.casualproject.R;
import com.example.yuxuehai.casualproject.arouter.RouterPath;

import java.util.ArrayList;

@Route(path = RouterPath.TEST_ROUTER)
public class AdapterActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;

    private ArrayList<String> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adapter);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_vertical);


        initdata();


        SimpleAdapter adapter = new SimpleAdapter(this, data);
        mRecyclerView.setAdapter(adapter);

        // 创建一个线性布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        //设置线性布局
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(new Decoration(this, LinearLayout.VERTICAL));

    }

    private void initdata() {
        data = new ArrayList<String>();
        for (int i = 0; i < 500; i++) {
            String str = "data" + i;
            data.add(str);
        }

    }

}
