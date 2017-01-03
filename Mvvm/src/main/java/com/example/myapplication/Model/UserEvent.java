package com.example.myapplication.Model;

import android.text.Editable;
import android.text.TextWatcher;

import com.example.myapplication.bean.User;

/**
 * Created by yuxuehai on 16-12-30.
 */

public class UserEvent {


    private User user;

    public UserEvent(User user){
        this.user = user;
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
            user.setName(s.toString());
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
            user.setPasswd(s.toString());
        }
    };
}
