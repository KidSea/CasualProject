package com.meizu.yuxuehai.rxjava2demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
                e.onComplete();
            }
        });
    }

    //创建一个下游 Observer
    Observer<Integer> observer = new Observer<Integer>() {

        @Override
        public void onSubscribe(Disposable d) {
            Log.d(TAG, "subscribe");
        }

        @Override
        public void onNext(Integer value) {
            Log.d(TAG, "" + value);
        }

        @Override
        public void onError(Throwable e) {
            Log.d(TAG, "error");
        }

        @Override
        public void onComplete() {
            Log.d(TAG, "complete");
        }
    };

}
