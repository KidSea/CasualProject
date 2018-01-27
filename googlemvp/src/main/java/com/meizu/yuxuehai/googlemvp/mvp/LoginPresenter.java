package com.meizu.yuxuehai.googlemvp.mvp;

import android.support.annotation.NonNull;

import com.meizu.yuxuehai.googlemvp.data.DataRespository;
import com.meizu.yuxuehai.googlemvp.data.bean.User;
import com.meizu.yuxuehai.googlemvp.data.net.NetModule;

/**
 * Created by yuxuehai on 18-1-27.
 */

public class LoginPresenter implements LoginContract.Presenter {

    private final DataRespository mDataRespository;
    private final LoginContract.LoginView mLoginView;
    private final NetModule mNetModule = NetModule.getInstance();


    public LoginPresenter(@NonNull DataRespository dataRespository,
                          @NonNull LoginContract.LoginView loginView) {
        mDataRespository = dataRespository;
        mLoginView = loginView;

        mLoginView.setPresenter(this);
    }


    @Override
    public void start() {

    }

    @Override
    public void checkLogin(User user) {
        if (mNetModule.checkUser(user)) {
            mLoginView.success();
        } else {
            mLoginView.fail();
        }
    }
}
