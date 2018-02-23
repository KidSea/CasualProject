package com.meizu.yuxuehai.googledatabinding.model;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.ObservableBoolean;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Toast;
import android.view.View;

import com.meizu.yuxuehai.googledatabinding.model.bean.User;
import com.meizu.yuxuehai.googledatabinding.model.net.NetModule;

/**
 * Created by yuxuehai on 18-2-10.
 */

public class LoginViewModel extends BaseObservable {

    public final ObservableBoolean checking = new ObservableBoolean(false);
    final ObservableBoolean show = new ObservableBoolean(false);
    private final NetModule mNetModule = NetModule.getInstance();

    private User mUser = new User();

    private Context mContext;

    private final DataRespository mDataRespository;

    public LoginViewModel(Context context, DataRespository dataRespository) {
        mDataRespository =  dataRespository;
        mContext = context;
        mUser.setName("a");
        mUser.setPasswd("123");
    }

    public void start(){
        loadData();
    }


    private void loadData() {

    }

    public TextWatcher usernameTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            mUser.setName(s.toString());
        }
    };

    public TextWatcher passwordTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            mUser.setPasswd(s.toString());
        }
    };


    public void onCheckLogin(View view) {
        checking.set(true);
        show.set(false);
        if (mNetModule.checkUser(mUser)){
            checking.set(false);
            show.set(true);
            Toast.makeText(mContext,"欢迎回来",Toast.LENGTH_SHORT).show();

        }else {
            checking.set(false);
            show.set(true);
            Toast.makeText(mContext,"登入失败",Toast.LENGTH_SHORT).show();
        }

    }
}
