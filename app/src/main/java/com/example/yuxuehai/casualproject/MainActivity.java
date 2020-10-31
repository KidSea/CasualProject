package com.example.yuxuehai.casualproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.callback.NavCallback;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.yuxuehai.casualproject.arouter.RouterPath;

public class MainActivity extends AppCompatActivity {

    private Button mRouterTestBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initValue();
    }

    private void initView() {
        mRouterTestBtn = findViewById(R.id.btn_router_test);
    }

    private void initValue() {
        mRouterTestBtn.setOnClickListener(mOnClickListener);
    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            switch (id) {
                case R.id.btn_router_test:
                    //发起路由跳转
                    ARouter.getInstance().build(RouterPath.TEST_ROUTER).navigation(MainActivity.this, new NavCallback() {
                        @Override
                        public void onFound(Postcard postcard) {
                            super.onFound(postcard);
                            Log.d("yuxuehai", "onFound: " + postcard);
                        }

                        @Override
                        public void onArrival(Postcard postcard) {
                            Log.d("yuxuehai", "onArrival: " + postcard.getGroup());
                        }


                        @Override
                        public void onLost(Postcard postcard) {
                            super.onLost(postcard);
                            Log.d("yuxuehai", "onLost: " + postcard);
                        }

                        @Override
                        public void onInterrupt(Postcard postcard) {
                            super.onInterrupt(postcard);
                            Log.d("yuxuehai", "onInterrupt: " + postcard);
                        }
                    });
                default:
            }
        }
    };
}
