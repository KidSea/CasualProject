package com.meizu.yuxuehai.googlemvp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.meizu.yuxuehai.googlemvp.data.Injection;
import com.meizu.yuxuehai.googlemvp.mvp.LoginPresenter;
import com.meizu.yuxuehai.googlemvp.utils.ActivityUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LoginFragment loginFragment = (LoginFragment) getSupportFragmentManager()
                .findFragmentById(R.id.contentFrame);
        if (loginFragment == null) {
            loginFragment = LoginFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    loginFragment, R.id.contentFrame);
        }

        new LoginPresenter(Injection.provideTasksRepository(getApplicationContext()), loginFragment);
    }
}
