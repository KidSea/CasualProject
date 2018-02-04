package com.meizu.yuxuehai.googledatabinding;

import android.app.ProgressDialog;
import android.widget.Button;
import android.widget.EditText;

import com.meizu.yuxuehai.googledatabinding.base.BaseFragment;

/**
 * Created by yuxuehai on 18-1-27.
 */

public class LoginFragment extends BaseFragment {

    private EditText mUsername;
    private EditText mUserpasswd;
    private Button mButton;
    private ProgressDialog mProgressDialog;


    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    protected int requestLayoutId() {
        return R.layout.fragment_login;
    }
}
