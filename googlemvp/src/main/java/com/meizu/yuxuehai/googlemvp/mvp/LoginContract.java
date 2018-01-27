package com.meizu.yuxuehai.googlemvp.mvp;

import com.meizu.yuxuehai.googlemvp.base.BasePresenter;
import com.meizu.yuxuehai.googlemvp.base.BaseView;
import com.meizu.yuxuehai.googlemvp.data.bean.User;

/**
 * Created by yuxuehai on 18-1-27.
 */

public interface LoginContract {

    interface LoginView extends BaseView<Presenter> {
        void success();
        void fail();

        boolean isActive();
    }

    interface Presenter extends BasePresenter {

        void checkLogin(User user);
    }
}
