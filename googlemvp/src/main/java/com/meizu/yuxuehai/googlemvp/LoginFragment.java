package com.meizu.yuxuehai.googlemvp;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.meizu.yuxuehai.googlemvp.base.BaseFragment;
import com.meizu.yuxuehai.googlemvp.data.bean.User;
import com.meizu.yuxuehai.googlemvp.mvp.LoginContract;

import static android.support.v4.util.Preconditions.checkNotNull;

/**
 * Created by yuxuehai on 18-1-27.
 */

public class LoginFragment extends BaseFragment implements LoginContract.LoginView {

    private EditText mUsername;
    private EditText mUserpasswd;
    private Button mButton;
    private ProgressDialog mProgressDialog;

    private LoginContract.Presenter mPresenter;

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void setPresenter(@NonNull LoginContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void success() {
        //登入成功
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mProgressDialog.dismiss();
                Toast.makeText(getContext(), "欢迎回来", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void fail() {
        //登入成功
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mProgressDialog.dismiss();
                Toast.makeText(getContext(), "登入失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    protected int requestLayoutId() {
        return R.layout.fragment_login;
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        mUsername = (EditText) view.findViewById(R.id.ed_ac);
        mUserpasswd = (EditText) view.findViewById(R.id.ed_passwd);
        mButton = (Button) view.findViewById(R.id.bt_sub);
        mProgressDialog = new ProgressDialog(getContext());
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final User user = new User();
                user.setName(mUsername.getText().toString().trim());
                user.setPasswd(mUserpasswd.getText().toString().trim());
                mProgressDialog.show();
                mUsername.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPresenter.checkLogin(user);
                    }
                },2000);
            }
        });
    }
}
