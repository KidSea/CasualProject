package com.meizu.yuxuehai.googledatabinding;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.meizu.yuxuehai.googledatabinding.model.Injection;
import com.meizu.yuxuehai.googledatabinding.model.LoginViewModel;
import com.meizu.yuxuehai.googledatabinding.utils.ActivityUtils;

public class MainActivity extends AppCompatActivity {

    public static final String LOGIN_VIEWMODEL_TAG = "ADD_EDIT_VIEWMODEL_TAG";

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

        LoginViewModel loginViewModel = findOrCreateViewModel();
        loginFragment.setViewModel(loginViewModel);
    }

    private LoginViewModel findOrCreateViewModel() {
        // In a configuration change we might have a ViewModel present. It's retained using the
        // Fragment Manager.
        @SuppressWarnings("unchecked")
        ViewModelHolder<LoginViewModel> retainedViewModel =
                (ViewModelHolder<LoginViewModel>) getSupportFragmentManager()
                        .findFragmentByTag(LOGIN_VIEWMODEL_TAG);

        if (retainedViewModel != null && retainedViewModel.getViewmodel() != null) {
            // If the model was retained, return it.
            return retainedViewModel.getViewmodel();
        } else {
            // There is no ViewModel yet, create it.
            LoginViewModel viewModel = new LoginViewModel(getApplicationContext(),
                    Injection.provideTasksRepository(getApplicationContext()));

            // and bind it to this Activity's lifecycle using the Fragment Manager.
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(),
                    ViewModelHolder.createContainer(viewModel),
                    Integer.parseInt(LOGIN_VIEWMODEL_TAG));
            return viewModel;
        }
    }
}
