package com.meizu.yuxuehai.googledatabinding;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.meizu.yuxuehai.googledatabinding.databinding.FragmentLoginBinding;
import com.meizu.yuxuehai.googledatabinding.model.LoginViewModel;

/**
 * Created by yuxuehai on 18-1-27.
 */

public class LoginFragment extends Fragment implements View.OnClickListener {

    private FragmentLoginBinding mBinding;
    private LoginViewModel mLoginViewModel;


    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_login, container, false);
        initView();
        return mBinding.getRoot();
    }

    private void initView() {
        mBinding.btSub.setOnClickListener(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mBinding.setLogin(mLoginViewModel);
    }

    @Override
    public void onResume() {
        super.onResume();
        mLoginViewModel.start();
    }

    public void setViewModel(LoginViewModel loginViewModel) {
        mLoginViewModel = loginViewModel;
    }

    @Override
    public void onClick(View view) {
        mLoginViewModel.checkLogin();
    }
}
